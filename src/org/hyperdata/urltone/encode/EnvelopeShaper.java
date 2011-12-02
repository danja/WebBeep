/**
 * 
 */
package org.hyperdata.urltone.encode;

import java.util.List;

/**
 * @author danny
 * 
 */
public class EnvelopeShaper {

	static double ATTACK_PROPORTION = 0.0625;
	static double DECAY_PROPORTION = 0.125;

	public static List<Double> applyEnvelope(List<Double> samples) {
		// System.out.println("SAMPLES="+samples.size());
		double attackMarker = ((double) samples.size()) * ATTACK_PROPORTION;
		// System.out.println("Attack marker="+attackMarker);
		double decayMarker = ((double) samples.size())
				* (1.0 - DECAY_PROPORTION);
		// System.out.println("Decay marker="+decayMarker);
		for (int i = 0; i < attackMarker; i++) {

			double scale = ((double) i) / attackMarker;
			// double value = samples.get(i) - WaveMaker.MAX_VALUE/2;
			// System.out.println("Attack Scaling by "+scale);
			samples.set(i, scale * samples.get(i));
		}

		for (int i = samples.size()-1; i > decayMarker; i--) {
			double scale = 1.0-((double)(i-decayMarker)) / (double)(samples.size()-decayMarker);
			// System.out.println("Decay Scaling by "+scale);
			samples.set(i, scale * samples.get(i));
		}

		return samples;
	}
}
