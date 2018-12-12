package org.chain3j.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

import rx.Observable;

import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.Response;
import org.chain3j.protocol.websocket.events.Notification;
import org.chain3j.utils.Async;

/**
 * Base service implementation.
 */
public abstract class Service implements Chain3jService {

    protected final ObjectMapper objectMapper;

    public Service(boolean includeRawResponses) {
        objectMapper = ObjectMapperFactory.getObjectMapper(includeRawResponses);
    }

    protected abstract InputStream performIO(String payload) throws IOException;

    @Override
    public <T extends Response> T send(
            Request request, Class<T> responseType) throws IOException {
        String payload = objectMapper.writeValueAsString(request);

        //Debugging use, print out the JSON string send to the server
        System.out.println("Service.java => Send:" + payload);
        try (InputStream result = performIO(payload)) {
            if (result != null) {
                return objectMapper.readValue(result, responseType);
            } else {
                return null;
            }
        }
    }

    @Override
    public <T extends Response> CompletableFuture<T> sendAsync(
            Request jsonRpc20Request, Class<T> responseType) {
        return Async.run(() -> send(jsonRpc20Request, responseType));
    }

    @Override
    public <T extends Notification<?>> Observable<T> subscribe(
            Request request,
            String unsubscribeMethod,
            Class<T> responseType) {
        throw new UnsupportedOperationException(
                String.format(
                        "Service %s does not support subscriptions",
                        this.getClass().getSimpleName()));
    }
}
