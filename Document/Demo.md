# Demo Description
### Demo List:
- BaseDemo: 
- ContractDemo:
- WalletDemo:  (will be uploaded soon)

## BaseDemo:
- BaseDemoApplication
- BaseFunctionDemo

## ContractDemo
- Greet Smart Contract

### Greet Smart Contract
Deploy the contract on Wallet.
``` solidiy
pragma solidity ^0.4.2;
   
contract Mortal {
    ...
}
   
contract Greeter is Mortal {
    string greeting;
     
    ...
       
    /* main function */
    function greet() public view returns (string) {
        return greeting;
    }
    
    function getGreeting() public view returns (string){
        return greeting;
    }
    
    ...
}

```
Create a Java Bean Class corresponding to the contract
```java
public class GreeterContract extends Contract {
    public GretterContract(){
        //...
    }
    
    public RemoteCall<Utf8String> greet(){
        //...
    }
    
    public RemoteCall<TransactionReceipt> newGreeting(Utf8String _greeting){
        //...
    }
    
}
```

> Contract class constructor
```java
public GreeterContract(
            String  contractBinary,
            String contractAddress, Chain3j chain3j, Credentials credentials,
            ContractGasProvider provider
    ){
        super(contractBinary,contractAddress,chain3j, credentials,provider);
    }
```

> Create functions corresponding to the functions in contract
```java
    public RemoteCall<Utf8String> greet(){
        Function function = new Function("greet",
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(
                new TypeReference<Utf8String>() {
                }));
        try
        {
            return executeRemoteCallSingleValueReturn(function);
        } catch (Exception e) {
            System.out.println("basedemo greet exception:" + e);
            return null;
        }
```
```java
public RemoteCall<TransactionReceipt> newGreeting(Utf8String _greeting){
        Function function = new Function( "newGreeting",
                Arrays.<Type>asList(_greeting),
                Collections.<TypeReference<?>>emptyList()
                );
        return executeRemoteCallTransaction(function);
    }
```

Calling the Contract function
> Connect to the chain
```java
    Chain3j chain3j = Chain3j.build(new HttpService(
                    "http://127.0.0.1:8545"));
```

> Create an object of contract class('GreeterContract')
```java
GreeterContract greeterContract = new GreeterContract(contractBinary,contractAddress,chain3j,credentials,gasProvider);
```

> Call the function
```java
 greeterContract.newGreeting(new Utf8String(/*string what you wanna enter*/)).send();

System.out.println("test123321 "+greeterContract.greet().send());
```


