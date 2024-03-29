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
public class RunningAverageWindowLengthParameter extends DefaultParameter {

	public RunningAverageWindowLengthParameter(){ // necessary for reflection-based instantiation
		
	}
	
	public RunningAverageWindowLengthParameter(ParameterList processor, String name) {
		super(processor, name);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		value = (int)(1024 * Math.random());
	}

}
