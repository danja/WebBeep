/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.system.DefaultParameter;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.Processor;


/**
 * @author danny
 *
 */
public class BooleanParameter extends DefaultParameter {
	
	public BooleanParameter(){// necessary for reflection-based instantiation
		
	}
	
	public BooleanParameter(ParameterList processor, String name){
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
			value = true;
		} else {
			value = false;
		}
	}
	
	
}
