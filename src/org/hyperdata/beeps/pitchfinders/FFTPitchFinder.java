/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.config.Maps;
import org.hyperdata.beeps.old.FixedEncoder;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 *         sensible fftBits = 10 fftMax = 1024 peakDelta = 0.5
 * 
 */
public class FFTPitchFinder extends DefaultPitchFinder { // was extends
															// DefaultProcessor

	int fftBits = Constants.FFT_BITS;
	int fftMax = Constants.FFT_MAX;
	double peakDelta = Constants.PEAK_DELTA;
	boolean repeatToFit = false;

	public FFTPitchFinder(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */

	public void initFromParameters() {
		fftBits = (Integer) getLocal("fftBits");
		fftMax = (int) Math.pow(2, fftBits);
		peakDelta = (Double) getLocal("peakDelta");
		repeatToFit = ((String) getLocal("repeatToFit")).equals("on");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util
	 * .Tone)
	 */
	// @Override
	// public Tone process(Tone input) {
	// return process(input);
	// }

	public static void main(String[] args) {
		FixedEncoder encoder = new FixedEncoder();
		Tone tones = encoder.encode("a");
		// Plotter.plot(tones, "Tones");
		// System.out.println("tones=" + tones.size());

		FFTPitchFinder finder = new FFTPitchFinder("test");
		Map<Double, Double> pitches = finder.findPairs(tones);

		Set<Double> keys = pitches.keySet();
		Iterator<Double> iterator = keys.iterator();
		while (iterator.hasNext()) {
			Double freq = iterator.next();
			Double amplitude = pitches.get(freq);
			System.out.println("Freq : " + freq + "    amplitude=" + amplitude
					+ "?");
		}

	}

	public Set<Double> findPitches(Tone tone) {
		Map<Double, Double> pitches = findPairs(tone);
		Set<Double> keys = pitches.keySet();
		Iterator<Double> iterator = keys.iterator();
		Set<Double> freqs = new HashSet<Double>();

		while (iterator.hasNext()) {
			double freq = iterator.next();
			freq = Maps.findNearestNote(freq); // tweak to known nots
			freqs.add(freq);
		}

		return freqs;
	}

	public Map<Double, Double> findPairs(Tone tones) {

		// System.out.println("\nfftBits = "+fftBits);
		//
		// System.out.println("tones.size() = "+tones.size());
		// System.out.println("fftMax = "+fftMax);
		// System.out.println("peakDelta = "+peakDelta);
		if (tones.size() == 0) {
			Debug.log("tones.size()==0");
			return new HashMap<Double, Double>();
		}
		if (repeatToFit && (tones.size() < fftMax)) {
			Tone tonesCopy = new Tone(tones);
			while (tones.size() < fftMax) {
				System.out.println("tones.size()=" + tones.size() + " fftMax="
						+ fftMax);
				tones.addAll(tonesCopy);
			}
		}
		if (tones.size() > fftMax) {
			tones = new Tone(tones.subList(0, fftMax));
		}

		FFT fft = new FFT(fftBits);

		// only need the chunk of the FFT up to the max expected freq
		int trim = (int) (Maps.MAX_HIGH_FREQ_CUTOFF * (double) fftMax / (double) Constants.SAMPLE_RATE);

		List<Double> f = fft.doPowerFFT(tones, false).subList(0, trim);

		Tone freqs = new Tone(f);
		// values from the FFT are very small
		Processor normalise = new Normalise("FFT.normalise");
		freqs = normalise.process(freqs);

		Plotter plotter = Plotter.plot(freqs, "Freqs", 4, true);

		// If... she.. weighs the same as a duck, she's made of wood.
		List<Map<Integer, Double>> peaks = PeakDetector.peak_detection(freqs,
				peakDelta);

		Map<Integer, Double> peak = peaks.get(0); // only peaks (get(i) gives
													// minima)

		Map<Double, Double> pitches = new HashMap<Double, Double>();

		Set<Integer> keys = peak.keySet();
		Iterator<Integer> iterator = keys.iterator();

		while (iterator.hasNext()) {
			Integer key = iterator.next();
			double freq = key * (double) Constants.SAMPLE_RATE
					/ (double) Constants.FFT_MAX;
			// n * SAMPLE_RATE / FFT_SIZE = 43.1 Hz
			Double amplitude = peak.get(key);

			plotter.addPoint(key, amplitude);
			// System.out.println("adding point freq="+key+"   amplitude="+amplitude);

			System.out.println("adding  point=" + key + "  freq=" + freq
					+ "  power=" + amplitude);

			pitches.put(freq, amplitude);
		}

		return pitches;
	}
}
