package sg.dex.starfish.integration.developerTC;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sg.dex.starfish.Asset;
import sg.dex.starfish.exception.StarfishValidationException;
import sg.dex.starfish.impl.memory.MemoryAsset;
import sg.dex.starfish.impl.remote.ARemoteAsset;
import sg.dex.starfish.impl.remote.RemoteAgent;
import sg.dex.starfish.impl.remote.RemoteDataAsset;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * As a developer wishing to make an asset available for download,
 * I need a way to upload the asset data to an appropriate service provider
 */
public class UploadAsset_11 {


    private RemoteAgent remoteAgent;
    private static String METADATA_JSON_SAMPLE = "src/test/resources/example/asset_metadata.json";

    @Before
    public void setUp() {
        // create remote Agent
        remoteAgent = RemoteAgentConfig.getRemoteAgent();
    }

    private Map<String,Object> getMetaData(){
        try {
            String METADATA_JSON_CONTENT = new String(Files.readAllBytes(Paths.get(METADATA_JSON_SAMPLE)));
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String,Object> json = objectMapper.readValue(METADATA_JSON_CONTENT, HashMap.class);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testUploadAsset() {

        Asset a = MemoryAsset.create("Testing to upload of asset");
        RemoteDataAsset remoteAssetUpload = (RemoteDataAsset)remoteAgent.uploadAsset(a);
        String actual = RemoteAgentConfig.getDataAsStringFromInputStream(remoteAssetUpload.getContentStream());

        assertEquals(actual, "Testing to upload of asset");
        assertEquals(a.getAssetID(), remoteAssetUpload.getAssetID());

    }

    @Test
    public void testUploadAssetWithMetaData() {

        byte [] data ={2,3,4,5,6,7,8,9,0};
        Asset a = MemoryAsset.create(data,getMetaData());
        RemoteDataAsset remoteAssetUpload = (RemoteDataAsset)remoteAgent.uploadAsset(a);

        assertEquals(remoteAssetUpload.getContent().length, data.length);
        assertEquals(a.getAssetID(), remoteAssetUpload.getAssetID());
        assertEquals(a.getMetadata().get("title").toString(),"First listing");

    }
    @Test(expected = StarfishValidationException.class)
    public void testNullUpload(){
        remoteAgent.uploadAsset(null);
    }
    @Test
    public void testUploadRegisteredAsset(){
        byte [] data ={2,3,4,5,6,7,8,9,0};
        MemoryAsset memoryAsset = MemoryAsset.create(data);
        ARemoteAsset aRemoteAsset =remoteAgent.registerAsset(memoryAsset);
        ARemoteAsset aRemoteAsset1 =remoteAgent.uploadAsset(memoryAsset);
        Assert.assertArrayEquals(aRemoteAsset.getContent(),data);
        assertEquals(aRemoteAsset.getAssetID(),memoryAsset.getAssetID());
        assertEquals(aRemoteAsset1.getAssetID(),memoryAsset.getAssetID());
        assertEquals(aRemoteAsset1.getAssetID(),aRemoteAsset.getAssetID());

    }

    @Test(expected = StarfishValidationException.class)
    public void testNullRegister(){
        remoteAgent.registerAsset(null);

    }


}


