/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.pipelines.Processor;

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
	public Processor getProcessor();
}
