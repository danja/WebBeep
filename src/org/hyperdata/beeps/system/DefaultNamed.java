/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.describe.Named;


/**
 * @author danny
 *
 */
public class DefaultNamed implements Named {

	private String name = null;
	private String uri = null;
	
	public DefaultNamed(){ // necessary for reflection-based instantiation
	}
	
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

	/* (non-Javadoc)
	 * @see org.hyperdata.common.describe.Named#setURI(java.lang.String)
	 */
	@Override
	public void setURI(String uri) {
		this.uri = uri;
		
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.common.describe.Named#getURI()
	 */
	@Override
	public String getURI() {
		return this.uri;
	}

}
