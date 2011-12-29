/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.system.DefaultParameter;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.Processor;


/**
 * @author danny
 * 
 */
public class FFTBitsParameter extends DefaultParameter {

	public FFTBitsParameter(){ // necessary for reflection-based instantiation
		
	}
	/**
	 * @param processor
	 * @param name
	 */
	public FFTBitsParameter(ParameterList processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		value = (int) (7 + 6.0 * Math.random()); // (int) (8 + 6.0 * Math.random());
	}
	
	/* sensible
	 * fftBits = 10
     * fftMax = 1024
     * peakDelta = 0.5
	 */

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			FFTBitsParameter p = new FFTBitsParameter(null, "test");
			System.out.println(p.getValue());
		}
	}
}
