package at.ac.tuwien.infosys.iotmiddleware.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.14.
 */
@SuppressWarnings("rawtypes")
public class IntegrityService extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506040516040806108758339810180604052810190808051906020019092919080519060200190929190505050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816003819055508060058190555050506107d98061009c6000396000f3006080604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630d359e90146100b4578063117df088146100df57806312065fe0146100f65780631aa3a00814610121578063223668441461012b5780633ccfd60b1461015a57806382e48d5a14610171578063a34ea2a3146101ca578063c399ec881461020f578063d826f88f1461023a578063e56e56db14610251575b600080fd5b3480156100c057600080fd5b506100c961027c565b6040518082815260200191505060405180910390f35b3480156100eb57600080fd5b506100f4610286565b005b34801561010257600080fd5b5061010b61035c565b6040518082815260200191505060405180910390f35b610129610366565b005b34801561013757600080fd5b506101406103fa565b604051808215151515815260200191505060405180910390f35b34801561016657600080fd5b5061016f610411565b005b34801561017d57600080fd5b506101c8600480360381019080803560ff169060200190929190803560ff169060200190929190803560001916906020019092919080356000191690602001909291905050506104fc565b005b3480156101d657600080fd5b506101f96004803603810190808035600019169060200190929190505050610693565b6040518082815260200191505060405180910390f35b34801561021b57600080fd5b50610224610717565b6040518082815260200191505060405180910390f35b34801561024657600080fd5b5061024f610721565b005b34801561025d57600080fd5b506102666107a3565b6040518082815260200191505060405180910390f35b6000600354905090565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156102e357600080fd5b6001600660016101000a81548160ff0219169083151502179055151561030857600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc6002549081150290604051600060405180830381858888f19350505050158015610350573d6000803e3d6000fd5b50600060028190555050565b6000600254905090565b6005543414801561038a575060001515600660009054906101000a900460ff161515145b151561039557600080fd5b33600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550346002819055506001600660006101000a81548160ff021916908315150217905550565b6000600660009054906101000a900460ff16905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561046d57600080fd5b6004546002541015151561048057600080fd5b3373ffffffffffffffffffffffffffffffffffffffff166108fc6004549081150290604051600060405180830381858888f193505050501580156104c8573d6000803e3d6000fd5b506004546002540360028190555060006004819055506001600660016101000a81548160ff02191690831515021790555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561055957600080fd5b60011515600660009054906101000a900460ff16151514801561058f575060001515600660019054906101000a900460ff161515145b151561059a57600080fd5b6000600760008460001916600019168152602001908152602001600020541180156105e35750600354600760008460001916600019168152602001908152602001600020540142115b156105fa5760016004600082825401925050819055505b42600760008460001916600019168152602001908152602001600020819055503373ffffffffffffffffffffffffffffffffffffffff167f2aeb3ae5e25be80fa0e09082b706430e7267388a18d30caaa0dd4c7abec3806d868686604051808460ff1660ff1681526020018360ff1660ff1681526020018260001916600019168152602001935050505060405180910390a25050505050565b600080600760008460001916600019168152602001908152602001600020541180156106dd5750600354600760008460001916600019168152602001908152602001600020540142115b156106f45760016004600082825401925050819055505b600760008360001916600019168152602001908152602001600020549050919050565b6000600554905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff168073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561077d57600080fd5b6000600660006101000a81548160ff021916908315150217905550600060048190555050565b60006004549050905600a165627a7a72305820f82e027944a9398d4387d68391da15fb02480bf27bda469535436b82ccf19f4c0029\n";

    public static final String FUNC_GETMAXDELAY = "getMaxDelay";

    public static final String FUNC_WITHDRAWDEPOSIT = "withdrawDeposit";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_ISREGISTERED = "isRegistered";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_GETLASTUPDATE = "getLastUpdate";

    public static final String FUNC_GETDEPOSIT = "getDeposit";

    public static final String FUNC_RESET = "reset";

    public static final String FUNC_GETPENALTY = "getPenalty";

    public static final Event MEASUREMENTUPDATE_EVENT = new Event("MeasurementUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes32>() {}));
    ;

    @Deprecated
    protected IntegrityService(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IntegrityService(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IntegrityService(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IntegrityService(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> getMaxDelay() {
        final Function function = new Function(FUNC_GETMAXDELAY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawDeposit() {
        final Function function = new Function(
                FUNC_WITHDRAWDEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> register(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Boolean> isRegistered() {
        final Function function = new Function(FUNC_ISREGISTERED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update(BigInteger function_code, BigInteger digest_length, byte[] digest, byte[] id_hash) {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(function_code), 
                new org.web3j.abi.datatypes.generated.Uint8(digest_length), 
                new org.web3j.abi.datatypes.generated.Bytes32(digest), 
                new org.web3j.abi.datatypes.generated.Bytes32(id_hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> getLastUpdate(byte[] id_hash) {
        final Function function = new Function(
                FUNC_GETLASTUPDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id_hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getDeposit() {
        final Function function = new Function(FUNC_GETDEPOSIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> reset() {
        final Function function = new Function(
                FUNC_RESET, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getPenalty() {
        final Function function = new Function(FUNC_GETPENALTY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<MeasurementUpdateEventResponse> getMeasurementUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MEASUREMENTUPDATE_EVENT, transactionReceipt);
        ArrayList<MeasurementUpdateEventResponse> responses = new ArrayList<MeasurementUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
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

    public Flowable<MeasurementUpdateEventResponse> measurementUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MeasurementUpdateEventResponse>() {
            @Override
            public MeasurementUpdateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MEASUREMENTUPDATE_EVENT, log);
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

    public Flowable<MeasurementUpdateEventResponse> measurementUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MEASUREMENTUPDATE_EVENT));
        return measurementUpdateEventFlowable(filter);
    }

    @Deprecated
    public static IntegrityService load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IntegrityService(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IntegrityService load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IntegrityService(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IntegrityService load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IntegrityService(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IntegrityService load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IntegrityService(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IntegrityService> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _maxDelay, BigInteger _deposit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxDelay), 
                new org.web3j.abi.datatypes.generated.Uint256(_deposit)));
        return deployRemoteCall(IntegrityService.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<IntegrityService> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _maxDelay, BigInteger _deposit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxDelay), 
                new org.web3j.abi.datatypes.generated.Uint256(_deposit)));
        return deployRemoteCall(IntegrityService.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<IntegrityService> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _maxDelay, BigInteger _deposit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxDelay), 
                new org.web3j.abi.datatypes.generated.Uint256(_deposit)));
        return deployRemoteCall(IntegrityService.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<IntegrityService> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _maxDelay, BigInteger _deposit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxDelay), 
                new org.web3j.abi.datatypes.generated.Uint256(_deposit)));
        return deployRemoteCall(IntegrityService.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class MeasurementUpdateEventResponse extends BaseEventResponse {
        public String sender;

        public BigInteger function_code;

        public BigInteger digest_length;

        public byte[] digest;
    }
}
