/**
 * 
 */
package org.hyperdata.go.parameters;
 
import org.hyperdata.beeps.pipelines.Processor;

/**
 * @author danny
 *
 */
public interface ParameterSet {
	public void addParameter(Parameter param);
	
	public Object getValue(String name) throws Exception;
	/**
	 * @param chunkEnv
	 */
	// public void copyParametersToProcessor(Processor chunkEnv);
}
