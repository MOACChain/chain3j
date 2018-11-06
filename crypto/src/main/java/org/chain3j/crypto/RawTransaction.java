package org.chain3j.crypto;

import java.math.BigInteger;

import org.chain3j.utils.Numeric;

/**
 * Transaction class used for signing MOAC transactions locally.<br>
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
    // private Integer chainId;
    private String systemFlag;//Always 0
    private String shardingFlag;// 0 - MotherChain TX, 1 - Microchain TX
    private String via;// Vnode address to send the TX to MicroChains

    protected RawTransaction(BigInteger nonce, 
            BigInteger gasPrice, 
            BigInteger gasLimit,
            String to, 
            BigInteger value,
            String data, 
            String shardingFlag,
            String via) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;
        this.shardingFlag = shardingFlag;
        this.via = via;

        if (data != null) {
            this.data = Numeric.cleanHexPrefix(data);
            // this.data = data;
        }

        // SystemFlag should always be 0
        this.systemFlag = "";
    }

    //Transfer MC only, no data 
    
    public static RawTransaction createMcTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value) {
        String shardingFlag = "";
        String via = "";
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "", shardingFlag, via);

    }
    // For transaction to the MotherChain
    // Add shardingFlag and via
    
    public static RawTransaction createTransaction(BigInteger nonce, 
            BigInteger gasPrice, BigInteger gasLimit,
            String to, BigInteger value,
            String data) {
        String shardingFlag = "";
        String via = "";
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, shardingFlag, via);
    }


    // For MicroChain input, need shardingFlag = '0x1'
    
    public static RawTransaction createTransaction(BigInteger nonce, 
            BigInteger gasPrice, BigInteger gasLimit,
            String to, BigInteger value,
            String data, String shardingFlag,
            String via) {
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, shardingFlag, via);
    }

    // Create MotherChain contract
    // No need to add des address
    
    public static RawTransaction createContractTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, 
            BigInteger value,
            String init) {
        String shardingFlag = "";
        String via = "";
        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init, shardingFlag, via);
    }

    // Create MicroChain contract with valid shardingFlag = 1, via = VNODE address.
    
    public static RawTransaction createContractTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, BigInteger value,
            String init, String shardingFlag, String via) {

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

    public String getShardingFlag() {
        return  shardingFlag;
    }

    public String getSystemFlag() {
        return  systemFlag;
    }

    public String getVia() {
        return via;
    }
}
