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

public class ScsGetExchangeByAddress extends Response<ScsGetExchangeByAddress.Exchange> {
    @Override
    @JsonDeserialize(using = ScsGetExchangeByAddress.ResponseDeserialiser.class)
    public void setResult(Exchange result) {
        super.setResult(result);
    }

    public Exchange getBlock() {
        return getResult();
    }

    public static class Exchange{
        private int depositRecordCount;
        private List<ExchangeDetail.DepositRecord> depositRecords;
        private int depositingRecordCount;
        private List<ExchangeDetail.DepositingRecord> depositingRecords;
        private int withdrawRecordCount;
        private List<ExchangeDetail.WithdrawRecord> withdrawRecords;
        private int withdrawingRecordCount;
        private List<ExchangeDetail.WithdrawingRecord> withdrawingRecords;
        private String microChain;
        private String sender;

        public int getDepositRecordCount() {
            return depositRecordCount;
        }

//        public BigInteger getDepositRecordCount(){
//            return Numeric.decodeQuantity(depositRecordCount);
//        }

        public void setDepositRecordCount(int depositRecordCount) {
            this.depositRecordCount = depositRecordCount;
        }

        public List<ExchangeDetail.DepositRecord> getDepositRecords() {
            return depositRecords;
        }

        public void setDepositRecords(List<ExchangeDetail.DepositRecord> depositRecords) {
            this.depositRecords = depositRecords;
        }

        public int getDepositingRecordCountRaw() {
            return depositingRecordCount;
        }

//        public BigInteger getDepositingRecordCount(){
//            return Numeric.decodeQuantity(depositingRecordCount);
//        }

        public void setDepositingRecordCount(int depositingRecordCount) {
            this.depositingRecordCount = depositingRecordCount;
        }

        public List<ExchangeDetail.DepositingRecord> getDepositingRecords() {
            return depositingRecords;
        }

        public void setDepositingRecords(List<ExchangeDetail.DepositingRecord> depositingRecords) {
            this.depositingRecords = depositingRecords;
        }

        public int getWithdrawRecordCountRaw() {
            return withdrawRecordCount;
        }

//        public BigInteger getWithdrawRecordCount(){
//            return Numeric.decodeQuantity(withdrawRecordCount);
//        }

        public void setWithdrawRecordCount(int withdrawRecordCount) {
            this.withdrawRecordCount = withdrawRecordCount;
        }

        public List<ExchangeDetail.WithdrawRecord> getWithdrawRecords() {
            return withdrawRecords;
        }

        public void setWithdrawRecords(List<ExchangeDetail.WithdrawRecord> withdrawRecords) {
            this.withdrawRecords = withdrawRecords;
        }

        public int getWithdrawingRecordCountRaw() {
            return withdrawingRecordCount;
        }

//        public BigInteger getWithdrwawingRecordCount(){
//            return Numeric.decodeQuantity(withdrawingRecordCount);
//        }

        public void setWithdrawingRecordCount(int withdrawingRecordCount) {
            this.withdrawingRecordCount = withdrawingRecordCount;
        }

        public List<ExchangeDetail.WithdrawingRecord> getWithdrawingRecords() {
            return withdrawingRecords;
        }

        public void setWithdrawingRecords(List<ExchangeDetail.WithdrawingRecord> withdrawingRecords) {
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
