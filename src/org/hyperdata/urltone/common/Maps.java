/**
 * 
 */
package org.hyperdata.urltone.common;

/**
 * @author danny
 * 
 */
public class Maps {

	// C D E G A - pentatonic (starts on G compared to black notes C#

	public static String[] HIGH_ABC = { "c", "d", "e", "g", "a", "c", "d", "e",
			"g", "a", "c'", "d'", "e'", "g'", "a'", "g'" };

	public static int[] HIGH_BEATS = { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1,
			1, 1, 2 };

	public static double[] HIGH_FREQ = { 523.25, 587.33, 659.26, 783.99, 880,
			523.25, 587.33, 659.26, 783.99, 880, 1046.5, 1174.66, 1318.51,
			1567.98, 1760, 1567.98};

	public static String[] LOW_ABC = { "C", "D", "E", "G", "A", "C", "D", "G" };

	public static int[] LOW_BEATS = { 1, 1, 1, 1, 1, 2, 2, 2 };

	public static double[] LOW_FREQ = { 261.63, 293.66, 329.63, 392, 440,
			261.63, 293.66, 392 };
	
	public static double MIN_LOW_FREQ = LOW_FREQ[0];
	public static double MAX_LOW_FREQ = LOW_FREQ[0];
	public static double MIN_HIGH_FREQ = HIGH_FREQ[0];
	public static double MAX_HIGH_FREQ = HIGH_FREQ[0];
	
	static{
		for(int i=0;i<LOW_FREQ.length;i++){
			if(LOW_FREQ[i]<MIN_LOW_FREQ) MIN_LOW_FREQ = LOW_FREQ[i];
			if(LOW_FREQ[i]>MAX_LOW_FREQ) MAX_LOW_FREQ = LOW_FREQ[i];
			if(HIGH_FREQ[i]<MIN_HIGH_FREQ) MIN_HIGH_FREQ = HIGH_FREQ[i];
			if(HIGH_FREQ[i]>MAX_HIGH_FREQ) MAX_HIGH_FREQ = HIGH_FREQ[i];
		}
	}
	
	public static void main(String args[]){
		System.out.println(MIN_LOW_FREQ);
		System.out.println(MAX_LOW_FREQ);
		System.out.println(MIN_HIGH_FREQ);
		System.out.println(MAX_HIGH_FREQ);
	}
}
