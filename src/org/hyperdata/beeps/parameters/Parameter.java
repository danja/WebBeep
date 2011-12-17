/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.pipelines.Parameterized;

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
}
