package org.chain3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.McFilter;
import org.chain3j.protocol.core.methods.response.McLog;

/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(Chain3j chain3j, Callback<String> callback) {
        super(chain3j, callback);
    }

    @Override
    McFilter sendRequest() throws IOException {
        return chain3j.mcNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<McLog.LogResult> logResults) {
        for (McLog.LogResult logResult : logResults) {
            if (logResult instanceof McLog.Hash) {
                String transactionHash = ((McLog.Hash) logResult).get();
                callback.onEvent(transactionHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     * @param filterId
     * Id of the filter for which the historic log should be retrieved
     * @return
     * Optional.empty()
     */
    @Override
    protected Optional<Request<?, McLog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}

