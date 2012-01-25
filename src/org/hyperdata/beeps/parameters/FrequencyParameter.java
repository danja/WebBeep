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
 *         should refactor, replace this with an ExponentialParameter suitable configured
 */
public class FrequencyParameter extends DefaultParameter {

	public FrequencyParameter(){ // // necessary for reflection-based instantiation
		
	}
	
	public FrequencyParameter(ParameterList processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		// System.out.println(getProcessor());
		String shape = null;
		try {
			shape = (String)getParameterList().getLocal("shape");
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	
		Debug.debug("Initializing a "+shape);
		if (shape.equals("LP")) {
			value = scaledFreq(Constants.LP_LOW, Constants.LP_HIGH);
		}
		if (shape.equals("HP")) {
			value = scaledFreq(Constants.HP_LOW, Constants.HP_HIGH);
		}
	}
	
	public static double scaledFreq(double low, double high){
		double top = Math.log(high);
		double bottom = Math.log(low);
		double diff = top - bottom;
		double r = Math.random() * diff;
		double exponent = bottom + r;
		return Math.exp(exponent);
	}

	public static void main(String args[]) {
		for (int i = 0; i < 10; i++) {
		//	System.out.println(new FrequencyParameter("HP_Fc"));
		}
	}
}
