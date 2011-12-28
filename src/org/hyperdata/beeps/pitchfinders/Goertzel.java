/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 * s_prev = 0
 * s_prev2 = 0
 * normalized_frequency = target_frequency / sample_rate;
 * coeff = 2*cos(2*PI*normalized_frequency);
 * for each sample, x[n],
 *   s = x[n] + coeff*s_prev - s_prev2;
 *   s_prev2 = s_prev;
 *   s_prev = s;
 * end
 * power = s_prev2*s_prev2 + s_prev*s_prev - coeff*s_prev*s_prev2;
 */
public class Goertzel {

	public double getPower(Tone tone, double targetFreq){
		 double sPrevious = 0;
		 double sPrevious2 = 0;
		 
		 double coeff = 2*Math.cos(2 * Math.PI * targetFreq/Constants.SAMPLE_RATE);
		 
		 for(int i=0;i<tone.size();i++){
			 double val = tone.get(i);
			double s = val + coeff*sPrevious - sPrevious2;
			sPrevious2 = sPrevious;
			sPrevious = s;
		 }
		return sPrevious2 * sPrevious2 + sPrevious * sPrevious - coeff * sPrevious * sPrevious2;
	}
}
