/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.Set;

import org.hyperdata.beeps.util.Tone;

/**
 * 
 * Given an audio sample, find the most prevalent pitches in the sample,
 * i.e. decompose complex wave into components, select those of greatest power.
 * 
 * Implementations should identify musical pitches based on A = 440Hz (which may be derived from 
 * nearest pitches in the audio sample) rather than arbitrary frequencies 
 * 
 * 
 * @author danny
 *
 */
public interface PitchFinderGeneral {
	/**
	 * @param tone audio sample
	 * @return frequencies of contained pitches
	 */
	public Set<Double> findPitches(Tone tone);
}
