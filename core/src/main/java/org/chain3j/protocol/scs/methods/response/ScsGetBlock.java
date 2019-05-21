package org.chain3j.protocol.scs.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.chain3j.abi.datatypes.Bool;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ScsGetBlock extends Response<ScsGetBlock.Block> {
    @Override
    @JsonDeserialize(using = ScsGetBlock.ResponseDeserialiser.class)
    public void setResult(Block result) {
        super.setResult(result);
    }

    public Block getBlock() {
        return getResult();
    }

    public static class Block{
        private String extraData;
        private String hash;
        private String miner;
        private String number;
        private String parentHash;
        private String receiptsRoot;
        private String stateRoot;
        private String timestamp;
        private List<String> transactions;
        private String transactionsRoot;

        public String getExtraDataRaw() {
            return extraData;
        }

        public BigInteger getExtraData(){
            return Numeric.decodeQuantity(extraData);
        }

        public void setExtraData(String extraData) {
            this.extraData = extraData;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getNumberRaw() {
            return number;
        }

        public BigInteger getNumber(){
            return Numeric.decodeQuantity(number);
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getParentHash() {
            return parentHash;
        }

        public void setParentHash(String parentHash) {
            this.parentHash = parentHash;
        }

        public String getReceiptsRoot() {
            return receiptsRoot;
        }

        public void setReceiptsRoot(String receiptsRoot) {
            this.receiptsRoot = receiptsRoot;
        }

        public String getStateRoot() {
            return stateRoot;
        }

        public void setStateRoot(String stateRoot) {
            this.stateRoot = stateRoot;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public List<String> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<String> transactions) {
            this.transactions = transactions;
        }

        public String getTransactionsRoot() {
            return transactionsRoot;
        }

        public void setTransactionsRoot(String transactionsRoot) {
            this.transactionsRoot = transactionsRoot;
        }


        public String getMiner() {
            return miner;
        }

        public void setMiner(String miner) {
            this.miner = miner;
        }

        public Block() {

        }

        public Block(String extraData, String hash, String number, String parentHash,
                     String receiptsRoot, String stateRoot, String timestamp,
                     List<String> transactions, String transactionsRoot){
            this.extraData = extraData;
            this.hash = hash;
            this.number = number;
            this.parentHash = parentHash;
            this.receiptsRoot = receiptsRoot;
            this.stateRoot = stateRoot;
            this.timestamp = timestamp;
            this.transactions = transactions;
            this.transactionsRoot = transactionsRoot;
        }

        @Override
        public boolean equals(Object o){
            if (this == o){
                return true;
            }
            if (!(o instanceof Block)){
                return false;
            }

            Block block = (Block)o;

            if (getExtraDataRaw() != null
                ? !getExtraDataRaw().equals(block.getExtraDataRaw())
                : block.getExtraDataRaw() != null){
                return false;
            }
            if (getHash() != null
                ? !getHash().equals(block.getHash())
                : block.getHash() != null){
                return false;
            }
            if (getMiner() != null
                ? !getMiner().equals(block.getMiner())
                : block.getMiner() != null){
                return false;
            }
            if (getNumberRaw() != null
                ? !getNumberRaw().equals(block.getNumberRaw())
                : block.getNumberRaw() != null){
                return false;
            }
            if (getParentHash() != null
                ? !getParentHash().equals(block.getParentHash())
                : block.getParentHash() != null){
                return false;
            }
            if (getReceiptsRoot() != null
                ? !getReceiptsRoot().equals(block.getReceiptsRoot())
                : block.getReceiptsRoot() != null){
                return false;
            }
            if (getStateRoot() != null
                ? !getStateRoot().equals(block.getStateRoot())
                : block.getStateRoot() != null){
                return false;
            }
            if (getTimestamp() != null
                ? !getTimestamp().equals(block.getTimestamp())
                : block.getTimestamp() != null){
                return false;
            }
            if (getTransactionsRoot() != null
                ? !getTransactionsRoot().equals(block.getTransactionsRoot())
                : block.getTransactionsRoot() != null){
                return false;
            }

            return getTransactions() != null
                    ? getTransactions().equals(block.getTransactions()) : block.getTransactions() == null;

        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Block> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Block deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Block.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }


}
