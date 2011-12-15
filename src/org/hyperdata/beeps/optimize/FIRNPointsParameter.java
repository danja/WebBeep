/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.pipelines.Processor;

/**
 * @author danny 64...4096
 */
public class FIRNPointsParameter extends DefaultParameter {

	public FIRNPointsParameter(Processor processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 * 
	 * 64...4096
	 * 
	 * 2^6 = 64 2^12 = 4096
	 */
	@Override
	public void initRandom() {
		double exponent = Math.random() * 6 + 6;
		value = (int)Math.pow(2, exponent);
	}

	public static void main(String[] args) {
		System.out.println(Math.pow(2, 0 + 6));
		System.out.println(Math.pow(2, 0.99 * 6+ 6));
		for (int i = 0; i < 10; i++) {
		//	FIRNPointsParameter p = new FIRNPointsParameter("test");
		//	System.out.println(p);
		}
	}
}
