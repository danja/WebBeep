/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.parameters.Parameterized;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public interface MergingProcessor extends Parameterized {
	// public List<Double> process(List<List<Double>> input);
	public Tone process(Chunks input);
}
