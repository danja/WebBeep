package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.util.Tone;


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
//	public List<Double> applyPreProcessors(List<Double> input);
//	public List<Double> applyPostProcessors(List<Double> input);
	public Tone applyPreProcessors(Tone input);
	public Tone applyPostProcessors(Tone input);
}
