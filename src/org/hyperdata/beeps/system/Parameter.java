/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.describe.Described;
import org.hyperdata.common.describe.Named;




/**
 * @author danny
 *
 */
public interface Parameter extends Named, Described {

	public void setValue(Object value);
	public Object getValue();
	
	public void initRandom();

	public ParameterList getParameterList();

	
	/**
	 * @return
	 */
	public Parameter clone();
	/**
	 * @param processor
	 */
	public void setParameterList(ParameterList parameterList);
	/**
	 * @param value
	 */
	public void setValue(String value);

}
