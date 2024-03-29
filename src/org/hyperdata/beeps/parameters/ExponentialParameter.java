/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.system.DefaultParameter;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.Processor;

/**
 * @author danny
 * 
 *         FIR Filters, ranges are in Constants
 * 
 *         should refactor, replace this with an ExponentialParameter suitable
 *         configured
 */
public class ExponentialParameter extends DefaultParameter {

	private double low = 1;
	private double high = 100;

	public ExponentialParameter() { // necessary for reflection-based instantiation

	}

	public ExponentialParameter(ParameterList processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		value = scaledRandom(low, high);
		
	//	value = 10000;
	}

	public void setRange(double low, double high) {
		this.low = low;
		this.high = high;
		initRandom();
	}

	public static double scaledRandom(double low, double high) {
		double top = Math.log(high);
		double bottom = Math.log(low);
		double diff = top - bottom;
		double r = Math.random() * diff;
		double exponent = bottom + r;
		return (double) Math.exp(exponent);
	}

	public static void main(String args[]) {
		for (int i = 0; i < 100; i++) {
			ExponentialParameter ep = new ExponentialParameter(null, "g");
			ep.setRange(100, 100000);
			
			System.out.println(ep.getValue());
		}
	}
}
