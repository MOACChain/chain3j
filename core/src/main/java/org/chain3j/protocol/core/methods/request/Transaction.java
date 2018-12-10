package org.chain3j.protocol.core.methods.request;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.chain3j.tx.ChainId;
import org.chain3j.utils.Numeric;

/**
 * WalletDemo request object used the below methods.
 * <ol>
 *     <li>mc_call</li>
 *     <li>mc_sendTransaction</li>
 *     <li>mc_estimateGas</li>
 * </ol>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    // https://github.com/MOACChain/moac-core/wiki/JSON-RPC#mc_sendtransaction
    public static final BigInteger DEFAULT_GAS = BigInteger.valueOf(9000);

    private String from;
    private String to;
    private BigInteger gas;
    private BigInteger gasPrice;
    private BigInteger value;
    private String data;
    private BigInteger nonce;  // nonce field is not present on mc_call/mc_estimateGas
    // private Integer chainId;
    //private String shardingFlag;// 0 - MotherChain TX, 1 - MicroChain TX
    private Integer shardingFlag;//Edit by Shidian Wang
    //private String systemFlag;  // 0 - non-system TX, default;
    private Integer systemFlag;//Edit by Shidian Wang
    //1 - system TX, internal use only, should not set
    private String via;         // 

    public Transaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                       String to, BigInteger value, String data) {
        this.from = from;
        this.to = to;
        this.gas = gasLimit;
        this.gasPrice = gasPrice;
        this.value = value;

        //Set default values for new flags
//        this.shardingFlag = "0";
//        this.systemFlag = "0";
        this.shardingFlag = 0; //Edit by Shidian Wang
        this.systemFlag = 0; //Edit by Shidian Wang
        this.via = "0xD814F2ac2c4cA49b33066582E4e97EBae02F2aB9";

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }

        this.nonce = nonce;
        // if ( chainId.byteValue() > ChainId.NONE){
        //     this.chainId = chainId;
        // }
    }

    public static Transaction createContractTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
            BigInteger value, String init) {

        return new Transaction(from, nonce, gasPrice, gasLimit, null, value, init);
    }

    public static Transaction createContractTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, String init) {

        return createContractTransaction(from, nonce, gasPrice, null, null, init);
    }

    public static Transaction createMcTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, null);
    }

    public static Transaction createFunctionCallTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, data);
    }

    public static Transaction createFunctionCallTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            String data) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, null, data);
    }

    public static Transaction createMcCallTransaction(String from, String to, String data) {

        return new Transaction(from, null, null, null, to, null, data);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }

    public String getValue() {
        return convert(value);
    }

    public String getData() {
        return data;
    }

    public String getNonce() {
        return convert(nonce);
    }

//    public String getSystemFlag() {
//        return systemFlag;
//    }
    public Integer getSystemFlag() {
        return systemFlag;
    } //test -- //Edit by Shidian Wang

    public String getVia() {
        return via;
    }

//    public String getShardingFlag() {
//        return shardingFlag;
//    }
    public Integer getShardingFlag() {
        return shardingFlag;
    } //test -- //Edit by Shidian Wang

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null;  // we don't want the field to be encoded if not present
        }
    }
}
