package sg.dex.starfish.util;

import java.util.HashMap;
import java.util.Map;

import sg.dex.starfish.Asset;
import sg.dex.starfish.Operation;

/**
 * Utility class for handling invoke parameters
 * 
 * @author Mike
 *
 */
public class Params {

	/**
	 * Creates the "params" part of the invoke payload using the spec in the operation metadata 
	 * and the passed positional arguments
	 * @param operation The operation to format the parameters for
	 * @param params A map of parameter names to assets
	 * @return The "params" portion of the invoke payload as a Map
	 */
	@SuppressWarnings("unchecked")
	public
	static Map<String,Object> formatParams(Operation operation, Map<String,Asset> params) {
		HashMap<String,Object> result=new HashMap<>(params.size());
		Map<String,Object> paramSpec=operation.getParamSpec();
		for (Map.Entry<String,Object> me:paramSpec.entrySet()) {
			String paramName=me.getKey();
			Map<String,Object> spec=(Map<String,Object>)me.getValue();
			// String type=(String) spec.get("type");
			boolean required=Utils.coerceBoolean(spec.get("required"));
			if (params.containsKey(paramName)) {
				Asset a=params.get(paramName);
				Map<String,Object> value=a.getParamValue();
				result.put(paramName,value);
			}
			if (required) {
				throw new IllegalArgumentException("Paramter "+paramName+" is required but not supplied");
			}
		}
		return result;
	}

	/**
	 * Creates the "params" part of the invoke payload using the spec in the operation metadata 
	 * and the passed positional arguments
	 * @param operation The operation to format the parameters for
	 * @param params An array of assets to be provided as positional parameters
	 * @return The "params" portion of the invoke payload as a JSONObject
	 */
	@SuppressWarnings("unchecked")
	public
	static Map<String,Object> formatParams(Operation operation, Asset... params) {
		HashMap<String,Object> result=new HashMap<>(params.length);
		Map<String,Object> paramSpec=operation.getParamSpec();
		for (Map.Entry<String,Object> me:paramSpec.entrySet()) {
			String paramName=me.getKey();
			Map<String,Object> spec=(Map<String,Object>)me.getValue();
			// String type=(String) spec.get("type");
			Object positionObj=spec.get("position");
			int pos=(positionObj!=null)?Utils.coerceInt(positionObj):-1;
			boolean required=Utils.coerceBoolean(spec.get("required"));
			if ((pos>=0)&&(pos<params.length)) {
				Asset a=params[pos];
				Map<String,Object> value=a.getParamValue();
				result.put(paramName,value);
			}
			if (required) {
				throw new IllegalArgumentException("Paramter "+paramName+" is required but not supplied");
			}
		}
		return result;
	}

}