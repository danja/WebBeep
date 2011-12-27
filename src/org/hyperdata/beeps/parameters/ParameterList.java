/**
 * 
 */
package org.hyperdata.beeps.parameters;
 
import org.hyperdata.beeps.pipelines.Processor;

/**
 * @author danny
 *
 */
public interface ParameterList {
	public void add(Parameter param);
	
	public Object getValue(String name) throws Exception;
	/**
	 * @param chunkEnv
	 */
	// public void copyParametersToProcessor(Processor chunkEnv);

	public Parameter get(int i);
	/**
	 * @return
	 */
	public int size();
	

	/**
	 * @param parameters
	 */
	public void addAll(ParameterList parameters);
	
	/**
	 * Adds incoming parameters unless parameter already exists, in which case 
	 * it's given the new value
	 * 
	 * @param incoming
	 */
	public void consume(ParameterList incoming);
	
	public void randomizeValues();
}
