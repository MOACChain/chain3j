package org.chain3j.protocol.core.methods.request;

import org.chain3j.utils.Numeric;

public class SCSTransaction {
    private String from;
    private String to;
    private String data;

    public SCSTransaction(String from, String to, String data){
        this.from = from;
        this.to = to;

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }
    }
}
