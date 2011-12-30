/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.Described;

/**
 * @author danny
 * 
 */
public interface Component extends Named, Described, Parameterized {

	/**
	 * @param parameter
	 */
	public void setParameter(Parameter parameter);
	
	public void setParameter(String name, String valueString);
	
	/**
	 * @return
	 */
//	public ParameterList getParameters();

	/**
	 * @param parameters
	 */
	public void update(ParameterList parameters);

	/**
	 * @return
	 */
	public ParameterList getParameters();
	
	/**
	 * 
	 */

}
