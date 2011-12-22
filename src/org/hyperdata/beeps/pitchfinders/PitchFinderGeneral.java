/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.List;
import java.util.Set;

import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public interface PitchFinderGeneral {
	public Set<Double> findPitches(Tone tone);
}
