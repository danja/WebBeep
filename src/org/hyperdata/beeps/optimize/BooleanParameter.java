/**
 * 
 */
package org.hyperdata.beeps.optimize;

/**
 * @author danny
 *
 */
public class BooleanParameter extends DefaultParameter {
	

	
	public BooleanParameter(String name){
		super(name);
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 */
	@Override
	public void initRandom() {
		value = new Boolean(Math.random() > 0.5);
	}
}
