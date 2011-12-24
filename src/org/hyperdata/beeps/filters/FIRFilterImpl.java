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
import org.hyperdata.beeps.WaveMaker;
import org.hyperdata.beeps.pitchfinders.FFT;
import org.hyperdata.beeps.util.Noise;
import org.hyperdata.beeps.util.Plotter;

/**
 * @author danny
 * 
 */
public class FIRFilterImpl implements FIRFilter {

	private double[] weights;

	private int shape;
	private int nPoints;
	private int window;
	private double fc1; 
	private double fc2;
	
	/**
	 * @return the fc2
	 */
	public double getFc2() {
		return this.fc2;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setFc2(double)
	 */
	@Override
	public void setFc2(double fc2) {
		this.fc2 = fc2;
	}

	/**
	 * @return the shape
	 */
	public int getShape() {
		return this.shape;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setShape(int)
	 */
	@Override
	public void setShape(int shape) {
		this.shape = shape;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setShapeName(java.lang.String)
	 */
	@Override
	public void setShapeName(String shapeName) {
		if(shapeName.equals("LP")){
			setShape(LP);
			return;
		}
		if(shapeName.equals("HP")){
			setShape(HP);
			return;
		}
		if(shapeName.equals("BP")){
			setShape(BP);
			return;
		}
		if(shapeName.equals("BS")){
			setShape(BS);
		}
	}

	/**
	 * @return the nPoints
	 */
	public int getnPoints() {
		return this.nPoints;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setnPoints(int)
	 */
	@Override
	public void setnPoints(int nPoints) {
		this.nPoints = nPoints;
	}

	/**
	 * @return the fc
	 */
	public double getFc() {
		return this.fc1;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setFc(double)
	 */
	@Override
	public void setFc(double fc) {
		this.fc1 = fc;
	}


	
public FIRFilterImpl(){
		super();
	}

	public FIRFilterImpl(int shape, int nPoints, int window, double fc) {
		this.shape = shape;
		this.nPoints = nPoints;
		this.window = window;
		this.fc1 = fc;
		initWeights();
	}

	public FIRFilterImpl(int shape, int nPoints, int window, double fc1, double fc2) {
		this.shape = shape;
		this.nPoints = nPoints;
		this.window = window;
		this.fc1 = fc1;
		this.fc2 = fc2;
		initWeights();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#initWeights()
	 */
	@Override
	public void initWeights() {
		switch (shape) {
		case LP:
			weights = Firk.LP(nPoints, window, fc1
					/ (double) Constants.SAMPLE_RATE);
			break;
		case HP:
			weights = Firk.HP(nPoints, window, fc1
					/ (double) Constants.SAMPLE_RATE);
			break;
		case BP:
			weights = Firk.BP(nPoints, window, fc1
					/ (double) Constants.SAMPLE_RATE, fc2
					/ (double) Constants.SAMPLE_RATE);
			break;
		case BS:
			weights = Firk.BS(nPoints, window, fc1
					/ (double) Constants.SAMPLE_RATE, fc2
					/ (double) Constants.SAMPLE_RATE);
			break;
		}
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#filter(java.util.List)
	 */
	@Override
	public List<Double> filter(List<Double> input) {

		// double in[] = input.toArray<Double>();

		List<Double> output = new ArrayList<Double>();
// System.out.println("FIRFilterImpl input.size()="+input.size());
//  System.out.println("weights.length="+weights.length);
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

		FIRFilter filterLP = new FIRFilterImpl(LP, taps, FIRFilter.HAMMING, 1000);
		List<Double> time = filterLP.filter(white);

		long thisTime = System.currentTimeMillis();
		// System.out.println("time: " + (float)(thisTime - startTime)/1000 +
		// " seconds");
		// Plotter.plot(fft.fft(time), "LP 1kHz, Hamming "+taps+" taps");

		startTime = System.currentTimeMillis();
		FIRFilter filterHP = new FIRFilterImpl(HP, taps, FIRFilter.HANNING, 1000);
		time = filterHP.filter(white);
		thisTime = System.currentTimeMillis();
		// System.out.println("time: " + (float)(thisTime - startTime)/1000 +
		// " seconds");
		// Plotter.plot(fft.fft(time), "HP 1kHz Hanning "+taps+" taps");

		startTime = System.currentTimeMillis();
		FIRFilter filterBS = new FIRFilterImpl(BS, taps, FIRFilter.BLACKMAN, 1000, 2000);
		time = filterBS.filter(white);
		thisTime = System.currentTimeMillis();
		// System.out.println("time: " + (float)(thisTime - startTime)/1000 +
		// " seconds");
		// Plotter.plot(fft.fft(time), "BS 1kHz, 2kHz Blackman "+taps+" taps");

		taps = 128;
		startTime = System.currentTimeMillis();
		FIRFilter filterBP = new FIRFilterImpl(BP, taps, FIRFilter.BLACKMAN, 999, 1001);
		// for(int i=0;i<100;i++){
		time = filterBP.filter(white);
		// }
		thisTime = System.currentTimeMillis();
		System.out.println("time: " + (float) (thisTime - startTime) / 1000
				+ " seconds");
		Plotter plotter = new Plotter();
		plotter.setPointSize(8);
		Plotter.plot(4, fft.fft(time), "BP 999Hz, 1001kHz Blackman " + taps
				+ " taps");

		taps = 512;
		startTime = System.currentTimeMillis();
		FIRFilter filterBPtight = new FIRFilterImpl(BP, taps, FIRFilter.BLACKMAN, 999,
				1001);
		// for(int i=0;i<100;i++){
		time = filterBPtight.filter(white);
		// }
		thisTime = System.currentTimeMillis();
		System.out.println("time: " + (float) (thisTime - startTime) / 1000
				+ " seconds");
		Plotter.plot(4, fft.fft(time), "BP fc=999,1001, Hamming " + taps
				+ " taps");
	}

	/**
	 * @return the window
	 */
	public int getWindow() {
		return this.window;
	}

	/**
	 * @param window the window to set
	 */
	public void setWindow(int window) {
		this.window = window;
	}

	/**
	 * @return the fc1
	 */
	public double getFc1() {
		return this.fc1;
	}

	/**
	 * @param fc1 the fc1 to set
	 */
	public void setFc1(double fc1) {
		this.fc1 = fc1;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getShapeName()
	 */
	@Override
	public String getWindowName() {
		return FIRFilter.WINDOW_TYPES[getShape()];
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getShapeName()
	 */
	@Override
	public String getShapeName() {
		return FIRFilter.SHAPES[getShape()];
	}

}
