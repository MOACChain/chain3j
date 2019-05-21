package org.chain3j.protocol.scs.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ScsGetExchangeInfo extends Response<ScsGetExchangeInfo.Exchange> {
    @Override
    @JsonDeserialize(using = ScsGetExchangeByAddress.ResponseDeserialiser.class)
    public void setResult(Exchange result) {
        super.setResult(result);
    }

    public Exchange getBlock() {
        return getResult();
    }

    public static class Exchange{
        private String depositRecordCount;
        private List<Object> depositRecords;
        private String depositingRecordCount;
        private List<Object> depositingRecords;
        private String withdrawRecordCount;
        private List<Object> withdrawRecords;
        private String withdrawingRecordCount;
        private List<Object> withdrawingRecords;
        private String microChain;
        private String sender;

        public String getDepositRecordCountRaw() {
            return depositRecordCount;
        }

        public BigInteger getDepositRecordCount(){
            return Numeric.decodeQuantity(depositRecordCount);
        }

        public void setDepositRecordCount(String depositRecordCount) {
            this.depositRecordCount = depositRecordCount;
        }

        public List<Object> getDepositRecords() {
            return depositRecords;
        }

        public void setDepositRecords(List<Object> depositRecords) {
            this.depositRecords = depositRecords;
        }

        public String getDepositingRecordCountRaw() {
            return depositingRecordCount;
        }

        public BigInteger getDepositingRecordCount(){
            return Numeric.decodeQuantity(depositingRecordCount);
        }

        public void setDepositingRecordCount(String depositingRecordCount) {
            this.depositingRecordCount = depositingRecordCount;
        }

        public List<Object> getDepositingRecords() {
            return depositingRecords;
        }

        public void setDepositingRecords(List<Object> depositingRecords) {
            this.depositingRecords = depositingRecords;
        }

        public String getWithdrawRecordCountRaw() {
            return withdrawRecordCount;
        }

        public BigInteger getWithdrawRecordCount(){
            return Numeric.decodeQuantity(withdrawRecordCount);
        }

        public void setWithdrawRecordCount(String withdrawRecordCount) {
            this.withdrawRecordCount = withdrawRecordCount;
        }

        public List<Object> getWithdrawRecords() {
            return withdrawRecords;
        }

        public void setWithdrawRecords(List<Object> withdrawRecords) {
            this.withdrawRecords = withdrawRecords;
        }

        public String getWithdrawingRecordCountRaw() {
            return withdrawingRecordCount;
        }

        public BigInteger getWithdrwawingRecordCount(){
            return Numeric.decodeQuantity(withdrawingRecordCount);
        }

        public void setWithdrawingRecordCount(String withdrawingRecordCount) {
            this.withdrawingRecordCount = withdrawingRecordCount;
        }

        public List<Object> getWithdrawingRecords() {
            return withdrawingRecords;
        }

        public void setWithdrawingRecords(List<Object> withdrawingRecords) {
            this.withdrawingRecords = withdrawingRecords;
        }

        public String getMicroChain() {
            return microChain;
        }

        public void setMicroChain(String microChain) {
            this.microChain = microChain;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Exchange> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Exchange deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Exchange.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
