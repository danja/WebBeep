/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.pipelines.Processor;


/**
 * @author danny
 *
 * 0 <= value < 1
 * 
 */
public class NormalParameter extends DefaultParameter {

//	public NormalParameter(){
//		
//	}
	/**
	 * @param processor
	 * @param name
	 */
	public NormalParameter(ParameterList processor, String name) {
		super(processor, name);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		value = Math.random();
	}

}
