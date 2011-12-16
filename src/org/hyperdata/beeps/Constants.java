/**
 * 
 */
package org.hyperdata.beeps;

/**
 * @author danny
 *
 */
public class Constants {
	/*
	 * Preset values for mono, 16bit, 44.1kHz
	 */
	public static final double AMPLITUDE = 0.49;
	public static final int BITS_PER_SAMPLE = 16; 
	public static final int BYTES_PER_SAMPLE = 2; 
	public static final double MAX_VALUE = 32767;
	public static final int SAMPLE_RATE = 44100; // 22050;44100
//	public static final int N_SAMPLES = SAMPLE_RATE;
	// 140 bpm
	//	0.58333333 barsps
	//	/2 = 0.291666665
	//	*2 =1.16666666
	//	*2 = 2.33333333
		//0.145833333;
		// 0.0729166667
	public static final int FFT_BITS = 12; // is ok at 18
	public static final int FFT_MAX = (int)Math.pow(2,FFT_BITS);
	
	/* 
	 * Chunk parameters
	 */
		public static double TONE_DURATION = 0.145833333/1.5;
		// Silence between tones not handled correctly : tone/tone/gap etc
		public static double SILENCE_DURATION = 0;
		
		public static double START_PAD_DURATION = 0.0729166667;
		public static double END_PAD_DURATION = 0.145833333;
		
		/* 
		 * Encode parameters
		 */
		public static double ENCODE_ATTACK_PROPORTION = 0.0625;
		public static double ENCODE_DECAY_PROPORTION = 0.125;
		
		/* 
		 * Decode parameters
		 */
		public static double SILENCE_THRESHOLD = 0.5; // was 0.3 for cropping - below this level assumed no sound
		public static double CROP_PROPORTION = 0.5; // was 0.5
		public static double EDGE_WINDOW_PROPORTION = 0.01;
		
		// proportion between actual min/max freqs and filter cutoff freqs
		public static double CUTOFF_PADDING = 1.1;
		public static double LP_LOW = 900;
		public static double LP_HIGH = 12000;
		public static double HP_LOW = 30;
		public static double HP_HIGH = 300;
}
