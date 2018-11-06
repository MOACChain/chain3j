package org.chain3j.codegen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;

import org.chain3j.TempFileProvider;
import org.chain3j.abi.datatypes.Address;
import org.chain3j.abi.datatypes.Bool;
import org.chain3j.abi.datatypes.DynamicArray;
import org.chain3j.abi.datatypes.DynamicBytes;
import org.chain3j.abi.datatypes.StaticArray;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.abi.datatypes.generated.Bytes32;
import org.chain3j.abi.datatypes.generated.Int256;
import org.chain3j.abi.datatypes.generated.StaticArray10;
import org.chain3j.abi.datatypes.generated.StaticArray2;
import org.chain3j.abi.datatypes.generated.StaticArray3;
import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.abi.datatypes.generated.Uint64;
import org.chain3j.protocol.core.methods.response.AbiDefinition;

import static org.chain3j.codegen.SolidityFunctionWrapper.buildTypeName;
import static org.chain3j.codegen.SolidityFunctionWrapper.createValidParamName;
import static org.chain3j.codegen.SolidityFunctionWrapper.getEventNativeType;
import static org.chain3j.codegen.SolidityFunctionWrapper.getNativeType;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class SolidityFunctionWrapperTest extends TempFileProvider {

    private SolidityFunctionWrapper solidityFunctionWrapper;

    private GenerationReporter generationReporter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        generationReporter = mock(GenerationReporter.class);
        solidityFunctionWrapper = new SolidityFunctionWrapper(true, generationReporter);
    }

    @Test
    public void testCreateValidParamName() {
        assertThat(createValidParamName("param", 1), is("param"));
        assertThat(createValidParamName("", 1), is("param1"));
    }

    @Test
    public void testBuildTypeName() {
        assertThat(buildTypeName("uint256"),
                is(ClassName.get(Uint256.class)));
        assertThat(buildTypeName("uint64"),
                is(ClassName.get(Uint64.class)));
        assertThat(buildTypeName("string"),
                is(ClassName.get(Utf8String.class)));

        assertThat(buildTypeName("uint256[]"),
                is(ParameterizedTypeName.get(DynamicArray.class, Uint256.class)));

        assertThat(buildTypeName("uint256[] storage"),
                is(ParameterizedTypeName.get(DynamicArray.class, Uint256.class)));

        assertThat(buildTypeName("uint256[] memory"),
                is(ParameterizedTypeName.get(DynamicArray.class, Uint256.class)));

        assertThat(buildTypeName("uint256[10]"),
                is(ParameterizedTypeName.get(StaticArray10.class, Uint256.class)));

        assertThat(buildTypeName("uint256[33]"),
                is(ParameterizedTypeName.get(StaticArray.class, Uint256.class)));

        assertThat(buildTypeName("uint256[10][3]"),
                is(ParameterizedTypeName.get(ClassName.get(StaticArray3.class),
                        ParameterizedTypeName.get(StaticArray10.class, Uint256.class))));

        assertThat(buildTypeName("uint256[2][]"),
                is(ParameterizedTypeName.get(ClassName.get(DynamicArray.class),
                        ParameterizedTypeName.get(StaticArray2.class, Uint256.class))));

        assertThat(buildTypeName("uint256[33][]"),
                is(ParameterizedTypeName.get(ClassName.get(DynamicArray.class),
                        ParameterizedTypeName.get(StaticArray.class, Uint256.class))));

        assertThat(buildTypeName("uint256[][]"),
                is(ParameterizedTypeName.get(ClassName.get(DynamicArray.class),
                        ParameterizedTypeName.get(DynamicArray.class, Uint256.class))));
    }

    @Test
    public void testGetNativeType() {
        assertThat(getNativeType(TypeName.get(Address.class)),
                equalTo(TypeName.get(String.class)));
        assertThat(getNativeType(TypeName.get(Uint256.class)),
                equalTo(TypeName.get(BigInteger.class)));
        assertThat(getNativeType(TypeName.get(Int256.class)),
                equalTo(TypeName.get(BigInteger.class)));
        assertThat(getNativeType(TypeName.get(Utf8String.class)),
                equalTo(TypeName.get(String.class)));
        assertThat(getNativeType(TypeName.get(Bool.class)),
                equalTo(TypeName.get(Boolean.class)));
        assertThat(getNativeType(TypeName.get(Bytes32.class)),
                equalTo(TypeName.get(byte[].class)));
        assertThat(getNativeType(TypeName.get(DynamicBytes.class)),
                equalTo(TypeName.get(byte[].class)));
    }

    @Test
    public void testGetNativeTypeParameterized() {
        assertThat(getNativeType(
                ParameterizedTypeName.get(
                        ClassName.get(DynamicArray.class), TypeName.get(Address.class))),
                equalTo(ParameterizedTypeName.get(
                        ClassName.get(List.class), TypeName.get(String.class))));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetNativeTypeInvalid() {
        getNativeType(TypeName.get(BigInteger.class));
    }

    @Test
    public void testGetEventNativeType() {
        assertThat(getEventNativeType(TypeName.get(Utf8String.class)),
                equalTo(TypeName.get(byte[].class)));
    }

    @Test
    public void testGetEventNativeTypeParameterized() {
        assertThat(getEventNativeType(
                ParameterizedTypeName.get(
                        ClassName.get(DynamicArray.class), TypeName.get(Address.class))),
                equalTo(TypeName.get(byte[].class)));
    }

    @Test
    public void testBuildFunctionTransaction() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                false,
                Arrays.asList(
                        new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Collections.emptyList(),
                "type",
                false);

        MethodSpec methodSpec = solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        String expected =
                "public org.chain3j.protocol.core.RemoteCall<org.chain3j.protocol.core.methods.response.TransactionReceipt> functionName(java.math.BigInteger param) {\n"
                        + "  final org.chain3j.abi.datatypes.Function function = new org.chain3j.abi.datatypes.Function(\n"
                        + "      FUNC_FUNCTIONNAME, \n"
                        + "      java.util.Arrays.<org.chain3j.abi.datatypes.Type>asList(new org.chain3j.abi.datatypes.generated.Uint8(param)), \n"
                        + "      java.util.Collections.<org.chain3j.abi.TypeReference<?>>emptyList());\n"
                        + "  return executeRemoteCallTransaction(function);\n"
                        + "}\n";
        //CHECKSTYLE:ON

        assertThat(methodSpec.toString(), is(expected));
    }

    @Test
    public void testBuildingFunctionTransactionThatReturnsValueReportsWarning() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                false,
                Arrays.asList(
                        new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Arrays.asList(
                        new AbiDefinition.NamedType("result", "uint8")),
                "type",
                false);

        solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        verify(generationReporter).report(
                "Definition of the function functionName returns a value but is not defined as a view function. " +
                        "Please ensure it contains the view modifier if you want to read the return value");
        //CHECKSTYLE:ON
    }

    @Test
    public void testBuildPayableFunctionTransaction() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                false,
                Arrays.asList(
                        new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Collections.emptyList(),
                "type",
                true);

        MethodSpec methodSpec = solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        String expected =
                "public org.chain3j.protocol.core.RemoteCall<org.chain3j.protocol.core.methods.response.TransactionReceipt> functionName(java.math.BigInteger param, java.math.BigInteger shaValue) {\n"
                        + "  final org.chain3j.abi.datatypes.Function function = new org.chain3j.abi.datatypes.Function(\n"
                        + "      FUNC_FUNCTIONNAME, \n"
                        + "      java.util.Arrays.<org.chain3j.abi.datatypes.Type>asList(new org.chain3j.abi.datatypes.generated.Uint8(param)), \n"
                        + "      java.util.Collections.<org.chain3j.abi.TypeReference<?>>emptyList());\n"
                        + "  return executeRemoteCallTransaction(function, shaValue);\n"
                        + "}\n";
        //CHECKSTYLE:ON

        assertThat(methodSpec.toString(), is(expected));
    }

    @Test
    public void testBuildFunctionConstantSingleValueReturn() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                true,
                Arrays.asList(
                        new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Arrays.asList(
                        new AbiDefinition.NamedType("result", "int8")),
                "type",
                false);

        MethodSpec methodSpec = solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        String expected =
                "public org.chain3j.protocol.core.RemoteCall<java.math.BigInteger> functionName(java.math.BigInteger param) {\n"
                        + "  final org.chain3j.abi.datatypes.Function function = new org.chain3j.abi.datatypes.Function(FUNC_FUNCTIONNAME, \n"
                        + "      java.util.Arrays.<org.chain3j.abi.datatypes.Type>asList(new org.chain3j.abi.datatypes.generated.Uint8(param)), \n"
                        + "      java.util.Arrays.<org.chain3j.abi.TypeReference<?>>asList(new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.generated.Int8>() {}));\n"
                        + "  return executeRemoteCallSingleValueReturn(function, java.math.BigInteger.class);\n"
                        + "}\n";
        //CHECKSTYLE:ON

        assertThat(methodSpec.toString(), is(expected));
    }

    @Test
    public void testBuildFunctionConstantSingleValueRawListReturn() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                true,
                Arrays.asList(
                    new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Arrays.asList(
                        new AbiDefinition.NamedType("result", "address[]")),
                "type",
                false);

        MethodSpec methodSpec = solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        String expected =
                "public org.chain3j.protocol.core.RemoteCall<java.util.List> functionName(java.math.BigInteger param) {\n"
                + "  final org.chain3j.abi.datatypes.Function function = new org.chain3j.abi.datatypes.Function(FUNC_FUNCTIONNAME, \n"
                + "      java.util.Arrays.<org.chain3j.abi.datatypes.Type>asList(new org.chain3j.abi.datatypes.generated.Uint8(param)), \n"
                + "      java.util.Arrays.<org.chain3j.abi.TypeReference<?>>asList(new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.DynamicArray<org.chain3j.abi.datatypes.Address>>() {}));\n"
                + "  return new org.chain3j.protocol.core.RemoteCall<java.util.List>(\n"
                + "      new java.util.concurrent.Callable<java.util.List>() {\n"
                + "        @java.lang.Override\n"
                + "        @java.lang.SuppressWarnings(\"unchecked\")\n"
                + "        public java.util.List call() throws java.lang.Exception {\n"
                + "          java.util.List<org.chain3j.abi.datatypes.Type> result = (java.util.List<org.chain3j.abi.datatypes.Type>) executeCallSingleValueReturn(function, java.util.List.class);\n"
                + "          return convertToNative(result);\n"
                + "        }\n"
                + "      });\n"
                + "}\n";
        //CHECKSTYLE:ON

        assertThat(methodSpec.toString(), is(expected));
    }

    @Test
    public void testBuildFunctionConstantInvalid() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                true,
                Arrays.asList(
                        new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Collections.emptyList(),
                "type",
                false);

        MethodSpec methodSpec = solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        String expected =
                "public void functionName(java.math.BigInteger param) {\n"
                + "  throw new RuntimeException(\"cannot call constant function with void return type\");\n"
                + "}\n";
        //CHECKSTYLE:ON

        assertThat(methodSpec.toString(), is(expected));
    }

    @Test
    public void testBuildFunctionConstantMultipleValueReturn() throws Exception {

        AbiDefinition functionDefinition = new AbiDefinition(
                true,
                Arrays.asList(
                        new AbiDefinition.NamedType("param1", "uint8"),
                        new AbiDefinition.NamedType("param2", "uint32")),
                "functionName",
                Arrays.asList(
                        new AbiDefinition.NamedType("result1", "int8"),
                        new AbiDefinition.NamedType("result2", "int32")),
                "type",
                false);

        MethodSpec methodSpec = solidityFunctionWrapper.buildFunction(functionDefinition);

        //CHECKSTYLE:OFF
        String expected = "public org.chain3j.protocol.core.RemoteCall<org.chain3j.tuples.generated.Tuple2<java.math.BigInteger, java.math.BigInteger>> functionName(java.math.BigInteger param1, java.math.BigInteger param2) {\n"
                + "  final org.chain3j.abi.datatypes.Function function = new org.chain3j.abi.datatypes.Function(FUNC_FUNCTIONNAME, \n"
                + "      java.util.Arrays.<org.chain3j.abi.datatypes.Type>asList(new org.chain3j.abi.datatypes.generated.Uint8(param1), \n"
                + "      new org.chain3j.abi.datatypes.generated.Uint32(param2)), \n"
                + "      java.util.Arrays.<org.chain3j.abi.TypeReference<?>>asList(new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.generated.Int8>() {}, new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.generated.Int32>() {}));\n"
                + "  return new org.chain3j.protocol.core.RemoteCall<org.chain3j.tuples.generated.Tuple2<java.math.BigInteger, java.math.BigInteger>>(\n"
                + "      new java.util.concurrent.Callable<org.chain3j.tuples.generated.Tuple2<java.math.BigInteger, java.math.BigInteger>>() {\n"
                + "        @java.lang.Override\n"
                + "        public org.chain3j.tuples.generated.Tuple2<java.math.BigInteger, java.math.BigInteger> call() throws java.lang.Exception {\n"
                + "          java.util.List<org.chain3j.abi.datatypes.Type> results = executeCallMultipleValueReturn(function);\n"
                + "          return new org.chain3j.tuples.generated.Tuple2<java.math.BigInteger, java.math.BigInteger>(\n"
                + "              (java.math.BigInteger) results.get(0).getValue(), \n"
                + "              (java.math.BigInteger) results.get(1).getValue());\n"
                + "        }\n"
                + "      });\n"
                + "}\n";
        //CHECKSTYLE:ON

        assertThat(methodSpec.toString(), is(expected));
    }

    @Test
    public void testBuildEventConstantMultipleValueReturn() throws Exception {

        AbiDefinition.NamedType id = new AbiDefinition.NamedType("id", "string", true);
        AbiDefinition.NamedType fromAddress = new AbiDefinition.NamedType("from", "address");
        AbiDefinition.NamedType toAddress = new AbiDefinition.NamedType("to", "address");
        AbiDefinition.NamedType value = new AbiDefinition.NamedType("value", "uint256");
        AbiDefinition.NamedType message = new AbiDefinition.NamedType("message", "string");
        fromAddress.setIndexed(true);
        toAddress.setIndexed(true);

        AbiDefinition functionDefinition = new AbiDefinition(
                false,
                Arrays.asList(id, fromAddress, toAddress, value, message),
                "Transfer",
                new ArrayList<>(),
                "event",
                false);
        TypeSpec.Builder builder = TypeSpec.classBuilder("testClass");

        builder.addMethods(
                solidityFunctionWrapper.buildEventFunctions(functionDefinition, builder));

        //CHECKSTYLE:OFF
        String expected =
                "class testClass {\n"
                        + "  public static final org.chain3j.abi.datatypes.Event TRANSFER_EVENT = new org.chain3j.abi.datatypes.Event(\"Transfer\", \n"
                        + "      java.util.Arrays.<org.chain3j.abi.TypeReference<?>>asList(new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.Utf8String>(true) {}, new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.Address>(true) {}, new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.Address>(true) {}, new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.generated.Uint256>() {}, new org.chain3j.abi.TypeReference<org.chain3j.abi.datatypes.Utf8String>() {}));\n  ;\n\n"
                        + "  public java.util.List<TransferEventResponse> getTransferEvents(org.chain3j.protocol.core.methods.response.TransactionReceipt transactionReceipt) {\n"
                        + "    java.util.List<org.chain3j.tx.Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);\n"
                        + "    java.util.ArrayList<TransferEventResponse> responses = new java.util.ArrayList<TransferEventResponse>(valueList.size());\n"
                        + "    for (org.chain3j.tx.Contract.EventValuesWithLog eventValues : valueList) {\n"
                        + "      TransferEventResponse typedResponse = new TransferEventResponse();\n"
                        + "      typedResponse.log = eventValues.getLog();\n"
                        + "      typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();\n"
                        + "      typedResponse.from = (java.lang.String) eventValues.getIndexedValues().get(1).getValue();\n"
                        + "      typedResponse.to = (java.lang.String) eventValues.getIndexedValues().get(2).getValue();\n"
                        + "      typedResponse.value = (java.math.BigInteger) eventValues.getNonIndexedValues().get(0).getValue();\n"
                        + "      typedResponse.message = (java.lang.String) eventValues.getNonIndexedValues().get(1).getValue();\n"
                        + "      responses.add(typedResponse);\n"
                        + "    }\n"
                        + "    return responses;\n"
                        + "  }\n"
                        + "\n"
                        + "  public rx.Observable<TransferEventResponse> transferEventObservable(org.chain3j.protocol.core.methods.request.McFilter filter) {\n"
                        + "    return chain3j.mcLogObservable(filter).map(new rx.functions.Func1<org.chain3j.protocol.core.methods.response.Log, TransferEventResponse>() {\n"
                        + "      @java.lang.Override\n"
                        + "      public TransferEventResponse call(org.chain3j.protocol.core.methods.response.Log log) {\n"
                        + "        org.chain3j.tx.Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);\n"
                        + "        TransferEventResponse typedResponse = new TransferEventResponse();\n"
                        + "        typedResponse.log = log;\n"
                        + "        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();\n"
                        + "        typedResponse.from = (java.lang.String) eventValues.getIndexedValues().get(1).getValue();\n"
                        + "        typedResponse.to = (java.lang.String) eventValues.getIndexedValues().get(2).getValue();\n"
                        + "        typedResponse.value = (java.math.BigInteger) eventValues.getNonIndexedValues().get(0).getValue();\n"
                        + "        typedResponse.message = (java.lang.String) eventValues.getNonIndexedValues().get(1).getValue();\n"
                        + "        return typedResponse;\n"
                        + "      }\n"
                        + "    });\n"
                        + "  }\n"
                        + "\n"
                        + "  public rx.Observable<TransferEventResponse> transferEventObservable(org.chain3j.protocol.core.DefaultBlockParameter startBlock, org.chain3j.protocol.core.DefaultBlockParameter endBlock) {\n"
                        + "    org.chain3j.protocol.core.methods.request.McFilter filter = new org.chain3j.protocol.core.methods.request.McFilter(startBlock, endBlock, getContractAddress());\n"
                        + "    filter.addSingleTopic(org.chain3j.abi.EventEncoder.encode(TRANSFER_EVENT));\n"
                        + "    return transferEventObservable(filter);\n"
                        + "  }\n"
                        + "\n"
                        + "  public static class TransferEventResponse {\n"
                        + "    public org.chain3j.protocol.core.methods.response.Log log;\n"
                        + "\n"
                        + "    public byte[] id;\n"
                        + "\n"
                        + "    public java.lang.String from;\n"
                        + "\n"
                        + "    public java.lang.String to;\n"
                        + "\n"
                        + "    public java.math.BigInteger value;\n"
                        + "\n"
                        + "    public java.lang.String message;\n"
                        + "  }\n"
                        + "}\n";
        //CHECKSTYLE:ON

        assertThat(builder.build().toString(), is(expected));
    }

    @Test
    public void testBuildFuncNameConstants() throws Exception {
        AbiDefinition functionDefinition = new AbiDefinition(
                false,
                Arrays.asList(
                        new AbiDefinition.NamedType("param", "uint8")),
                "functionName",
                Collections.emptyList(),
                "function",
                true);
        TypeSpec.Builder builder = TypeSpec.classBuilder("testClass");

        builder.addFields(solidityFunctionWrapper
                .buildFuncNameConstants(Collections.singletonList(functionDefinition)));


        //CHECKSTYLE:OFF
        String expected =
                "class testClass {\n" +
                        "  public static final java.lang.String FUNC_FUNCTIONNAME = \"functionName\";\n" +
                        "}\n";
        //CHECKSTYLE:ON


        assertThat(builder.build().toString(), is(expected));
    }

}
