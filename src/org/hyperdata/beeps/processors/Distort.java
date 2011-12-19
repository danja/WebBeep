/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class Distort extends DefaultProcessor {
	
	/**
	 * @return the distortProportion
	 */
	public double getDistortProportion() {
		return this.distortProportion;
	}

	/**
	 * @param distortProportion the distortProportion to set
	 */
	public void setDistortProportion(double distortProportion) {
		this.distortProportion = distortProportion;
	}

	private double distortProportion = 0.5;

	/**
	 * @param name
	 */
	public Distort(String name) {
		super(name);
	}

	/**
	 * non-linear distortion (valve-like?)
	 * 
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		for(int i=0;i<input.size();i++){
			double in = input.get(i);
			double output = (1 - distortProportion) * in + distortProportion * Math.signum(in) * Math.exp(1.5 * Math.log(Math.abs(in)));
			input.set(i, output);
		}
		return input;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args){
		String infile = "/home/danny/workspace/WebBeep/data/testin.wav";
		String outfile = "/home/danny/workspace/WebBeep/data/distorty.wav";
		Tone inTones = WavCodec.readTone(infile);
		Distort distort  = new Distort("test");
		Tone outTones = distort.process(inTones);
		WavCodec.save(outfile, outTones);
	}
	
}
