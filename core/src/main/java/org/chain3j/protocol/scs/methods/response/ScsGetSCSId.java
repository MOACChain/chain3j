package org.chain3j.protocol.scs.methods.response;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

public class ScsGetSCSId extends Response<String> {
    public String getSCSId(){
        return getResult();
    }
}
