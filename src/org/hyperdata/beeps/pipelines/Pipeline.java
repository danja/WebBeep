/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public interface Pipeline extends Processor {

	public void addProcessor(Processor processor);
//	public List<Double> applyProcessors(List<Double> input);

	public int size();

}
