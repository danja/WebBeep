/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.system.DefaultParameter;


/**
 * @author danny
 *
 */
public class SimpleParameter extends DefaultParameter {

	
	public SimpleParameter(String name, Object value){
		super(name,value);
	}
	/**
	 * 
	 */
	public SimpleParameter() { // // necessary for reflection-based instantiation
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		// ignore
		
	}

}
