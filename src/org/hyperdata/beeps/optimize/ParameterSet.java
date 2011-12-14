/**
 * 
 */
package org.hyperdata.beeps.optimize;

/**
 * @author danny
 *
 */
public interface ParameterSet {
	public void addParameter(Parameter param);
	public Object getValue(String name);
}
