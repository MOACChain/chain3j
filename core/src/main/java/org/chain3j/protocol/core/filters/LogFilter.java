package org.chain3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.Log;
import org.chain3j.protocol.core.methods.response.McFilter;
import org.chain3j.protocol.core.methods.response.McLog;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private final org.chain3j.protocol.core.methods.request.McFilter mcFilter;

    public LogFilter(
            Chain3j chain3j, Callback<Log> callback,
            org.chain3j.protocol.core.methods.request.McFilter mcFilter) {
        super(chain3j, callback);
        this.mcFilter = mcFilter;
    }


    @Override
    McFilter sendRequest() throws IOException {
        return chain3j.mcNewFilter(mcFilter).send();
    }

    @Override
    void process(List<McLog.LogResult> logResults) {
        for (McLog.LogResult logResult : logResults) {
            if (logResult instanceof McLog.LogObject) {
                Log log = ((McLog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Optional<Request<?, McLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(chain3j.mcGetFilterLogs(filterId));
    }
}
