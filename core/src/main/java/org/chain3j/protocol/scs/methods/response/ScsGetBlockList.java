package org.chain3j.protocol.scs.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Response;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ScsGetBlockList extends Response<ScsGetBlockList.BlockList> {
    @Override
    @JsonDeserialize(using = ScsGetBlockList.ResponseDeserialiser.class)
    public void setResult(BlockList result){
        super.setResult(result);
    }

    public BlockList getBlockList(){
        return getResult();
    }

    public static class BlockList{
        private List<ScsGetBlock.Block> blockList;
        private String endBlk;
        private String startBlk;
        private String microchainAddress;

        public List<ScsGetBlock.Block> getBlockList() {
            return blockList;
        }

        public void setBlockList(List<ScsGetBlock.Block> blockList) {
            this.blockList = blockList;
        }

        public String getEndBlkRaw() {
            return endBlk;
        }

        public BigInteger getEndBlk(){
            return Numeric.decodeQuantity(endBlk);
        }

        public void setEndBlk(String endBlk) {
            this.endBlk = endBlk;
        }

        public String getStartBlkRaw() {
            return startBlk;
        }

        public BigInteger getStartBlk(){
            return Numeric.decodeQuantity(startBlk);
        }

        public void setStartBlk(String startBlk) {
            this.startBlk = startBlk;
        }

        public String getMicrochainAddress() {
            return microchainAddress;
        }

        public void setMicrochainAddress(String microchainAddress) {
            this.microchainAddress = microchainAddress;
        }

        public BlockList(){}

        public BlockList(List<ScsGetBlock.Block> blockList, String startBlk, String endBlk,
                         String microchainAddress){
            this.blockList = blockList;
            this.startBlk = startBlk;
            this.endBlk = endBlk;
            this.microchainAddress = microchainAddress;
        }

        @Override
        public boolean equals(Object o){
            if (this == o){
                return true;
            }
            if (!(o instanceof BlockList)){
                return false;
            }

            BlockList blockList = (BlockList) o;

            if (getEndBlkRaw() != null
                ? !getEndBlkRaw().equals(blockList.getEndBlkRaw())
                : blockList.getEndBlkRaw() != null){
                return false;
            }
            if (getStartBlkRaw() != null
                ? !getStartBlkRaw().equals(blockList.getStartBlkRaw())
                : blockList.getStartBlk() != null){
                return false;
            }
            if (getMicrochainAddress() != null
                ? !getMicrochainAddress().equals(blockList.getMicrochainAddress())
                : blockList.getMicrochainAddress() != null){
                return false;
            }

            return getBlockList() != null ? getBlockList().equals(blockList.getBlockList()) : blockList.getBlockList() != null;
        }

    }

//    public interface BlockResult<T> {
//        T get();
//    }



    public static class ResponseDeserialiser extends JsonDeserializer<ScsGetBlockList.BlockList> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsGetBlockList.BlockList deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsGetBlockList.BlockList.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }

}
