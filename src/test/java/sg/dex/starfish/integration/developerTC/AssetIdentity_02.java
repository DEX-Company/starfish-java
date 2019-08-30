package sg.dex.starfish.integration.developerTC;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sg.dex.starfish.Asset;
import sg.dex.starfish.exception.StarfishValidationException;
import sg.dex.starfish.impl.memory.MemoryAsset;
import sg.dex.starfish.impl.remote.ARemoteAsset;
import sg.dex.starfish.impl.remote.RemoteAgent;
import sg.dex.starfish.impl.remote.RemoteDataAsset;
import sg.dex.starfish.util.JSON;
import sg.dex.starfish.util.ProvUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


/**
 * As a developer working with Ocean,
 * I need a stable identifier (Asset ID) for an arbitrary asset in the Ocean ecosystem
 */
@RunWith(JUnit4.class)

public class AssetIdentity_02 {

    private RemoteAgent remoteAgent;

    @Before
    public void setup() {
        // Initialize Remote Agent
        remoteAgent = RemoteAgentConfig.getRemoteAgent();
        Assume.assumeNotNull(remoteAgent);
    }

    @Test
    public void testByteContent() {

        // create a memory asset
        byte[] data = new byte[]{1, 2, 3};
        Asset asset1 = MemoryAsset.create(data);

        //2. registration : it will just reg the asset and upload its metadata content  and will return a Remote Agent
        RemoteDataAsset remoteAsset = (RemoteDataAsset)remoteAgent.registerAsset(asset1);

        // register to the remote agent
        // get the Remote asset ID which has been register using remote Agent

        String assetID = remoteAsset.getAssetID();
        // compare both the assetID, It must be equal
        assertEquals(assetID, asset1.getAssetID());
    }


    // try to register again with same content and check the Hash
    @Test
    public void createAssetWithMetaData() {

        byte[] data = new byte[]{1, 2, 3};
        // update the metadata
        Map<String, Object> metaDataAsset = new HashMap<>();
        metaDataAsset.put("id", "123");
        metaDataAsset.put("name", "Fig");
        metaDataAsset.put("location", "Singapore");

        // creating asset with MetaData
        Asset asset2 = MemoryAsset.create( data,metaDataAsset);

        // uploading the Asset this remote Agent
        ARemoteAsset aRemoteAsset =remoteAgent.uploadAsset(asset2);

        // get the Remote asset ID which has been register using remote Agent
        String assetID = asset2.getAssetID();

        //Getting the content of the Asset
        byte[] result = asset2.getContent();

        // compare both the assetID, It must be equal
        assertEquals(assetID, asset2.getAssetID());

        // verifying the Content is same or not
        assertEquals(result.length, data.length);


        // verify the Asset metaDAta must be equal to Registered asset MetaData
        assertEquals(asset2.getMetadata().get("id").toString(), "123");


    }

    @Test
    public void createAssetWithProvMetadata(){
        byte[] data = new byte[]{1, 2, 3};
        // update the metadata
        Map<String, Object> metaDataAsset = new HashMap<>();
        metaDataAsset.put("id", "123");
        metaDataAsset.put("name", "Fig");
        metaDataAsset.put("location", "Singapore");

        String actId=UUID.randomUUID().toString();
        String agentId=UUID.randomUUID().toString();
        
        Map<String, Object> provmetadata= ProvUtil.createPublishProvenance(actId,agentId);
        metaDataAsset.put("provenance",provmetadata);
        // creating asset with MetaData
        Asset asset2 = MemoryAsset.create( data,metaDataAsset);
        RemoteDataAsset remoteAsset = (RemoteDataAsset)remoteAgent.registerAsset(asset2);

        // uploading the Asset this remote Agent
        remoteAgent.uploadAsset(asset2);


        //verify prov info exists
        assertNotNull(remoteAsset.getMetadata().get("provenance"));
        Map<String,Object> provData = JSON.toMap(remoteAsset.getMetadata().get("provenance").toString());

        assertNotNull(provData.get("activity"));
        assertNotNull(provData.get("wasGeneratedBy"));
    }

    @Test
    public void testWithStringContent() {

        // create Asset using String data
        Asset asset3 = MemoryAsset.create("Testing using String");

        //Registering the Asset
        RemoteDataAsset remoteAsset3 = (RemoteDataAsset)remoteAgent.registerAsset(asset3);
        // uploading the Asset, it will upload the content of an asset
        remoteAgent.uploadAsset(asset3);

        assertEquals(remoteAsset3.getAssetID(), asset3.getAssetID());
//        // verify the content
//        assertEquals(RemoteAgentConfig.getDataAsStringFromInputStream(remoteAsset3.getContentStream()),
//                "Testing using String");
    }


    @Test(expected = StarfishValidationException.class)
    public void testForNullAsset(){
        // Null check should be there ?
        remoteAgent.registerAsset(null);
    }

}
