package org.chain3j.protocol.scs.methods.response;

import org.chain3j.abi.datatypes.Bool;
import org.chain3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class Logs {
    private String address;
    private List<String> topics;
    private String data;
    private String blockNumber;
    private String transactionHash;
    private String transactionIndex;
    private String blockHash;
    private String logIndex;
    private boolean removed;

    public Logs(){}

    public Logs(String address, List<String> topics, String data, String blockNumber,
                String transactionHash, String transactionIndex, String blockHash,
                String logIndex, Boolean removed){
        this.address = address;
        this.topics = topics;
        this.data = data;
        this.blockNumber = blockNumber;
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.blockHash = blockHash;
        this.logIndex = logIndex;
        this.removed = removed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBlockNumberRaw() {
        return blockNumber;
    }

    public BigInteger getBlockNumber(){
        return Numeric.decodeQuantity(blockNumber);
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getTransactionIndexRaw() {
        return transactionIndex;
    }

    public BigInteger getTransactionIndex(){
        return Numeric.decodeQuantity(transactionIndex);
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getLogIndexRaw() {
        return logIndex;
    }

    public BigInteger getlogIndex(){
        return Numeric.decodeQuantity(logIndex);
    }

    public void setLogIndex(String logIndex) {
        this.logIndex = logIndex;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof Logs)){
            return false;
        }

        Logs logs = (Logs) o;

        if (getAddress() != null
            ? !getAddress().equals(logs.getAddress())
            : logs.getAddress() != null){
            return false;
        }
        if (getData() != null
            ? !getData().equals(logs.getData())
            : logs.getData() != null){
            return false;
        }
        if (getBlockNumberRaw() != null
            ? !getBlockNumberRaw().equals(logs.getBlockNumberRaw())
            : logs.getBlockNumberRaw() != null){
            return false;
        }
        if (getTransactionHash() != null
            ? !getTransactionHash().equals(logs.getTransactionHash())
            : logs.getTransactionHash() != null){
            return false;
        }
        if (getTransactionIndexRaw() != null
            ? !getTransactionIndexRaw().equals(logs.getTransactionIndexRaw())
            : logs.getTransactionIndexRaw() != null){
            return false;
        }
        if (getBlockHash() != null
            ? !getBlockHash().equals(logs.getBlockHash())
            : logs.getBlockHash() != null){
            return false;
        }
        if (getLogIndexRaw() != null
            ? !getLogIndexRaw().equals(logs.getLogIndexRaw())
            : logs.getLogIndexRaw() != null){
            return false;
        }

        return getTopics() != null ? getTopics().equals(logs.getTopics()) : logs.getTopics() != null;
    }
}
