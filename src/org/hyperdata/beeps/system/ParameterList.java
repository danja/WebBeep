/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.describe.Described;
import org.hyperdata.common.describe.Named;
 

/**
 * An ordered list of Parameters
 * 
 * @author danny
 *
 */
public interface ParameterList extends Named, Described {
	
	/**
	 * Add parameter to the list
	 *
	 * @param parameter the parameter to add
	 */
	public void add(Parameter parameter);
	
	/**
	 * Get the value of the named parameter
	 * 
	 * @param name name of the parameter
	 * @return value of the parameter
	 * 
	 * @throws Exception if the parameter is not found
	 */
	public Object getValue(String name) throws Exception;
	
	/**
	 * Get the named parameter
	 * 
	 * @param name name of Parameter
	 * @return the Parameter named
	 */
	public Parameter getParameter(String name);
	
	
	/**
	 * Get the 
	 * @param key
	 * @return
	 * @throws NotFoundException 
	 */
	public Object getLocal(String localName) throws NotFoundException;
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
	public void update(ParameterList incoming);
	
	public void setParameter(Parameter parameter);

	
	public void randomizeValues();
}
