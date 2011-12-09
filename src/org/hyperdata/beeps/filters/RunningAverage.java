/**
 * 
 */
package org.hyperdata.beeps.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danny
 *
 */
public class RunningAverage {

	public static List<Double> filter(List<Double> tones, int windowLength) {
	
		List<Double> filtered = new ArrayList<Double>();
	
	//	int WINDOW_LENGTH = (int) ((double) Constants.SAMPLE_RATE * WINDOW_TIME);
		// System.out.println(WINDOW_LENGTH);
		double max = 0;
		double total = 0;
	
		for (int i = 0; i < tones.size() - windowLength - 1; i++) {
			double sum = 0;
			for (int j = 0; j < windowLength; j++) {
			//	sum += Math.abs(tones.get(i + j))/windowLength;
				sum += tones.get(i + j);
			}
			sum *= 2/windowLength; // not sure if/why the 2 is needed
			// System.out.println(sum);
			
			filtered.add(sum); // add to end of ArrayList
		}
		return filtered;
	}

}
