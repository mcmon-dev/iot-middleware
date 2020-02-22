#IoT Middleware

## Prerequisites
- Java 1.8.0_131
- Apache Maven 3.3.9
- Geth (1.9.10-stable) client has to be up and running
- [Ipfs](https://ipfs.io/) 0.4.15 client has to be up and running
- [Mosquitto](https://mosquitto.org/) (MQTT v3.1 broker) has to be up and running
- [Web3j Command Line Tool](https://github.com/web3j/web3j-cli) 4.5.15

## Smart Contract Wrapper
In case you have changed the smart contract you have to generate the according wrapper class in Java.
```shell
web3j solidity generate -b path_to/IntegrityService.bin -a path_to/IntegrityService.abi -o path_to/iotmiddleware/src/main/java/ -p at.ac.tuwien.infosys.iotmiddleware.contract;
```

## Configuration
The IoT middleware can be configured via the [Defines](./src/main/java/at/ac/tuwien/infosys/iotmiddleware/utils/Defines.java) 
class. The following variables have to be changed:

*BC_ACCOUNT:* the address of the account which you used for the registration at the smart contract.
*CONTRACT_ADDRESS:* the address of the smart contract.

## Build
Go to the *iotmiddleware* folder and execute:
```shell
mvn clean package
```

##Usage
```shell
USAGE: java -jar [-m <value>] iot-middleware-1.0-SNAPSHOT-jar-with-dependencies.jar
```

**-m:** This is to set the maximum inflight value. It determines the number of messages which can be transmitted by the 
broker concurrently. Further information can be found in the [Mosquito documentation](https://mosquitto.org/man/mosquitto-conf-5.html).
