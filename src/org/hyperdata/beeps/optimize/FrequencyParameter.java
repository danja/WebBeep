/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.pipelines.Parameterized;

/**
 * @author danny
 * 
 *         FIR Filters, ranges are in Constants
 */
public class FrequencyParameter extends DefaultParameter {

	public FrequencyParameter(Parameterized processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		String shape = (String)getProcessor().getParameter("shape");
		Debug.debug("Initializing a "+shape);
		if (shape.equals("LP")) {
			value = scaledFreq(Constants.LP_LOW, Constants.LP_HIGH);
		}
		if (shape.equals("HP")) {
			value = scaledFreq(Constants.HP_LOW, Constants.HP_HIGH);
		}
	}
	
	public static int scaledFreq(double low, double high){
		double top = Math.log(high);
		double bottom = Math.log(low);
		double diff = top - bottom;
		double r = Math.random() * diff;
		double exponent = bottom + r;
		return (int)Math.exp(exponent);
	}

	public static void main(String args[]) {
		for (int i = 0; i < 10; i++) {
		//	System.out.println(new FrequencyParameter("HP_Fc"));
		}
	}
}
