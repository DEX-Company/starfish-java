package squid;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sg.dex.starfish.Resolver;
import sg.dex.starfish.impl.memory.MemoryAsset;
import sg.dex.starfish.impl.squid.SquidAgent;
import sg.dex.starfish.impl.squid.SquidAsset;
import sg.dex.starfish.impl.squid.SquidResolverImpl;
import sg.dex.starfish.util.DID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
@Ignore
public class SquidAgentTest {


    private SquidAgent squidAgent;
    private Resolver resolver = new SquidResolverImpl();

    @Before
    public void setup() {
        // create random DID
        DID did = DID.createRandom();
        squidAgent = SquidAgent.create( resolver, did);

    }


    @Test(expected = UnsupportedOperationException.class)
    public void testUploadAsset() {
        byte[] data = {1, 2, 3, 4};
        MemoryAsset asset = MemoryAsset.create(data);
        SquidAsset squidAsset = squidAgent.uploadAsset(asset);
        assertEquals(asset.getAssetID(), squidAsset.getAssetID());
    }

    @Test
    public void testGetAssetByAssetID() {
        byte[] data = {1, 2, 3, 4};
        MemoryAsset asset = MemoryAsset.create(data);
        SquidAsset squidAsset = squidAgent.registerAsset(asset);

        // getting the registered from squid agent using asset DID
        DID did = DID.parse(squidAsset.getDID().toString());
        SquidAsset squidAsset_1 = squidAgent.getAsset(did);
        //assertEquals(squidAsset_1.getSquidDDO().id, squidAsset.getSquidDDO().id);


    }


    @Test
    public void testQueryAsset() throws Exception {



        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("license", "Test_license");

        byte[] data = {1, 2, 3, 4};
        MemoryAsset asset = MemoryAsset.create(data,queryMap);

        SquidAsset squidAsset = squidAgent.registerAsset(asset);


        List<SquidAsset> allSquidAsset = squidAgent.queryAsset(queryMap);
        assertTrue(allSquidAsset.contains(squidAsset));


    }


    @Test
    public void testResolveSquidDID() {

        byte[] data = {1, 2, 3, 4};
        MemoryAsset asset = MemoryAsset.create(data);

        SquidAsset squidAsset = squidAgent.registerAsset(asset);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("license", "Test_license");
        SquidAsset squidAsset1 = squidAgent.resolveSquidDID(squidAsset.getDID());
        assertEquals(squidAsset.getDID(), squidAsset1.getDID());


    }
}
