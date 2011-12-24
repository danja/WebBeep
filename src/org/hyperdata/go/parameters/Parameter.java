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
//	public void setDatatype(String datatype);
//	public String getDatatype();
	/**
	 * @param d
	 */
	public void setValue(Object object);
	/**
	 * @return
	 */
	public Parameter clone();
	/**
	 * @param processor
	 */
	public void setProcessor(Parameterized processor);

}
