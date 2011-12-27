/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class Compressor extends DefaultProcessor {

	private double lowThreshold = 0.2; // anything below that is noise, leave alone
	private double highThreshold = 0.8;	// anything above that is signal, leave alone
	
	private int windowLength = 256;
	/**
	 * @param name
	 */
	public Compressor(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		if(!isEnabled()) return input;
		Rectifier rectifier = new Rectifier("Compressor.rectifier");
		Tone rectified = rectifier.process(input);
		RunningAverage runningAverage = new RunningAverage("Compressor.runningAverage");
		runningAverage.setWindowLength(windowLength );
		Tone envelope = runningAverage.process(rectified);
		
		for(int i=0;i<input.size();i++){
			double envVal = envelope.get(i);
			double val = input.get(i);
			if(envVal > lowThreshold && envVal < highThreshold){
				val = val * 1/envVal; // scale it
				input.set(i,val);
			}
		}
		return input;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		setEnabled((Boolean)  getLocal("on"));
		setLowThreshold((Double)getLocal("lowThreshold"));
		setHighThreshold((Double)getLocal("highThreshold"));
		setWindowLength((Integer)getLocal("windowLength"));
	}

	/**
	 * @return the lowThreshold
	 */
	public double getLowThreshold() {
		return this.lowThreshold;
	}

	/**
	 * @param lowThreshold the lowThreshold to set
	 */
	public void setLowThreshold(double lowThreshold) {
		this.lowThreshold = lowThreshold;
	}

	/**
	 * @return the highThreshold
	 */
	public double getHighThreshold() {
		return this.highThreshold;
	}

	/**
	 * @param highThreshold the highThreshold to set
	 */
	public void setHighThreshold(double highThreshold) {
		this.highThreshold = highThreshold;
	}

	/**
	 * @return the windowLength
	 */
	public int getWindowLength() {
		return this.windowLength;
	}

	/**
	 * @param windowLength the windowLength to set
	 */
	public void setWindowLength(int windowLength) {
		this.windowLength = windowLength;
	}
}
