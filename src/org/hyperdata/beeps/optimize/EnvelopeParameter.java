/**
 * 
 */
package org.hyperdata.beeps.optimize;

import java.util.Random;

/**
 * @author danny
 *
 */
public class EnvelopeParameter extends DefaultParameter {
	
	public EnvelopeParameter(String name){
		super(name);
		initRandom();
	}
	


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 */
	@Override
	public void initRandom() {
		value = Math.random()/2; // 0...0.5
	}
}
