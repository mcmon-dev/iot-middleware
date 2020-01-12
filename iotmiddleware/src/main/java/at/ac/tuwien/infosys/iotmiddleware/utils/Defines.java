package at.ac.tuwien.infosys.iotmiddleware.utils;

import java.math.BigInteger;

public class Defines {

    public static final int REGISTRY_PORT = 60000;

    /**
     * Indicates how long the data is delayed until it is pushed into the blockchain. Unit is seconds.
     */
    public static final long DELAY = 30;

    // URI of the MQTTv3 broker.
    public static final String BROKER = "tcp://localhost:1883";
    //Path to the GETH IPC file
    public static final String GETH_IPC_PATH = "custom_path/geth.ipc";
    //Smart contract address
    public static final String CONTRACT_ADDRESS = "0x4d401bd126316c1689b332a975eea7cf29b04a6d";;
    //Ropsten Client Account
    public static final String BC_ACCOUNT = "account address of the bc client";
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(6000000000l);
    public static final String IFPS_ADDRESS = "/ip4/127.0.0.1/tcp/5001";
    //Max. inflights value of the mqtt client
    public static int MAX_INFLIGHTS = 10;
}
