package org.chain3j.protocol.scs.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.tx.Contract;

import java.io.IOException;
import java.util.List;

public class ScsGetReceiptByHash  extends Response<ScsGetReceiptByHash.ReceiptByHash> {
    @Override
    @JsonDeserialize(using = ScsGetReceiptByHash.ResponseDeserialiser.class)
    public void setResult(ReceiptByHash result){
        super.setResult(result);
    }

    public ReceiptByHash getReceiptByHash(){
        return getResult();
    }

    public static class ReceiptByHash{
        private List<Logs> logsList;
        private String logsBloom;
        private String status;
        private String transactionHash;
        private String contractAddress;
        private boolean failed;

        public List<Logs> getLogsList() {
            return logsList;
        }

        public void setLogsList(List<Logs> logsList) {
            this.logsList = logsList;
        }

        public String getLogsBloom() {
            return logsBloom;
        }

        public void setLogsBloom(String logsBloom) {
            this.logsBloom = logsBloom;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public String getContractAddress() {
            return contractAddress;
        }

        public void setContractAddress(String contractAddress) {
            this.contractAddress = contractAddress;
        }

        public boolean isFailed() {
            return failed;
        }

        public void setFailed(boolean failed) {
            this.failed = failed;
        }

        public ReceiptByHash(){

        }

        public ReceiptByHash(List<Logs> logsList, String logsBloom, String status, String transactionHash,
                      String contractAddress, boolean failed){
            this.logsList = logsList;
            this.logsBloom = logsBloom;
            this.status = status;
            this.transactionHash = transactionHash;
            this.contractAddress = contractAddress;
            this.failed = failed;
        }

//        @Override
//        public boolean equals(Object o){
//            if (this == o){
//                return true;
//            }
//            if (!(o instanceof ReceiptByHash)){
//                return false;
//            }
//
//            ReceiptByHash result = (ReceiptByHash) o;
//
//            if (getLogsBloom() != null
//                ? !getLogsBloom().equals(result.getLogsBloom())
//                : result.getLogsBloom() != null){
//                return false;
//            }
//
//            return getTransactionHash() != null
//                    ? getTransactionHash().equals(result.getTransactionHash()) : result.getTransactionHash() != null;
//        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ScsGetReceiptByHash.ReceiptByHash> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsGetReceiptByHash.ReceiptByHash deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsGetReceiptByHash.ReceiptByHash.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
