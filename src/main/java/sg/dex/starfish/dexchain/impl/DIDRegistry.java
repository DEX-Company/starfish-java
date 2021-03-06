package sg.dex.starfish.dexchain.impl;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.*;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>Generated with web3j version 4.5.14.
 */
@SuppressWarnings("rawtypes")
public class DIDRegistry extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b5061071e806100206000396000f3fe608060405234801561001057600080fd5b506004361061005e576000357c010000000000000000000000000000000000000000000000000000000090048063331daa99146100635780638129fc1c1461013c578063bbc1dede14610146575b600080fd5b6101266004803603604081101561007957600080fd5b8101908080359060200190929190803590602001906401000000008111156100a057600080fd5b8201836020820111156100b257600080fd5b803590602001918460018302840111640100000000831117156100d457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610188565b6040518082815260200191505060405180910390f35b610144610564565b005b6101726004803603602081101561015c57600080fd5b8101908080359060200190929190505050610663565b6040518082815260200191505060405180910390f35b60008073ffffffffffffffffffffffffffffffffffffffff166033600085815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16148061025b57503373ffffffffffffffffffffffffffffffffffffffff166033600085815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b6102b0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260308152602001806106956030913960400191505060405180910390fd5b61080082511115610329576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f496e76616c69642076616c75652073697a65000000000000000000000000000081525060200191505060405180910390fd5b60006033600085815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614156103c95733905060348490806001815401808255809150509060018203906000526020600020016000909192909190915055505b60405180604001604052808273ffffffffffffffffffffffffffffffffffffffff168152602001438152506033600086815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050506033600085815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16847fd249d46c05fa2d9983ee4d99bf117a4072ab968cbd1cd64f6aac599e928282b38543426040518080602001848152602001838152602001828103825285818151815260200191508051906020019080838360005b838110156105195780820151818401526020810190506104fe565b50505050905090810190601f1680156105465780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a360348054905091505092915050565b600060019054906101000a900460ff16806105835750610582610683565b5b8061059a57506000809054906101000a900460ff16155b6105ef576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602e8152602001806106c5602e913960400191505060405180910390fd5b60008060019054906101000a900460ff16159050801561063f576001600060016101000a81548160ff02191690831515021790555060016000806101000a81548160ff0219169083151502179055505b80156106605760008060016101000a81548160ff0219169083151502179055505b50565b600060336000838152602001908152602001600020600101549050919050565b600080303b9050600081149150509056fe41747472696275746573206d75737420626520726567697374657265642062792074686520444944206f776e6572732e436f6e747261637420696e7374616e63652068617320616c7265616479206265656e20696e697469616c697a6564a165627a7a72305820f568b33717adfa906fb1adbdd93c798711e9fbbc16e70263355709222646e9e10029";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_REGISTERDID = "registerDID";

    public static final String FUNC_GETBLOCKNUMBERUPDATED = "getBlockNumberUpdated";

    public static final Event DIDREGISTERED_EVENT = new Event("DIDRegistered",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("8995", "0x0640B8c602bd466FB2F5C5C155e94dE50655bc39");
    }

    @Deprecated
    protected DIDRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DIDRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DIDRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DIDRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static DIDRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DIDRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DIDRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DIDRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DIDRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DIDRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DIDRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DIDRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DIDRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DIDRegistry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DIDRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DIDRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<DIDRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DIDRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DIDRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DIDRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public List<DIDRegisteredEventResponse> getDIDRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DIDREGISTERED_EVENT, transactionReceipt);
        ArrayList<DIDRegisteredEventResponse> responses = new ArrayList<DIDRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DIDRegisteredEventResponse typedResponse = new DIDRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._did = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._blockNumberUpdated = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DIDRegisteredEventResponse> dIDRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DIDRegisteredEventResponse>() {
            @Override
            public DIDRegisteredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DIDREGISTERED_EVENT, log);
                DIDRegisteredEventResponse typedResponse = new DIDRegisteredEventResponse();
                typedResponse.log = log;
                typedResponse._did = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._owner = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._blockNumberUpdated = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DIDRegisteredEventResponse> dIDRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIDREGISTERED_EVENT));
        return dIDRegisteredEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> initialize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE,
                Arrays.asList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> registerDID(byte[] _did, String _value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERDID,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(_did),
                        new org.web3j.abi.datatypes.Utf8String(_value)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getBlockNumberUpdated(byte[] _did) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBLOCKNUMBERUPDATED,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(_did)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class DIDRegisteredEventResponse extends BaseEventResponse {
        public byte[] _did;

        public String _owner;

        public String _value;

        public BigInteger _blockNumberUpdated;

        public BigInteger _timestamp;
    }
}
