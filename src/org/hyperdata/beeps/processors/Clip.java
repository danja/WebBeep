/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class Clip extends DefaultProcessor {

	private double clipLevel = 0.5;
	
	/**
	 * @return the clipLevel
	 */
	public double getClipLevel() {
		return this.clipLevel;
	}

	/**
	 * @param clipLevel the clipLevel to set
	 */
	public void setClipLevel(double clipLevel) {
		this.clipLevel = clipLevel;
	}



	/**
	 * @param name
	 */
	public Clip(String name) {
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
			double out = input.get(i);
			if(Math.abs(out) > clipLevel ){
				out = clipLevel * Math.signum(input.get(i));
			}
			input.set(i, out);
		}
		Processor normalise = new Normalise("Clipnorm");
		input = normalise.process(input);
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
		String outfile = "/home/danny/workspace/WebBeep/data/clippy.wav";
		Tone inTones = WavCodec.readTone(infile);
		Clip distort  = new Clip("test");
		distort.setClipLevel(0.6);
		Tone outTones = distort.process(inTones);
		WavCodec.save(outfile, outTones);
	}
	
}
