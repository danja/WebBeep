/**
 * 
 */
package org.hyperdata.beeps.goertzel;

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
public class GoertzelDecoder {

}
