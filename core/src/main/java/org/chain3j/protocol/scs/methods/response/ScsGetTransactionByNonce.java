package org.chain3j.protocol.scs.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;

import java.io.IOException;
import java.util.List;

public class ScsGetTransactionByNonce extends Response<ScsGetTransactionByNonce.TransactionByNonce> {
    @Override
    @JsonDeserialize(using = ScsGetTransactionByHash.ResponseDeserialiser.class)
    public void setResult(TransactionByNonce result){
        super.setResult(result);
    }

    public TransactionByNonce getResult(){
        return getResult();
    }

    public static class TransactionByNonce{
        public List<Logs> getLogs() {
            return logsList;
        }

        public void setLogs(List<Logs> logsList) {
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

        public List<Logs> getLogsList() {
            return logsList;
        }

        public void setLogsList(List<Logs> logsList) {
            this.logsList = logsList;
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

        public String getTransactionHash() {
            return transactionHash;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        private List<Logs> logsList; //should be change
        private String logsBloom;
        private String status;
        private String transactionHash;
        private String contractAddress;
        private boolean failed;

        public TransactionByNonce(){

        }

        public TransactionByNonce(List<Logs> logsList, String logsBloom, String status, String transactionHash,
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
//            if (!(o instanceof TransactionByNonce)){
//                return false;
//            }
//
//            TransactionByNonce result = (TransactionByNonce) o;
//
//            if (getLogs() != null
//                    ? !getLogs().equals(result.getLogs())
//                    : result.getLogs() != null){
//                return false;
//            }
//            if (getLogsBloom() != null
//                    ? !getLogsBloom().equals(result.getLogsBloom())
//                    : result.getLogsBloom() != null){
//                return false;
//            }
//
//            return getTransactionHash() != null
//                    ? getTransactionHash().equals(result.getTransactionHash()) : result.getTransactionHash() != null;
//        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ScsGetTransactionByNonce.TransactionByNonce> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsGetTransactionByNonce.TransactionByNonce deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsGetTransactionByNonce.TransactionByNonce.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
