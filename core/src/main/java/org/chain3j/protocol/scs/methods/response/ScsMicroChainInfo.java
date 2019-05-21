package org.chain3j.protocol.scs.methods.response;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * MicroChaib Info object returned by:
 * <ul>
 * <li>scc_getMicroChainInfo</li>
 * </ul>
 *
 * <p>See
 * <a href="https://github.com/MOACChain/moac-core/wiki/JSON-RPC#scs_getmicrochaininfo">docs</a>
 * for further details.</p>
 * "balance":"0x0",
 * "blockReward":"0x1c6bf52634000",
 * "bondLimit":"0xde0b6b3a7640000",
 * "owner":"0xa8863fc8Ce3816411378685223C03DAae9770ebB",
 * "scsList":["0xECd1e094Ee13d0B47b72F5c940C17bD0c7630326",
 * "0x50C1
 * 5fafb95968132d1a6ee3617E99cCa1FCF059",
 * "0x1b65cE1A393FFd5960D2ce11E7fd6fDB9e991945"],
 * "txReward":"0x174876e800",
 * "viaReward":"0x9184e72a000"
 */
public class ScsMicroChainInfo extends Response<ScsMicroChainInfo.ChainInfo> {

    @Override
    @JsonDeserialize(using = ScsMicroChainInfo.ResponseDeserialiser.class)
    public void setResult(ChainInfo result) {
        super.setResult(result);
    }

    public ChainInfo getChainInfo() {
        return getResult();
    }

    // If a MicroChain defined a MicroChain token, balance is a String of Hex with a digit -18.
    // bondLimit, Unit in Sha, 1 MOAC = 1E+18 Sha   
    // A list of SCSIDs in the MicroChain, this only includes the most recent active SCSs, 
    // monitors won't be included??

    public static class ChainInfo {
        private String balance; 
        private String blockReward;
        private String bondLimit; 
        private String owner;
        private List<String> scsList;
        private String txReward;
        private String viaReward;
        
        public ChainInfo() {
        }

        public ChainInfo(String balance, String blockReward, 
                String bondLimit, String owner, 
                List<String> scsList, String txReward,String viaReward) {
            this.balance = balance;
            this.blockReward = blockReward;
            this.bondLimit = bondLimit;
            this.scsList = scsList;
            this.txReward = txReward;
            this.viaReward = viaReward;
        }

        public BigInteger getBalance() {
            return Numeric.decodeQuantity(balance);
        }

        public String getBalanceRaw() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public BigInteger getBlockReward() {
            return Numeric.decodeQuantity(blockReward);
        }

        public String getBlockRewardRaw() {
            return blockReward;
        }

        public void setBlockReward(String blockReward) {
            this.blockReward = blockReward;
        }

        public BigInteger getBondLimit() {
            return Numeric.decodeQuantity(bondLimit);
        }

        public String getBondLimitRaw() {
            return bondLimit;
        }

        public void setBondLimit(String bondLimit) {
            this.bondLimit = bondLimit;
        }


        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public List<String> getScsList() {
            return scsList;
        }

        public void setScsList(List<String> scsList) {
            this.scsList = scsList;
        }


        public BigInteger getTxReward() {
            return Numeric.decodeQuantity(txReward);
        }

        public String getTxRewardRaw() {
            return txReward;
        }

        public void setTxReward(String txReward) {
            this.txReward = txReward;
        }

        public BigInteger getViaReward() {
            return Numeric.decodeQuantity(viaReward);
        }

        public String getViaRewardRaw() {
            return viaReward;
        }

        public void setViaReward(String viaReward) {
            this.viaReward = viaReward;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ChainInfo)) {
                return false;
            }

            ChainInfo chainInfo = (ChainInfo) o;

            if (getBalanceRaw() != null
                    ? !getBalanceRaw().equals(chainInfo.getBalanceRaw())
                    : chainInfo.getBalanceRaw() != null) {
                return false;
            }
            if (getBlockRewardRaw() != null
                    ? !getBlockRewardRaw().equals(chainInfo.getBlockRewardRaw())
                    : chainInfo.getBlockRewardRaw() != null) {
                return false;
            }
            if (getBondLimitRaw() != null
                    ? !getBondLimitRaw().equals(chainInfo.getBondLimitRaw())
                    : chainInfo.getBondLimitRaw() != null) {
                return false;
            }
            if (getOwner() != null
                    ? !getOwner().equals(chainInfo.getOwner()) : chainInfo.getOwner() != null) {
                return false;
            }

            if (getTxRewardRaw() != null
                    ? !getTxRewardRaw().equals(chainInfo.getTxRewardRaw())
                    : chainInfo.getTxRewardRaw() != null) {
                return false;
            }

            if (getViaRewardRaw() != null
                    ? !getViaRewardRaw().equals(chainInfo.getViaRewardRaw())
                    : chainInfo.getViaRewardRaw() != null) {
                return false;
            }

            return getScsList() != null
                    ? getScsList().equals(chainInfo.getScsList()) : chainInfo.getScsList() == null;

        }


    }
    
    public static class ResponseDeserialiser extends JsonDeserializer<ChainInfo> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ChainInfo deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ChainInfo.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
