/**
 * 
 */
package org.hyperdata.beeps.parameters;

/**
 * @author danny
 *
 */
public class DefaultNamed implements Named {

	private String name = null;
	
	public DefaultNamed(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.Named#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.Named#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
