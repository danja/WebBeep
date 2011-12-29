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
public interface SplittingProcessor {
	// public List<List<Double>> process(List<Double> input);
	public Chunks process(Tone input);
}
