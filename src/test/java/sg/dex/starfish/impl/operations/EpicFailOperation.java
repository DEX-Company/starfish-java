package sg.dex.starfish.impl.operations;

import java.util.Map;

import sg.dex.starfish.Operation;
import sg.dex.starfish.impl.memory.AMemoryOperation;
import sg.dex.starfish.impl.memory.MemoryAgent;

/**
 * Basic implementation of an operation which always fails
 * data asset
 *
 * @author Mike
 *
 */

public class EpicFailOperation extends AMemoryOperation implements Operation {
	protected EpicFailOperation(String meta,MemoryAgent memoryAgent) {
		super(meta,memoryAgent);
	}

	/**
	 * Creates a new instance of EpicFailOperation
	 * @param meta metadata
	 * @return new instance of EpicFailOperation
	 */
	public static EpicFailOperation create(String meta) {
		//String meta =  "{\"params\": {\"input\": {\"required\":true, \"type\":\"asset\", \"position\":0}}}";
		MemoryAgent memoryAgent = MemoryAgent.create();
		return new EpicFailOperation(meta,memoryAgent);
	}

	@Override
	public Map<String, Object> compute(Map<String, Object> params) {
		throw new Error ("Always failing operation");
	}

}
