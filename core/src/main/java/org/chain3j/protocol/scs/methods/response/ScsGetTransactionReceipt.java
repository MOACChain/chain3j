package org.chain3j.protocol.scs.methods.response;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;

/**
 * ScsGetTransactionReceipt return a ScsTransaction
 * receipt type.
 */
public class ScsGetTransactionReceipt extends Response<ScsTransactionReceipt> {

    public Optional<ScsTransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(getResult());
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ScsTransactionReceipt> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsTransactionReceipt deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsTransactionReceipt.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}

