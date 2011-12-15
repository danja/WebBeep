/**
 * 
 */
package org.hyperdata.beeps.optimize;

/**
 * @author danny
 *
 */
public abstract class DefaultParameter implements Parameter {

	
	protected String name = "unnamed";
	protected Object value;

	public DefaultParameter(String name){
		this.name = name;
		initRandom();
	}
	
	public String toString(){
		return name + " = " + value;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name  = name;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

}
