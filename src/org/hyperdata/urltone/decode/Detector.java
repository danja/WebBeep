/**
 * 
 */
package org.hyperdata.urltone.decode;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.urltone.Encoder;
import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.WavCodec;

/**
 * @author danny
 * 
 *         rectifier+ simple moving-average filter
 * 
 *         used to locate start of tone
 */
public class Detector {

	static double WINDOW_TIME = 0.04; // seconds

/**
 * Rectifies input and applies a running average filter
 * 
 * if breakLevel <= 0, will operate on full signal
 * otherwise will return when (smoothed) value > breakLevel
 * 
 * note the length of the returned filtered list will correspond
 * to the point in time at which the breakLevel amplitude was surpassed
 * (making full use of the Collection object!)
 * 
 * @param tones input waveform
 * @param breakLevel amplitude of smoothed output that will cause method to return
 * @return smoothed amplitude of input
 */
	public static List<Double> filter(List<Double> tones, double breakLevel) {

		List<Double> filtered = new ArrayList<Double>();

		int WINDOW_LENGTH = (int) ((double) Constants.SAMPLE_RATE * WINDOW_TIME);
		System.out.println(WINDOW_LENGTH);
		double max = 0;
		double total = 0;

		for (int i = 0; i < tones.size() - WINDOW_LENGTH - 1; i++) {
			double sum = 0;
			for (int j = 0; j < WINDOW_LENGTH; j++) {
				sum += Math.abs(tones.get(i + j))/WINDOW_LENGTH;
			}
			sum *= 2; // not sure if/why this is needed
			System.out.println(sum);
			
			if (sum > max)
				max = sum;

			filtered.add(sum); // add to end of ArrayList
			if(breakLevel > 0 && sum > breakLevel) { // if < 0 keep going for full length
				return filtered; // otherwise will only contain early part
			}
		}
		return filtered;
	}
	
	/**
	 * 
 * Rectifies input and applies a running average filter
 * 
 * 
 * @param tones input waveform
 * @param breakLevel amplitude of smoothed output that will cause method to return
 * @return smoothed amplitude of input
	 */
	public static List<Double> filter(List<Double> tones) {
		return filter(tones, -1);
	}

	
	static String IRI = "http://dannyayers.com/stuff"; // "OK" is good!
	static String filename = "/home/danny/workspace/UTone/data/filtered.wav";

	public static void main(String[] args) {
		List<Double> tones = Encoder.makeTones(IRI);
		tones = filter(tones, 0.75);
		System.out.println(tones.size());
		WavCodec.save(filename, tones);
	}
}
