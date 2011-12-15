/**
 * 
 */
package org.hyperdata.beeps.optimize;

/**
 * @author danny
 *
 */
public interface Parameter {

	public void setName(String name);
	public String getName();
	// public void init();
	public void initRandom();
	public Object getValue();
}
