/**
 * 
 */
package org.hyperdata.beeps.optimize;

/**
 * @author danny
 * 
 *         LP FIR Fc 1kHz...12kHz HP FIR Fc 30Hz...250Hz
 */
public class FrequencyParameter extends DefaultParameter {

	static double LP_LOW = 1000;
	static double LP_HIGH = 12000;
//	static double HP_LOW = 30;
//	static double HP_HIGH = 250;
	static double HP_LOW = 1000;
	static double HP_HIGH = 2000;

	public FrequencyParameter(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		if (name.equals("LP_Fc")) {
			value = scaledFreq(LP_LOW, LP_HIGH);
		}
		if (name.equals("HP_Fc")) {
			value = scaledFreq(HP_LOW, HP_HIGH);
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
			System.out.println(new FrequencyParameter("HP_Fc"));
		}
	}
}
