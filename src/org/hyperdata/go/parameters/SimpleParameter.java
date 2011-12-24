/**
 * 
 */
package org.hyperdata.go.parameters;

/**
 * @author danny
 *
 */
public class SimpleParameter extends DefaultParameter {

	
	public SimpleParameter(String name, Object value){
		super(name,value);
	}
	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		// ignore
		
	}

}
