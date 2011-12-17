/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public interface Pipeline {

	public void addProcessor(Processor processor);
//	public List<Double> applyProcessors(List<Double> input);
	public Tone applyProcessors(Tone tone);
	public int size();
}
