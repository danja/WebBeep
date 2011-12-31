/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class LFNoisy extends DefaultProcessor {

	/**
	 * @return the noiseProportion
	 */
	public double getNoiseProportion() {
		return this.noiseProportion;
	}

	/**
	 * @param noiseProportion
	 *            the noiseProportion to set
	 */
	public void setNoiseProportion(double noiseProportion) {
		this.noiseProportion = noiseProportion;
	}

	/**
	 * @param name
	 */
	public LFNoisy(String name) {
		super(name);
	}

	double noiseProportion = 0.25;
	private int windowlength = 512;

	/**
	 * adds LF noise
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.system.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) { // inefficient, but ok for testing purpose
		if(!isEnabled()) return input;
		// System.out.println("input size="+input.size());
		Tone noise = makeNoise(input.size());
		// System.out.println("noise size="+noise.size());
		for (int i = 0; i < input.size(); i++) {
			double in = input.get(i);
			double output = in * (1 - noiseProportion) + noise.get(i)
					* noiseProportion;
			input.set(i, output);
		}
		return input;
	}

	public Tone makeNoise(int size) {
		Tone lf = new Tone();

		for (int i = 0; i < size; i++) {
			lf.add(2 * Math.random() - 1);
		}
	//	System.out.println("LF size=" + lf.size());
		RunningAverage ra = new RunningAverage("test");
		ra.setWindowLength(windowlength);
		Tone rumble = ra.process(lf);
		Processor normalise = new Normalise("rumble");
		rumble = normalise.process(rumble);
		// Plotter.plot(rumble, "Rumble");
		// System.out.println("input size="+input.size());
		return rumble;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		setEnabled((Boolean)  getLocal("on"));
		noiseProportion = (Double) getLocal("noiseProportion");
	}

	public static void main(String[] args) {
		String infile = "./data/testin.wav";
		String outfile = "./data/rumbly.wav";
		Tone inTones = WavCodec.readTone(infile);
		LFNoisy noisy = new LFNoisy("test");
		Tone outTones = noisy.process(inTones);
		WavCodec.save(outfile, outTones);
	}

}
