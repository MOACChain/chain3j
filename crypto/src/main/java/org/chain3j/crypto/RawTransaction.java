package org.chain3j.crypto;

import java.math.BigInteger;

import org.chain3j.utils.Numeric;

/**
 * WalletDemo class used for signing MOAC transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">
 * yellow paper</a>.
 * To replace the RawTransaction.java in ETH
 * Encode the TX for signature
    type txdata struct {
    AccountNonce uint64          `json:"nonce"    gencodec:"required"`
    SystemContract uint64          `json:"syscnt" gencodec:"required"`
    Price        *big.Int        `json:"gasPrice" gencodec:"required"`
    GasLimit     *big.Int        `json:"gas"      gencodec:"required"`
        // nil means contract creation
    Amount       *big.Int        `json:"value"    gencodec:"required"`
    Payload      []byte          `json:"input"    gencodec:"required"`
    ShardingFlag uint64 `json:"shardingFlag" gencodec:"required"`
    Via            *common.Address `json:"to"       rlp:"nil"`

    // Signature values
    V *big.Int `json:"v" gencodec:"required"`
    R *big.Int `json:"r" gencodec:"required"`
    S *big.Int `json:"s" gencodec:"required"`
 */

public class RawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;
    
    //private String systemFlag;//Always 0
    private Integer systemFlag;//Edit by Shidian Wang
   // private String shardingFlag;// 0 - MotherChain TX, 1 - Microchain TX
   private Integer shardingFlag;//Edit by Shidian Wang
    private String via;// Vnode address to send the TX to MicroChains

    protected RawTransaction(BigInteger nonce, 
            BigInteger gasPrice, 
            BigInteger gasLimit,
            String to, 
            BigInteger value,
            String data, 
            Integer shardingFlag,
            String via) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;
        //this.shardingFlag = shardingFlag;
        //this.via = via;
        // use empty via address
        this.via=via;

        if (data != null) {
            this.data = Numeric.cleanHexPrefix(data);
        }

        // Set default to 0
        if (shardingFlag >= 0 ){
            this.shardingFlag = shardingFlag;
        }else{
            this.shardingFlag = 0; 
        }
        // SystemFlag should always be 0
        this.systemFlag = 0; 
    }

    //Transfer MC only, no data 
    public static RawTransaction createMcTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value) {
        Integer shardingFlag = 0;
        String via = "0x0000000000000000000000000000000000000000"; //note:
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "", shardingFlag, via);

    }
    // For transaction to the MotherChain
    // Set shardingFlag and via for Global TX
    
    public static RawTransaction createTransaction(BigInteger nonce, 
            BigInteger gasPrice, BigInteger gasLimit,
            String to, BigInteger value,
            String data) {
        Integer shardingFlag = 0;
        String via = "0x0000000000000000000000000000000000000000";
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, shardingFlag, via);
    }


    // For MicroChain input, set shardingFlag = 1, 
    public static RawTransaction createTransaction(BigInteger nonce, 
            BigInteger gasPrice, BigInteger gasLimit,
            String to, BigInteger value,
            String data, Integer shardingFlag,
            String via) {
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, shardingFlag, via);
    }

    // For MicroChain input, set shardingFlag = 1, 
    public static RawTransaction createMicroChainTransaction(BigInteger nonce, 
            BigInteger gasPrice, BigInteger gasLimit,
            String to, BigInteger value,
            String data, Integer shardingFlag,
            String via) {
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, shardingFlag, via);
    }

    // Create MotherChain Global contract
    // No need to add des address
    public static RawTransaction createContractTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, 
            BigInteger value,
            String init) {
        String via = "0x0000000000000000000000000000000000000000";
        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init, 0, via);
    }

    // Create MicroChain contract with valid shardingFlag = 1, via = VNODE address.
    
    public static RawTransaction createContractTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, BigInteger value,
            String init, Integer shardingFlag, String via) {

        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init, shardingFlag, via);
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public String getData() {
        return data;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public Integer getShardingFlag() {
        return shardingFlag;
    }

    public Integer getSystemFlag() {
        return systemFlag;
    }

    public String getVia() {
        return via;
    }
}
