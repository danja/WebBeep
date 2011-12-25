/**
 * 
 */
package org.hyperdata.beeps.parameters;


/**
 * @author danny
 *
 */
public class RunningAverageWindowLengthParameter extends DefaultParameter {

	public RunningAverageWindowLengthParameter(){
		
	}
	
	public RunningAverageWindowLengthParameter(Parameterized processor, String name) {
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
