/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.List;

import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public interface Pipeline extends  ComponentList {

	public void addProcessor(Processor processor);
//	public List<Double> applyProcessors(List<Double> input);

	

	/**
	 * @param input
	 * @return
	 */
	public Tone process(Tone input);



	/**
	 * @param chunks
	 * @return
	 */
	public Chunks process(Chunks chunks);
	
//	public Processor get(int i);
	
	// public void updateParameters(ParameterList parameters);

	/**
	 * @return
	 */
	
	

}
