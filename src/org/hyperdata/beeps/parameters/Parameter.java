/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.pipelines.Processor;



/**
 * @author danny
 *
 */
public interface Parameter extends Named {

//	public void setName(String name);
//	public String getName();
	// public void init();
	public void initRandom();
	public Object getValue();
	public ParameterList getProcessor();
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
	public void setProcessor(ParameterList processor);

}
