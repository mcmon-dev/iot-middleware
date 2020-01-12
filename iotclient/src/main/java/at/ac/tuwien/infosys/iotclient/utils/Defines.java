package at.ac.tuwien.infosys.iotclient.utils;

public class Defines {

    //IP address to the MQTT broker
    public static final String BROKER = "tcp://ip_address";

    public static final String[] REALTIME_TOPICS = {
            "coap://address_of_data_source",
            "coap://address_of_data_source",

    };

    public static final String CONTRACT_ADDRESS = "smart_contract_address";
    public static final String IPC_PATH = "path/geth.ipc";

    public static final String IPFS_ADDRESS = "/ip4/127.0.0.1/tcp/5001";
}
