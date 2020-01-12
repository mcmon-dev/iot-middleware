package at.ac.tuwien.infosys.iotmiddleware.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class IntegrityService extends Contract {
    private static final String BINARY = "0x6060604052341561000f57600080fd5b60405160408061083783398101604052808051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816003819055508060058190555050506107a4806100936000396000f3006060604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630d359e90146100b4578063117df088146100dd57806312065fe0146100f25780631aa3a0081461011b57806322366844146101255780633ccfd60b1461015257806382e48d5a14610167578063a34ea2a3146101b3578063c399ec88146101ee578063d826f88f14610217578063e56e56db1461022c575b600080fd5b34156100bf57600080fd5b6100c7610255565b6040518082815260200191505060405180910390f35b34156100e857600080fd5b6100f061025f565b005b34156100fd57600080fd5b61010561032e565b6040518082815260200191505060405180910390f35b610123610338565b005b341561013057600080fd5b6101386103cc565b604051808215151515815260200191505060405180910390f35b341561015d57600080fd5b6101656103e3565b005b341561017257600080fd5b6101b1600480803560ff1690602001909190803560ff1690602001909190803560001916906020019091908035600019169060200190919050506104c7565b005b34156101be57600080fd5b6101d860048080356000191690602001909190505061065e565b6040518082815260200191505060405180910390f35b34156101f957600080fd5b6102016106e2565b6040518082815260200191505060405180910390f35b341561022257600080fd5b61022a6106ec565b005b341561023757600080fd5b61023f61076e565b6040518082815260200191505060405180910390f35b6000600354905090565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156102bc57600080fd5b6001600660016101000a81548160ff021916908315150217905515156102e157600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc6002549081150290604051600060405180830381858888f19350505050151561032357600080fd5b600060028190555050565b6000600254905090565b6005543414801561035c575060001515600660009054906101000a900460ff161515145b151561036757600080fd5b33600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550346002819055506001600660006101000a81548160ff021916908315150217905550565b6000600660009054906101000a900460ff16905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561043f57600080fd5b6004546002541015151561045257600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc6004549081150290604051600060405180830381858888f19350505050151561049457600080fd5b6004546002540360028190555060006004819055506001600660016101000a81548160ff02191690831515021790555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561052457600080fd5b60011515600660009054906101000a900460ff16151514801561055a575060001515600660019054906101000a900460ff161515145b151561056557600080fd5b6000600760008460001916600019168152602001908152602001600020541180156105ae5750600354600760008460001916600019168152602001908152602001600020540142115b156105c55760016004600082825401925050819055505b42600760008460001916600019168152602001908152602001600020819055503373ffffffffffffffffffffffffffffffffffffffff167f2aeb3ae5e25be80fa0e09082b706430e7267388a18d30caaa0dd4c7abec3806d868686604051808460ff1660ff1681526020018360ff1660ff1681526020018260001916600019168152602001935050505060405180910390a25050505050565b600080600760008460001916600019168152602001908152602001600020541180156106a85750600354600760008460001916600019168152602001908152602001600020540142115b156106bf5760016004600082825401925050819055505b600760008360001916600019168152602001908152602001600020549050919050565b6000600554905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561074857600080fd5b6000600660006101000a81548160ff021916908315150217905550600060048190555050565b60006004549050905600a165627a7a723058209a06bec40021ca1ded50b15c5bcd7fec3c419575c8be3412a0ceb94d0b99a83f0029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
        _addresses.put("3", "0x4d401bd126316c1689b332a975eea7cf29b04a6d");
        _addresses.put("4", "0xb567b1dab1e93454b352f1e64472a8046d2ab2c4");
        _addresses.put("5777", "0x57b2b6be715753a78674fa769a6427dab99ace02");
    }

    protected IntegrityService(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IntegrityService(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<MeasurementUpdateEventResponse> getMeasurementUpdateEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("MeasurementUpdate", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<MeasurementUpdateEventResponse> responses = new ArrayList<MeasurementUpdateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            MeasurementUpdateEventResponse typedResponse = new MeasurementUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.function_code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.digest_length = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.digest = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MeasurementUpdateEventResponse> measurementUpdateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("MeasurementUpdate", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, MeasurementUpdateEventResponse>() {
            @Override
            public MeasurementUpdateEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                MeasurementUpdateEventResponse typedResponse = new MeasurementUpdateEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.function_code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.digest_length = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.digest = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public static RemoteCall<IntegrityService> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _maxDelay, BigInteger _deposit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(_maxDelay),
                new Uint256(_deposit)));
        return deployRemoteCall(IntegrityService.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<IntegrityService> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _maxDelay, BigInteger _deposit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(_maxDelay),
                new Uint256(_deposit)));
        return deployRemoteCall(IntegrityService.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<TransactionReceipt> register(BigInteger weiValue) {
        final Function function = new Function(
                "register", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> update(BigInteger function_code, BigInteger digest_length, byte[] digest, byte[] id_hash) {
        final Function function = new Function(
                "update", 
                Arrays.<Type>asList(new Uint8(function_code),
                new Uint8(digest_length),
                new Bytes32(digest),
                new Bytes32(id_hash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getLastUpdate(byte[] id_hash) {
        final Function function = new Function(
                "getLastUpdate", 
                Arrays.<Type>asList(new Bytes32(id_hash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                "withdraw", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawDeposit() {
        final Function function = new Function(
                "withdrawDeposit", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getBalance() {
        final Function function = new Function("getBalance", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getPenalty() {
        final Function function = new Function("getPenalty", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isRegistered() {
        final Function function = new Function("isRegistered", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> getDeposit() {
        final Function function = new Function("getDeposit", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getMaxDelay() {
        final Function function = new Function("getMaxDelay", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> reset() {
        final Function function = new Function(
                "reset", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static IntegrityService load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IntegrityService(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static IntegrityService load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IntegrityService(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class MeasurementUpdateEventResponse {
        public Log log;

        public String sender;

        public BigInteger function_code;

        public BigInteger digest_length;

        public byte[] digest;
    }
}
