package org.hyperdata.beeps.pipelines;

import java.util.List;


/**
 * 
 */

/**
 * @author danny
 * 
 *
 */
public interface Codec {
	public void initProcessors();
	public void addPreProcessor(Processor processor);
	public void addPostProcessor(Processor processor);
	public List<Double> applyPreProcessors(List<Double> input);
	public List<Double> applyPostProcessors(List<Double> input);
}
