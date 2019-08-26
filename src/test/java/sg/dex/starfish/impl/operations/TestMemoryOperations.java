package sg.dex.starfish.impl.operations;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sg.dex.crypto.Hash;
import sg.dex.starfish.Asset;
import sg.dex.starfish.Job;
import sg.dex.starfish.Operation;
import sg.dex.starfish.impl.memory.MemoryAgent;
import sg.dex.starfish.impl.memory.MemoryAsset;
import sg.dex.starfish.util.Hex;
import sg.dex.starfish.util.Utils;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMemoryOperations {
    private MemoryAgent memoryAgent = MemoryAgent.create();

    /**
     * This test is to test the Asset input Asset output Async
     */
    @Test
    public void testReverseBytesAsync() {
        byte[] data = new byte[]{1, 2, 3};
        Operation memoryOperation = ReverseByte_AssetI_AssetO.create(getMetaDataForAssetI_AssetO(), memoryAgent);

        Asset a = MemoryAsset.create(data);
        Map<String, Object> test = new HashMap<>();
        test.put("input", a);
        // Map<String, Object> result =Params.formatParams(memoryOperation,test);

        Job<Map<String,Object>> job = memoryOperation.invokeAsync(test);

        Asset response = (Asset) job.awaitResult(1000).get("output");

        assertArrayEquals(new byte[]{3, 2, 1}, response.getContent());
    }

    /**
     * This test is to test the Asset input Asset output Async
     */
    @Test
    public void testReverseBytesSync() {
        byte[] data = new byte[]{1, 2, 3};
        Operation memoryOperation =
                ReverseByte_AssetI_AssetO.
                        create(getMetaDataForAssetI_AssetO(), memoryAgent);

        Asset a = MemoryAsset.create(data);
        Map<String, Object> test = new HashMap<>();
        test.put("input", a);

        Map<String,Object> result = memoryOperation.invokeResult(test);
        Asset response= (Asset)result.get("output");



        assertArrayEquals(new byte[]{3, 2, 1}, response.getContent());
    }


    // ------JSON input and JSON output----------------
    /**
     * This test is to test the Async Operation
     */
    @Test
    public void testPrimeAsync() {

        Operation memoryOperation = FindPrime_JsonI_JsonO.create(getMetaDataJsonIAndJsonO(), memoryAgent);

        Map<String, Object> test = new HashMap<>();
        test.put("input", "10");
        Job<Map<String,Object>> job = memoryOperation.invokeAsync(test);

        Map<String,Object> res = job.awaitResult(1000);

        // prime between 1-10 is 2 3 5 7
       assertTrue(res.get("output").toString().contains("2"));
       assertTrue(res.get("output").toString().contains("3"));
       assertTrue(res.get("output").toString().contains("5"));
       assertTrue(res.get("output").toString().contains("7"));



    }

    /**
     * This test is to test the Async Operation
     */
    @Test
    public void testPrimeSync() {

        FindPrime_JsonI_JsonO memoryOperation = FindPrime_JsonI_JsonO.create(getMetaDataJsonIAndJsonO(), memoryAgent);
        Map<String, Object> test = new HashMap<>();
        test.put("input", "12");

        Map<String,Object> res = memoryOperation.invokeResult(test);
        // prime between 1-10 is 2 3 5 7
        assertTrue(res.get("output").toString().contains("2"));
        assertTrue(res.get("output").toString().contains("3"));
        assertTrue(res.get("output").toString().contains("5"));
        assertTrue(res.get("output").toString().contains("7"));
        assertTrue(res.get("output").toString().contains("11"));


    }

    //-----------JSON Input and Asset output---------------------

    // Todo

    // ------------Asset Input and JSON output--------------------

    @Test
    public void testHashAsync() {

        byte[] data = new byte[]{1, 2, 3};
        CalculateHash_AssetI_JsonO hashOperation =
                CalculateHash_AssetI_JsonO.
                        create(getMetaDataAssetIAndJsonO(), memoryAgent);

        Asset a = MemoryAsset.create(data);
        Map<String, Object> test = new HashMap<>();
        test.put("input", a);

        Job<Map<String,Object>> job = hashOperation.invokeAsync(test);

        Map<String ,Object> response = job.awaitResult(1000);
        Object r=response.get("output");
        String hash = Hex.toString(Hash.sha3_256(a.getContent()));
        assertEquals(hash,r.toString());

    }

    /**
     * This test is to test the Async Operation
     */
    @Test
    public void testHashSync() {

        byte[] data = new byte[]{1, 2, 3};
        CalculateHash_AssetI_JsonO hashOperation =
                CalculateHash_AssetI_JsonO.
                        create(getMetaDataAssetIAndJsonO(), memoryAgent);

        Asset a = MemoryAsset.create(data);
        Map<String, Object> test = new HashMap<>();
        test.put("input", a);

        Map<String,Object> res = hashOperation.invokeResult(test);

        String hash = Hex.toString(Hash.sha3_256(a.getContent()));
        assertEquals(hash,res.get("output").toString());
    }
    /**
     * API to get metadata
     * @return
     */
    private String getMetaDataForAssetI_AssetO() {
        String meta = "{\"dateCreated\":\"2019-05-07T08:17:31.521445Z\",\n" +
                "\t\"size\":\"3\",\n" +
                "\t\"contentType\":\"application/octet-stream\",\n" +
                "\t\"contentHash\":\"4e03657aea45a94fc7d47ba826c8d667c0d1e6e33a64a036ec44f58fa12d6c45\",\n" +
                "\t\"tags\":[\"Reverse byte\"],\n" +
                "\t\"license\":\"CC-BY\",\n" +
                "\t\"author\":\"Reverse Byte Inc\",\n" +
                "\t\"name\":\"Reverse byte computation operation\",\n" +
                "\t\"description\":\"Reverse the give byte\",\n" +
                "\t\"inLanguage\":\"en\",\n" +
                "\t\"type\":\"operation\",\n" +
                " \"operation\":{\n" +
                "     \"modes\":[\"sync\",\"async\"],\n" +
                "\t\t\"params\":{\"input\":{\"type\":\"asset\"}},\n" +
                "\t\t\"results\":{\"output\":{\"type\":\"asset\"}}}}";
        return meta;
    }

    /**
     * API to get metadata
     * @return
     */
    private String getMetaDataJsonIAndJsonO() {
        String meta = "{\"dateCreated\":\"2019-05-07T08:17:31.521445Z\",\n" +
                "\t\"size\":\"3\",\n" +
                "\t\"contentType\":\"application/octet-stream\",\n" +
                "\t\"contentHash\":\"4e03657aea45a94fc7d47ba826c8d667c0d1e6e33a64a036ec44f58fa12d6c45\",\n" +
                "\t\"tags\":[\"primes\"],\n" +
                "\t\"license\":\"CC-BY\",\n" +
                "\t\"author\":\"Primely Inc\",\n" +
                "\t\"name\":\"Prime computation operation\",\n" +
                "\t\"description\":\"Computes prime numbers\",\n" +
                "\t\"inLanguage\":\"en\",\n" +
                "\t\"type\":\"operation\",\n" +
                " \"operation\":{\n" +
                "     \"modes\":[\"sync\",\"async\"],\n" +
                "\t\t\"params\":{\"input\":{\"type\":\"json\"}},\n" +
                "\t\t\"results\":{\"output\":{\"type\":\"json\"}}}}";
        return meta;
    }

    /**
     * API to get metadata
     * @return
     */
    private String getMetaDataAssetIAndJsonO() {
        String meta = "{\"dateCreated\":\"2019-05-07T08:17:31.521445Z\",\n" +
                "\t\"size\":\"3\",\n" +
                "\t\"contentType\":\"application/octet-stream\",\n" +
                "\t\"contentHash\":\"4e03657aea45a94fc7d47ba826c8d667c0d1e6e33a64a036ec44f58fa12d6c45\",\n" +
                "\t\"tags\":[\"hash\"],\n" +
                "\t\"license\":\"CC-BY\",\n" +
                "\t\"author\":\"HAsh Inc\",\n" +
                "\t\"name\":\"Hash computation operation\",\n" +
                "\t\"description\":\"Computes HAsh numbers\",\n" +
                "\t\"inLanguage\":\"en\",\n" +
                "\t\"type\":\"operation\",\n" +
                " \"operation\":{\n" +
                "     \"modes\":[\"sync\",\"async\"],\n" +
                "\t\t\"params\":{\"input\":{\"type\":\"asset\"}},\n" +
                "\t\t\"results\":{\"output\":{\"type\":\"json\"}}}}";
        return meta;
    }


    //---Existing testcase----------


    /**
     * This test is to test the Async Operation but providing mode as Sync
     */
    @Test
    public void testReverseBytesAsyncWithDifferentMetadata() {
        byte[] data = new byte[]{1, 2, 3};

        String meta = "{\n" +
                "  \"params\": {\n" +
                "    \"input\": {\n" +
                "      \"required\": \"true\",\n" +
                "      \"position\": 0,\n" +
                "      \"type\": \"Object\"\n" +
                "      \n" +
                "    },\n" +
                "    \"did\": \"hashing\"\n" +
                "  },\n" +
                "  \"mode\":\"Async\",\n" +
                "  \"result\": {\n" +
                "        \"hash-value\": {\n" +
                "           \"type\": \"Object\"\n" +
                "      \n" +
                "    }\n" +
                "  }\n" +
                "}";


        ReverseByte_AssetI_AssetO memoryOperation = ReverseByte_AssetI_AssetO.create(meta, memoryAgent);

        Asset a = MemoryAsset.create(data);
        Map<String, Object> test = new HashMap<>();
        test.put("input", a);

        Job<Map<String, Object>> job = memoryOperation.invokeAsync(test);

        Asset result = (Asset) job.awaitResult(1000).get("output");

        assertArrayEquals(new byte[]{3, 2, 1}, result.getContent());

    }

    @Test
    public void testNamedParams() {
        byte[] data = new byte[]{1, 2, 3};
        String meta = "{\"params\": {\"input\": {\"required\":true, \"type\":\"asset\", \"position\":0}}}";
        ReverseByte_AssetI_AssetO op = ReverseByte_AssetI_AssetO.create(meta, memoryAgent);
        Asset a = MemoryAsset.create(data);
        Job<Map<String,Object>> job = op.invokeAsync(Utils.mapOf("input", a));
        Asset result = (Asset) job.awaitResult(1000).get("output");
        assertArrayEquals(new byte[]{3, 2, 1}, result.getContent());
    }

	@Test
    public void testBadNamedParams() {
        byte[] data = new byte[]{1, 2, 3};
        String meta = "{\"params\": {\"input\": {\"required\":true, \"type\":\"asset\", \"position\":0}}}";
        ReverseByte_AssetI_AssetO op = ReverseByte_AssetI_AssetO.create(meta, memoryAgent);
        Asset a = MemoryAsset.create(data);
        Job<Map<String,Object>> badJob = op.invokeAsync(Utils.mapOf("nonsense", a)); // should not yet fail since this is async
        try {
            Object result2 = badJob.awaitResult(1000);
            fail("Should not succeed! Got: " + Utils.getClass(result2));
        } catch (Exception ex) {
            /* OK */
        }
    }

    @Test(expected = Exception.class)
    public void testInsufficientParams() {
        String meta = "{\"params\": {\"input\": {\"required\":true, \"type\":\"asset\", \"position\":0}}}";
        ReverseByte_AssetI_AssetO op = ReverseByte_AssetI_AssetO.create(meta, memoryAgent);
        try {
        	Map<String,Object> emptyParams=new HashMap<>();
            Job<Map<String,Object>> badJob = op.invokeAsync(emptyParams); // should not yet fail since this is async
            Object result2 = badJob.awaitResult(10);
            fail("Should not succeed! Got: " + Utils.getClass(result2));
        } catch (IllegalArgumentException ex) {
            /* OK */
        }
    }

}
