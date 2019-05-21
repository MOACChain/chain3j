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
        private List<ScsGetBlock.Block> block;
        private String endBlock;
        private String startBlock;
        private String microchainAddress;

        public List<ScsGetBlock.Block> getBlock() {
            return block;
        }

        public void setBlock(List<ScsGetBlock.Block> block) {
            this.block = block;
        }

        public String getEndBlockRaw() {
            return endBlock;
        }

        public BigInteger getEndBlock(){
            return Numeric.decodeQuantity(endBlock);
        }

        public void setEndBlock(String endBlock) {
            this.endBlock = endBlock;
        }

        public String getStartBlockRaw() {
            return startBlock;
        }

        public BigInteger getStartBlock(){
            return Numeric.decodeQuantity(endBlock);
        }

        public void setStartBlock(String startBlock) {
            this.startBlock = startBlock;
        }

        public String getMicrochainAddress() {
            return microchainAddress;
        }

        public void setMicrochainAddress(String microchainAddress) {
            this.microchainAddress = microchainAddress;
        }

        public BlockList(){}

        public BlockList(List<ScsGetBlock.Block> block, String startBlock, String endBlock,
                         String microchainAddress){
            this.block = block;
            this.startBlock = startBlock;
            this.endBlock = endBlock;
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

            if (getEndBlockRaw() != null
                ? !getEndBlockRaw().equals(blockList.getEndBlockRaw())
                : blockList.getEndBlockRaw() != null){
                return false;
            }
            if (getStartBlockRaw() != null
                ? !getStartBlockRaw().equals(blockList.getStartBlockRaw())
                : blockList.getStartBlock() != null){
                return false;
            }
            if (getMicrochainAddress() != null
                ? !getMicrochainAddress().equals(blockList.getMicrochainAddress())
                : blockList.getMicrochainAddress() != null){
                return false;
            }

            return getBlock() != null ? getBlock().equals(blockList.getBlock()) : blockList.getBlock() != null;
        }

    }

//    public interface BlockResult<T> {
//        T get();
//    }



    public static class ResponseDeserialiser extends JsonDeserializer<ScsGetBlockList> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public ScsGetBlockList deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ScsGetBlockList.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }

}
