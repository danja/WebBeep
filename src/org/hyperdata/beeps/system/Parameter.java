/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.describe.Named;




/**
 * @author danny
 *
 */
public interface Parameter extends Named {

	public void setValue(Object value);
	public Object getValue();
	
	public void initRandom();

	public ParameterList getProcessor();

	
	/**
	 * @return
	 */
	public Parameter clone();
	/**
	 * @param processor
	 */
	public void setProcessor(ParameterList processor);
	/**
	 * @param value
	 */
	public void setValue(String value);

}
