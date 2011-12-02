/**
NOT YET TESTED!!!
 * 
 */
package org.hyperdata.urltone.decode;

import java.util.List;

/**
 * @author danny
 * 
 */
public class PreProcess {

	/**
	 * Normalises values in List scaling to peak value = +/- 1 and/or removing
	 * zero offset)
	 * 
	 * @param tones
	 *            input data
	 * @return normalised data
	 */
	public static List<Double> normalise(List<Double> tones,
			boolean normaliseScale, boolean normaliseOffset) {
		if (!normaliseScale && !normaliseOffset) {
			return tones; // silly caller
		}
		double min = 0;
		double max = 0;
		for (int i = 0; i < tones.size(); i++) {
			if (tones.get(i) < min) {
				min = tones.get(i);
			}
			if (tones.get(i) > max) {
				max = tones.get(i);
			}
		}
		double peakAmplitude = max - min;

		double offset = 0;
		double scale = 1;
		if (normaliseOffset) {
			offset = peakAmplitude / 2 - min;
			if (normaliseScale) {
				scale = 1 / peakAmplitude;
			}
		} else {
			if (normaliseScale) {
				double absmax = max > min ? max : min;
				scale = 1 / absmax;
			}
		}
		for (int i = 0; i < tones.size(); i++) {
			tones.set(i, tones.get(i) - offset);
		}
		return tones;
	}
}
