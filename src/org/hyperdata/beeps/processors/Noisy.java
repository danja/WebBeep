/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class Noisy extends DefaultProcessor {

	double noiseProportion = 0.25;
	
	/**
	 * @return the noiseProportion
	 */
	public double getNoiseProportion() {
		return this.noiseProportion;
	}

	/**
	 * @param noiseProportion the noiseProportion to set
	 */
	public void setNoiseProportion(double noiseProportion) {
		this.noiseProportion = noiseProportion;
	}

	/**
	 * @param name
	 */
	public Noisy(String name) {
		super(name);
	}

	
	/**
	 * adds noise
	 * 
	 *  (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		if(!isEnabled()) return input;
		for(int i=0;i<input.size();i++){
			double in = input.get(i);
			double output = in * (1 - noiseProportion) + Math.random() * noiseProportion;
			input.set(i, output);
		}
		return input;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		setEnabled((Boolean)  getLocal("on"));
		noiseProportion = (Double) getLocal("noiseProportion");
	}
	
	public static void main(String[] args){
		String infile = "./data/testin.wav";
		String outfile = "./data/noisy.wav";
		Tone inTones = WavCodec.readTone(infile);
		Noisy noisy = new Noisy("test");
		Tone outTones = noisy.process(inTones);
		WavCodec.save(outfile, outTones);
	}

}
