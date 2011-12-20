/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public interface PitchfinderGeneral {
	public List<Double> findPitches(Tone tone);
}
