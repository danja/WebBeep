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
public interface SplittingProcessor extends Parameterized {
	public List<List<Double>> process(List<Double> input);
	public Chunks process(Tone input);
}
