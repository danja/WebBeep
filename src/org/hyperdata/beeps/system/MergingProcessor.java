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
public interface MergingProcessor extends ParameterList {
	// public List<Double> process(List<List<Double>> input);
	public Tone process(Chunks input);
}
