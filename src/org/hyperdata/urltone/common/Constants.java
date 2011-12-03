/**
 * 
 */
package org.hyperdata.urltone.common;

/**
 * @author danny
 *
 */
public class Constants {

	// Values for mono, 16bit, 44.1kHz
	public static final double AMPLITUDE = 0.99;
	public static final int BITS_PER_SAMPLE = 16; 
	public static final int BYTES_PER_SAMPLE = 2; 
	public static final double MAX_VALUE = 32767;
	public static final int SAMPLE_RATE = 44100;
	public static final int N_SAMPLES = SAMPLE_RATE;
	// 140 bpm
	//	0.58333333 barsps
	//	/2 = 0.291666665
	//	*2 =1.16666666
	//	*2 = 2.33333333
		//0.145833333;
		// 0.0729166667
	
	// Chunk parameters
		public static double TONE_DURATION = 0.145833333;
		public static double END_PAD_DURATION = 0.145833333;
		public static double SILENCE_DURATION = 0;
		public static double START_PAD_DURATION = 0.0729166667;
		
		// Decode parameters
		public static double START_LEVEL = 0.75;
		public static double CROP_PROPORTION = 0.5;

}
