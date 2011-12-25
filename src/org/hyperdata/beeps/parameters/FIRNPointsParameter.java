/**
 * 
 */
package org.hyperdata.beeps.parameters;


/**
 * @author danny 64...4096
 */
public class FIRNPointsParameter extends DefaultParameter {

	public FIRNPointsParameter(){
		
	}
	
	public FIRNPointsParameter(Parameterized processor, String name) {
		super(processor, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 * 
	 * 64...4096
	 * 
	 * 2^7 = 128, 2^13 = 8192
	 */
	@Override
	public void initRandom() {
		double exponent = Math.random() * 8 + 5;
		value = (int)Math.pow(2, exponent);
	}

	public static void main(String[] args) {
		System.out.println(Math.pow(2, 0 + 6));
		System.out.println(Math.pow(2, 0.99 * 6+ 6));
		for (int i = 0; i < 10; i++) {
		FIRNPointsParameter p = new FIRNPointsParameter(null,"test");
		System.out.println(p.getValue());
		}
	}
}
