package sg.dex.starfish.impl.squid;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.web3j.crypto.CipherException;

import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.DIDRegisterException;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.manager.OceanManager;
import com.oceanprotocol.squid.models.DDO;

import sg.dex.starfish.Resolver;
import sg.dex.starfish.exception.ResolverException;
import sg.dex.starfish.util.DID;

public class SquidResolverImpl implements Resolver {
    private final Map<DID, String> ddoCache = new HashMap<>();
    private SquidService squidService;

    /**
     * Create SquidResolverImpl
     *
     * @param squidService
     */
    public SquidResolverImpl(SquidService squidService){
        this.squidService=squidService;
    }

    @Override
    public String getDDOString(DID did) throws ResolverException {
        try {
            com.oceanprotocol.squid.models.DID squidDID = new com.oceanprotocol.squid.models.DID(did.toString());
            OceanManager oceanManager = squidService.getResolverManager();
            DDO ddo = oceanManager.resolveDID(squidDID);
            if (ddo != null) {
                return ddo.toJson();
            }
        } catch (EthereumException | DDOException | DIDFormatException | IOException | CipherException e) {
            throw new ResolverException(e);
        }
        return null;

    }

    DDO getSquidDDO(DID did) throws EthereumException, DDOException, IOException, CipherException, DIDFormatException {

        com.oceanprotocol.squid.models.DID squidDID = new com.oceanprotocol.squid.models.DID(did.toString());
        OceanManager oceanManager = squidService.getResolverManager();
        DDO ddo = oceanManager.resolveDID(squidDID);
        if (ddo != null) {
            return ddo;
        }

        return null;

    }

    @Override
    public void registerDID(DID did, String ddo) throws ResolverException {
        installLocalDDO(did, ddo);

        try {
            com.oceanprotocol.squid.models.DID didSquid = new com.oceanprotocol.squid.models.DID(did.toString());
            squidService.getResolverManager().
                    registerDID(didSquid, squidService.getAquariusService().getDdoEndpoint(),
                            "checksum", Arrays.asList(squidService.getProvider()));

        } catch (DIDRegisterException e) {
            throw new ResolverException(e);
        } catch (IOException e) {
            throw new ResolverException(e);
        } catch (CipherException e) {
            throw new ResolverException(e);
        } catch (DIDFormatException e) {
            throw new ResolverException(e);
        }
    }

    /**
     * Registers a DID with a DDO in the context of this Ocean connection on the local machine.
     * <p>
     * This registration is intended for testing purposes.
     *
     * @param did       A did to register
     * @param ddoString A string containing a valid DDO in JSON Format
     */
    private void installLocalDDO(DID did, String ddoString) {
        if (did == null) throw new IllegalArgumentException("DID cannot be null");
        did = did.withoutPath();
        ddoCache.put(did, ddoString);
    }

}