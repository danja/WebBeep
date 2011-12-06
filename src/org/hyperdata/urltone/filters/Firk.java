package org.hyperdata.urltone.filters;

/*
 * ported from C and tweaked by Danny
 * 
 Windowed Sinc FIR Coefficients Generator
 
 Bob Maling (BobM.DSP@gmail.com)
 Contributed to musicdsp.org Source Code Archive
 Last Updated: April 12, 2005

 Usage:
 Lowpass:	LP(h, N, WINDOW, fc)
 Highpass:	HP(h, N, WINDOW, fc)
 Bandpass:	BP(h, N, WINDOW, fc1, fc2)
 Bandstop:	BS(h, N, WINDOW, fc1, fc2)

 where:
 h (double[N]):	filter coefficients will be written to this array
 N (int):		number of taps
 WINDOW (int):	Window (W_BLACKMAN, W_HANNING, or W_HAMMING)
 fc (double):	cutoff (0 < fc < 0.5, fc = f/fs)
 --> for fs=48kHz and cutoff f=12kHz, fc = 12k/48k => 0.25

 fc1 (double):	low cutoff (0 < fc < 0.5, fc = f/fs)
 fc2 (double):	high cutoff (0 < fc < 0.5, fc = f/fs)


 Windows included here are Blackman, Hanning, and Hamming. Other windows	can be
 added by doing the following:
 1. "Window type constants" section: Define a global constant for the new window.
 2. LP() function: Add the new window as a case in the switch() statement.
 3. Create the function for the window

 For windows with a design parameter, such as Kaiser, some modification
 will be needed for each function in order to pass the parameter.
 */

public class Firk {

	// Window type constants
	final static int BLACKMAN = 1;
	final static int HANNING = 2;
	final static int HAMMING = 3;

	// Generate lowpass filter
	//
	// This is done by generating a sinc function and then windowing it
	public static double[] LP(int N, // size of the filter (number of taps)
			int WINDOW, // window function (W_BLACKMAN, W_HANNING, etc.)
			double fc) // cutoff frequency
	{
		int i;
		double w[] = new double[N]; // window function
		double sinc[] = genSinc(N, fc);

		// 2. Generate Window function
		switch (WINDOW) {
		case BLACKMAN: // W_BLACKMAN
			w = wBlackman(N);
			break;
		case HANNING: // W_HANNING
			w = wHanning(N);
			break;
		case HAMMING: // W_HAMMING
			w = wHamming(N);
			break;
		default:
			break;
		}

		double[] h = new double[N];
		// 3. Make lowpass filter
		for (i = 0; i < N; i++) {
			h[i] = sinc[i] * w[i];
		}

		return h;
	}

	// Generate highpass filter
	//
	// This is done by generating a lowpass filter and then spectrally inverting
	// it
	public static double[] HP(int N, // size of the filter
			int WINDOW, // window function (W_BLACKMAN, W_HANNING, etc.)
			double fc) // cutoff frequency
	{
		int i;

		// 1. Generate lowpass filter
		double h[] = LP(N, WINDOW, fc);

		// 2. Spectrally invert (negate all samples and add 1 to center sample)
		// lowpass filter
		// = delta[n-((N-1)/2)] - h[n], in the time domain
		for (i = 0; i < N; i++) {
			h[i] *= -1.0; // = 0 - h[i]
		}
		h[(N - 1) / 2] += 1.0; // = 1 - h[(N-1)/2]

		return h;
	}

	// Generate bandstop filter
	//
	// This is done by generating a lowpass and highpass filter and adding them
	public static double[] BS(
			int N, // size of the filter
			int WINDOW, // window function (W_BLACKMAN, W_HANNING, etc.)
			double fc1, // low cutoff frequency
			double fc2) // high cutoff frequency
	{
		double h[] = new double[N];
		int i;

		// 1. Generate lowpass filter at first (low) cutoff frequency
		double h1[] = LP(N, WINDOW, fc1);

		// 2. Generate highpass filter at second (high) cutoff frequency
		double h2[] = HP(N, WINDOW, fc2);

		// 3. Add the 2 filters together
		for (i = 0; i < N; i++) {
			h[i] = h1[i] + h2[i];
		}
		return h;
	}

	// Generate bandpass filter
	//
	// This is done by generating a bandstop filter and spectrally inverting it
	public static double[] BP(
			int N, // size of the filter
			int WINDOW, // window function (W_BLACKMAN, W_HANNING, etc.)
			double fc1, // low cutoff frequency
			double fc2) // high cutoff frequency
	{
		
		int i;

		// 1. Generate bandstop filter
		double h[] =  BS(N, WINDOW, fc1, fc2);

		// 2. Spectrally invert bandstop (negate all, and add 1 to center
		// sample)
		for (i = 0; i < N; i++) {
			h[i] *= -1.0; // = 0 - h[i]
		}
		h[(N - 1) / 2] += 1.0; // = 1 - h[(N-1)/2]

		return h;
	}

	// Generate sinc function - used for making lowpass filter
	public static double[] genSinc(
			int N, // size (number of taps)
			double fc) // cutoff frequency
	{
		int i;
		double M = N - 1;
		double n;

		double sinc[] = new double[N];
		// Constants
		// double PI = 3.14159265358979323846;

		// Generate sinc delayed by (N-1)/2
		for (i = 0; i < N; i++) {
			if (i == M / 2.0) {
				sinc[i] = 2.0 * fc;
			} else {
				n = i - M / 2.0;
				sinc[i] = Math.sin(2.0 * Math.PI * fc * n) / (Math.PI * n);
			}
		}

		return sinc;
	}

	// Generate window function (Blackman)
	public static double[] wBlackman(int N) {
		double w[] = new double[N];
		int i;
		double M = N - 1;
		double PI = 3.14159265358979323846;

		for (i = 0; i < N; i++) {
			w[i] = 0.42 - (0.5 * Math.cos(2.0 * PI * i / M))
					+ (0.08 * Math.cos(4.0 * PI * i / M));
		}

		return w;
	}

	// Generate window function (Hanning)
	public static double[] wHanning(int N) {
		int i;
		double M = N - 1;
		// double PI = 3.14159265358979323846;

		double w[] = new double[N];
		
		for (i = 0; i < N; i++) {
			w[i] = 0.5 * (1.0 - Math.cos(2.0 * Math.PI * i / M));
		}

		return w;
	}

	// Generate window function (Hamming)
	public static double[] wHamming(int N) {
		double w[] = new double[N];
		int i;
		double M = N - 1;
		// double PI = 3.14159265358979323846;

		for (i = 0; i < N; i++) {
			w[i] = 0.54 - (0.46 * Math.cos(2.0 * Math.PI * i / M));
		}

		return w;
	}
}
