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
}
