/**
 * 
 */
package org.hyperdata.go.parameters;


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
	public Parameterized getProcessor();
	/**
	 * @param d
	 */
	public void setValue(Object object);
}