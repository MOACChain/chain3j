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

import java.io.IOException;

public class ScsGetBlock extends Response<ScsGetBlock.Block> {
//    @Override
//    @JsonDeserialize(using = ScsGetBlock.);
    public Block getBlock() {
        return getResult();
    }

    public static class Block{
        private String extraData;
        private String hash;
        private String number;
        private String parentHash;
        private String receiptsRoot;
        private String stateRoot;
        private String timestamp;
        private String transactions;

        public String getExtraData() {
            return extraData;
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

        public String getNumber() {
            return number;
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

        public String getTransactions() {
            return transactions;
        }

        public void setTransactions(String transactions) {
            this.transactions = transactions;
        }

        public String getTransactionsRoot() {
            return transactionsRoot;
        }

        public void setTransactionsRoot(String transactionsRoot) {
            this.transactionsRoot = transactionsRoot;
        }

        private String transactionsRoot;

        public Block(){

        }

        public Block(String extraData, String hash, String number, String parentHash,
                     String receiptsRoot, String stateRoot, String timestamp,
                     String transactions, String transactionsRoot){
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
    }


    public static class ResponseDeserialiser extends JsonDeserializer<ScsGetBlock.Block> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsGetBlock.Block deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsGetBlock.Block.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
