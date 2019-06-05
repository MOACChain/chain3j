package org.chain3j.protocol.scs.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

public class ScsGetExchangeInfo extends Response<ScsGetExchangeInfo.Exchange> {
    @Override
    @JsonDeserialize(using = ScsGetExchangeInfo.ResponseDeserialiser.class)
    public void setResult(Exchange result) {
        super.setResult(result);
    }

    public Exchange getExchange() {
        return getResult();
    }

    public static class Exchange {
        private int depositingRecordCount;
        private List<ExchangeDetail.DepositingRecord> depositingRecords;
        private String withdrawingRecordCount;
        private List<ExchangeDetail.WithdrawingRecord> withdrawingRecords;
        private String microchain;
        private String scsid;

        public Exchange() {

        }

        public Exchange(int depositingRecordCount, List<ExchangeDetail.DepositingRecord> depositingRecords,
                        String withdrawingRecordCount,
            List<ExchangeDetail.WithdrawingRecord> withdrawingRecords, String microchain, String scsid) {
            this.depositingRecordCount = depositingRecordCount;
            this.depositingRecords = depositingRecords;
            this.withdrawingRecordCount = withdrawingRecordCount;
            this.withdrawingRecords = withdrawingRecords;
            this.microchain = microchain;
            this.scsid = scsid;
        }

        public int getDepositingRecordCount() {
            return depositingRecordCount;
        }

        public void setDepositingRecordCount(int depositingRecordCount) {
            this.depositingRecordCount = depositingRecordCount;
        }

        public List<ExchangeDetail.DepositingRecord> getDepositingRecords() {
            return depositingRecords;
        }

        public void setDepositingRecords(List<ExchangeDetail.DepositingRecord> depositingRecords) {
            this.depositingRecords = depositingRecords;
        }

        public String getWithdrawingRecordCountRaw() {
            return withdrawingRecordCount;
        }

        public BigInteger getWithdrwawingRecordCount() {
            return Numeric.decodeQuantity(withdrawingRecordCount);
        }

        public void setWithdrawingRecordCount(String withdrawingRecordCount) {
            this.withdrawingRecordCount = withdrawingRecordCount;
        }

        public List<ExchangeDetail.WithdrawingRecord> getWithdrawingRecords() {
            return withdrawingRecords;
        }

        public void setWithdrawingRecords(List<ExchangeDetail.WithdrawingRecord> withdrawingRecords) {
            this.withdrawingRecords = withdrawingRecords;
        }

        public String getMicrochain() {
            return microchain;
        }

        public void setMicroChain(String microchain) {
            this.microchain = microchain;
        }

        public String getScsid() {
            return scsid;
        }

        public void setScsid(String scsid) {
            this.scsid = scsid;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ScsGetExchangeInfo.Exchange> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsGetExchangeInfo.Exchange deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsGetExchangeInfo.Exchange.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
