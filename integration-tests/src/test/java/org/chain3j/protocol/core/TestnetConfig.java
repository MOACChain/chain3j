package org.chain3j.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;

import org.chain3j.abi.EventEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Event;
import org.chain3j.abi.datatypes.Uint;
import org.chain3j.protocol.core.methods.request.Transaction;

/**
 * MOAC Testnet Configuration.
 */
public class TestnetConfig implements IntegrationTestConfig {

    @Override
    public String validBlockHash() {
        // https://testnet.moac.io/block/1508772
        return "0xd090b0e33e6f17a58c4ed8a851bb9b05464bb92e2d082777f59519b201c4424c";
    }

    @Override
    public BigInteger validBlock() {
        // https://testnet.moac.io/block/71032
        return BigInteger.valueOf(71032);
    }

    @Override
    public BigInteger validBlockTransactionCount() {
        return BigInteger.valueOf(3);
    }

    @Override
    public BigInteger validBlockUncleCount() {
        return BigInteger.ZERO;
    }

    @Override
    public String validAccount() {
        // https://testnet.moac.io/address/0x7312F4B8A4457a36827f185325Fd6B66a3f8BB8B
        return "0x7312F4B8A4457a36827f185325Fd6B66a3f8BB8B";
    }

    @Override
    public String validContractAddress() {
        // Deployed fibonacci example
        return "0xf2f4eec6c2adfcf780aae828de0b25f86506ffae";
    }

    @Override
    public String validContractAddressPositionZero() {
        return "0x0000000000000000000000000000000000000000000000000000000000000000";
    }

    @Override
    public String validContractCode() {
        return "0x";
    }

    @Override
    public Transaction buildTransaction() {
        return Transaction.createContractTransaction(
                validAccount(),
                BigInteger.ZERO,  // nonce
                Transaction.DEFAULT_GAS,
                validContractCode()
        );
    }

    @Override
    public String validTransactionHash() {
        return "0xf26d441775da4e01cb557dfe35e09ab8c8a69134b2687209e34348c11ae54509";
    }

    @Override
    public String validUncleBlockHash() {
        return "0x9d512dd0cad173dd3e7ec568794db03541c4a98448cc5940b695da604d118754";
    }

    @Override
    public BigInteger validUncleBlock() {
        return BigInteger.valueOf(1640092);
    }

    @Override
    public String encodedEvent() {
        Event event = new Event("Notify",
                Arrays.asList(
                        new TypeReference<Uint>(true) {},
                        new TypeReference<Uint>() {}));

        return EventEncoder.encode(event);
    }
}
