/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.pipelines.Parameterized;

/**
 * @author danny
 * 
 */
public class FFTBitsParameter extends DefaultParameter {

	/**
	 * @param processor
	 * @param name
	 */
	public FFTBitsParameter(Parameterized processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		value = (int) (5 + 10.0 * Math.random());
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			FFTBitsParameter p = new FFTBitsParameter(null, "test");
			System.out.println(p.getValue());
		}
	}
}
