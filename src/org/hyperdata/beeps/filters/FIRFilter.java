/**
 Usage:
 Lowpass:	LP(N, WINDOW, fc)
 Highpass:	HP(N, WINDOW, fc)
 Bandpass:	BP(N, WINDOW, fc1, fc2)
 Bandstop:	BS(N, WINDOW, fc1, fc2)

 where:
 h (double[N]):	filter coefficients will be written to this array
 N (int):		number of taps
 WINDOW (int):	Window (W_BLACKMAN, W_HANNING, or W_HAMMING)
 
 CUTOFF now expressed as frequency
// fc (double):	cutoff (0 < fc < 0.5, fc = f/fs)
// --> for fs=48kHz and cutoff f=12kHz, fc = 12k/48k => 0.25

 fc1 (double):	low cutoff (0 < fc < 0.5, fc = f/fs)
 fc2 (double):	high cutoff (0 < fc < 0.5, fc = f/fs)
 * 
 */
package org.hyperdata.beeps.filters;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.encode.WaveMaker;
import org.hyperdata.beeps.fft.FFT;
import org.hyperdata.beeps.util.Noise;
import org.hyperdata.beeps.util.Plotter;

/**
 * @author danny
 * 
 */
public class FIRFilter {

	public static final int LP = 0;
	public static final int HP = 1;
	public static final int BP = 2;
	public static final int BS = 3;

	private double[] weights;

	public FIRFilter(int shape, int nPoints, int window, double fc) {
		// double[] weights;
		switch (shape) {
		case LP:
			weights = Firk.LP(nPoints, window, fc/(double)Constants.SAMPLE_RATE);
			break;
		case HP:
			weights = Firk.HP(nPoints, window, fc/(double)Constants.SAMPLE_RATE);
			break;
		}
	}

	public FIRFilter(int shape, int nPoints, int window, double fc1, double fc2) {
		switch (shape) {
		case BP:
			weights = Firk.BP(nPoints, window, fc1/(double)Constants.SAMPLE_RATE, fc2/(double)Constants.SAMPLE_RATE);
			break;
		case BS:
			weights = Firk.BS(nPoints, window, fc1/(double)Constants.SAMPLE_RATE, fc2/(double)Constants.SAMPLE_RATE);
			break;
		}
	}

	public List<Double> filter(List<Double> input) {

		// double in[] = input.toArray<Double>();
		
		List<Double> output = new ArrayList<Double>();

		for (int i = 0; i < input.size() - weights.length; i++) {

			double y = 0;

			for (int j = 0; j < weights.length; j++) {
				y = y + weights[j] * input.get(i + j);
			}
			output.add(y);
		}
		return output;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<Double> white = Noise.white(0.5);
		int taps = 100;
		FFT fft = new FFT(15);
		
		long startTime = System.currentTimeMillis();

		FIRFilter filterLP = new FIRFilter(LP, taps, Firk.HAMMING, 1000);
		List<Double> time = filterLP.filter(white);
		
		long thisTime = System.currentTimeMillis();
		// System.out.println("time: " + (float)(thisTime - startTime)/1000 + " seconds");
		// Plotter.plot(fft.fft(time), "LP 1kHz, Hamming "+taps+" taps");
		
		startTime = System.currentTimeMillis();
		FIRFilter filterHP = new FIRFilter(HP, taps, Firk.HANNING, 1000);
		time = filterHP.filter(white);
		thisTime = System.currentTimeMillis();
		// System.out.println("time: " + (float)(thisTime - startTime)/1000 + " seconds");
		// Plotter.plot(fft.fft(time), "HP 1kHz Hanning "+taps+" taps");
		

		
		startTime = System.currentTimeMillis();
		FIRFilter filterBS = new FIRFilter(BS, taps, Firk.BLACKMAN, 1000, 2000);
		time = filterBS.filter(white);
		thisTime = System.currentTimeMillis();
		// System.out.println("time: " + (float)(thisTime - startTime)/1000 + " seconds");
		// Plotter.plot(fft.fft(time), "BS 1kHz, 2kHz Blackman "+taps+" taps");
		
		taps = 128;
		startTime = System.currentTimeMillis();
		FIRFilter filterBP = new FIRFilter(BP, taps, Firk.BLACKMAN, 999, 1001);
	//	for(int i=0;i<100;i++){
		time = filterBP.filter(white);
	//	}
		thisTime = System.currentTimeMillis();
		System.out.println("time: " + (float)(thisTime - startTime)/1000 + " seconds");
		Plotter plotter = new Plotter();
		plotter.setPointSize(8);
		Plotter.plot(4, fft.fft(time), "BP 999Hz, 1001kHz Blackman "+taps+" taps");
		
		taps = 512;
		startTime = System.currentTimeMillis();
		FIRFilter filterBPtight = new FIRFilter(BP, taps, Firk.BLACKMAN, 999, 1001 );
	//	for(int i=0;i<100;i++){
		time = filterBPtight.filter(white);
	//	}
		thisTime = System.currentTimeMillis();
		System.out.println("time: " + (float)(thisTime - startTime)/1000 + " seconds");
		Plotter.plot(4, fft.fft(time), "BP fc=999,1001, Hamming "+taps+" taps");
	}

}
