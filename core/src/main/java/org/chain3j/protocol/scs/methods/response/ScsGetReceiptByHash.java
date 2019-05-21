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

import java.io.IOException;
import java.util.List;

public class ScsGetReceiptByHash  extends Response<ScsGetReceiptByHash.Result> {
    @Override
    @JsonDeserialize(using = ScsGetReceiptByHash.ResponseDeserialiser.class)
    public void setResult(Result result){
        super.setResult(result);
    }

    public Result getResult(){
        return getResult();
    }

    public static class Result{
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

        public Result(){

        }

        public Result(List<Logs> logsList, String logsBloom, String status, String transactionHash){
            this.logsList = logsList;
            this.logsBloom = logsBloom;
            this.status = status;
            this.transactionHash = transactionHash;
        }

        @Override
        public boolean equals(Object o){
            if (this == o){
                return true;
            }
            if (!(o instanceof Result)){
                return false;
            }

            Result result = (Result) o;

            if (getLogs() != null
                ? !getLogs().equals(result.getLogs())
                : result.getLogs() != null){
                return false;
            }
            if (getLogsBloom() != null
                ? !getLogsBloom().equals(result.getLogsBloom())
                : result.getLogsBloom() != null){
                return false;
            }

            return getTransactionHash() != null
                    ? getTransactionHash().equals(result.getTransactionHash()) : result.getTransactionHash() != null;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Result> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Result deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Result.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
