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


	/**
	 * @param type
	 * @param string
	 */
	public BooleanParameter(String type, String string) { // force a value, for testing
		super(type);
		value = string;
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 */
	@Override
	public void initRandom() {
		if(Math.random() > 0.5){
			value = "on";
		} else {
			value = "off";
		}
	}
	
	
}
