package sg.dex.starfish.impl.operations;

import sg.dex.starfish.Operation;
import sg.dex.starfish.constant.Constant;
import sg.dex.starfish.impl.memory.AMemoryOperation;
import sg.dex.starfish.impl.memory.MemoryAgent;
import sg.dex.starfish.impl.resource.ResourceAsset;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a memory implementation of Invoke Service.
 * It calculate all prime number present before any given number.
 * It reads metadata from a file which has basic detail of the input and output type.
 */
public class FindSumOfPrime_JsonInput_AssetOutput extends AMemoryOperation implements Operation {
    protected FindSumOfPrime_JsonInput_AssetOutput(String meta, MemoryAgent memoryAgent) {
        super(meta, memoryAgent);
    }

    public static FindSumOfPrime_JsonInput_AssetOutput create(MemoryAgent memoryAgent) {
        ResourceAsset resourceAsset = ResourceAsset.create("src/test/resources/assets/prime_asset_metadata.json");
        return new FindSumOfPrime_JsonInput_AssetOutput(resourceAsset.getMetadataString(), memoryAgent);
    }


    private Map<String, Object> doCompute(final Object input) {
        Integer num = (Integer.parseInt(input.toString()));
        int primeSum =0;
        for (int i = 2; i < num; i++) {
            if (isPrime(i)) {
                primeSum =primeSum+i;
            }

        }

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        response.put("status", Constant.SUCCEEDED);
        response.put("sumOfPrime", primeSum);
        result.put("results",response);
        return result;
    }

    @Override
    protected Map<String, Object> compute(Map<String, Object> params) {
        if (params == null || params.get("input") == null)
            throw new IllegalArgumentException("Missing parameter 'input'");
        return doCompute(params.get("input"));
    }

    private boolean isPrime(int n) {
        if (n <= 1)
            return false;

        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }

}
