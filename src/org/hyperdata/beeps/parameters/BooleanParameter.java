/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.go.parameters.DefaultParameter;
import org.hyperdata.go.parameters.Parameterized;

/**
 * @author danny
 *
 */
public class BooleanParameter extends DefaultParameter {
	
	public BooleanParameter(){
		
	}
	
	public BooleanParameter(Parameterized processor, String name){
		super(processor, name);
	}


	/**
	 * @param type
	 * @param string
	 */
//	public BooleanParameter(Processor processor, String type, String string) { // force a value, for testing
//		super(processor, type);
//		value = string;
//	}


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 */
	@Override
	public void initRandom() {
		if(Math.random() > 0.5){
			value = "true";
		} else {
			value = "false";
		}
	}
	
	
}
