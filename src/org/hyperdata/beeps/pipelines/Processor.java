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
public interface Processor extends Parameterized {
//	public List<Double> process(List<Double> input);
//	public List<List<Double>> processMulti(List<List<Double>> input);
	
	public Tone process(Tone input);
	public Chunks processMulti(Chunks input);
}
