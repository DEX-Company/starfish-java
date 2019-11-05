package sg.dex.starfish.impl.squid;

import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.DIDRegisterException;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.manager.OceanManager;
import com.oceanprotocol.squid.models.DDO;
import org.web3j.crypto.CipherException;
import sg.dex.starfish.Resolver;
import sg.dex.starfish.util.DID;
import sg.dex.starfish.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import com.oceanprotocol.common.web3.KeeperService;
import com.oceanprotocol.keeper.contracts.DIDRegistry;

public class SquidResolverImpl implements Resolver {
    private DIDRegistry contract;

    SquidResolverImpl() {
        Properties properties = getProperties();
        String address = (String)properties.getOrDefault("contract.DIDRegistry.address", "");
        KeeperService keeper = null;
        try {
            keeper = SquidService.getKeeperService(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        contract = DIDRegistry.load(address, keeper.getWeb3(), keeper.getTxManager(), keeper.getContractGasProvider());
    }

    @Override
    public String getDDOString(DID did) {
        try {
            com.oceanprotocol.squid.models.DID squidDID = new com.oceanprotocol.squid.models.DID(did.toString());
            OceanManager oceanManager = SquidService.getResolverManager();
            DDO ddo = oceanManager.resolveDID(squidDID);
            if (ddo != null) {
                return ddo.toJson();
            }
        } catch (EthereumException | DDOException | DIDFormatException | IOException | CipherException e) {
            throw Utils.sneakyThrow(e);
        }
        return null;

    }

    DDO getSquidDDO(DID did) throws EthereumException, DDOException, IOException, CipherException, DIDFormatException {

        com.oceanprotocol.squid.models.DID squidDID = new com.oceanprotocol.squid.models.DID(did.toString());
        OceanManager oceanManager = SquidService.getResolverManager();
        DDO ddo = oceanManager.resolveDID(squidDID);
        if (ddo != null) {
            return ddo;
        }

        return null;

    }

    @Override
    public void registerDID(DID did, String ddo) {
        try {
            com.oceanprotocol.squid.models.DID didSquid = new com.oceanprotocol.squid.models.DID(did.toString());
            SquidService.getResolverManager().
                    registerDID(didSquid, ddo,
                            "checksum", Arrays.asList(SquidService.getProvider()));

        } catch (DIDRegisterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (DIDFormatException e) {
            e.printStackTrace();
        }


    }

    private Properties getProperties() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IOException("properties files is missing");
            }

            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
