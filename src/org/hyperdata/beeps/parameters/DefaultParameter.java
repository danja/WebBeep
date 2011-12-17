/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.pipelines.Parameterized;

/**
 * @author danny
 *
 */
public abstract class DefaultParameter implements Parameter {

	
	/**
	 * @return the processor
	 */
	public Parameterized getProcessor() {
		return this.processor;
	}

	protected String name = "unnamed";
	protected Object value;
	private Parameterized processor;

	public DefaultParameter(Parameterized processor, String name){
		this.processor = processor;
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
