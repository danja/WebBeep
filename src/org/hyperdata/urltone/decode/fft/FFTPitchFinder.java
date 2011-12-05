/**
 * 
 */
package org.hyperdata.urltone.decode.fft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hyperdata.urltone.Encoder;
import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Maps;
import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.decode.PitchFinderGeneral;
import org.hyperdata.urltone.decode.PreProcess;

/**
 * @author danny
 * 
 */
public class FFTPitchFinder implements PitchFinderGeneral {

	public static void main(String[] args) {
		List<Double> tones = Encoder.encode("a");
	//	Plotter.plot(tones, "Tones");
//		System.out.println("tones=" + tones.size());

		FFTPitchFinder finder = new FFTPitchFinder();
		Map<Double, Double> pitches = finder.findPairs(tones);

		Set<Double> keys = pitches.keySet();
		Iterator<Double> iterator = keys.iterator();
		while (iterator.hasNext()) {
			Double freq = iterator.next();
			Double amplitude = pitches.get(freq);
//			System.out
//					.println("Freq : " + freq + "    amplitude :" + amplitude);
		}

	}

	public List<Double> findPitches(List<Double> tones) {
		Map<Double, Double> pitches = findPairs(tones);
		Set<Double> keys = pitches.keySet();
		Iterator<Double> iterator = keys.iterator();
		List<Double> freqs = new ArrayList<Double>();
		
		while (iterator.hasNext()) {
			freqs.add(iterator.next());
		}
		return freqs;
	}
	
	public Map<Double, Double> findPairs(List<Double> tones) {

		FFT fft = new FFT(Constants.FFT_BITS);

		// only need the chunk of the FFT up to the max expected freq
		int trim = (int) (Maps.MAX_HIGH_FREQ_CUTOFF
				* (double) Constants.FFT_MAX / (double) Constants.SAMPLE_RATE);

		List<Double> freqs = fft.doPowerFFT(tones, false).subList(0, trim);

		// values from the FFT are very small
		freqs = PreProcess.normalise(freqs, true, true);

//		Plotter plotter = Plotter.plot(freqs, "Freqs", 4, true);
		
		// If... she.. weighs the same as a duck, she's made of wood.
		List<Map<Integer, Double>> peaks = PeakDetector.peak_detection(freqs, 0.5);

		Map<Integer, Double> peak = peaks.get(0); // only peaks (get(i) gives
													// minima)

		Map<Double, Double> pitches = new HashMap<Double, Double>();
		
		Set keys = peak.keySet();
		Iterator<Integer> iterator = keys.iterator();
		
		while (iterator.hasNext()) {
			Integer key = iterator.next();
			double freq = key * (double) Constants.SAMPLE_RATE
					/ (double) Constants.FFT_MAX;
			// n * SAMPLE_RATE / FFT_SIZE = 43.1 Hz
			Double amplitude = peak.get(key);
			
		//	plotter.addPoint(key, amplitude);
		//	System.out.println("adding point "+key+"   "+amplitude);
			pitches.put(freq, amplitude);
			// System.out.println("pitches.size()="+pitches.size());
		}
		
		return pitches;
	}

}
