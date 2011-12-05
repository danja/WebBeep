/**
 * I was mostly using static methods and not bothering with inheritance etc,
 * refactored solely for naming purposes...
 */
package org.hyperdata.urltone.decode;

import java.util.List;
import java.util.Map;

/**
 * @author danny
 * 
 */
public interface PitchFinderGeneral {
	
	// public Map<Double, Double> findPairs(List<Double> tones);
	public List<Double> findPitches(List<Double> tones);
}
