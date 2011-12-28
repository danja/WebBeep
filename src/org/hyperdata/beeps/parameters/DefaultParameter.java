/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.pipelines.Processor;



/**
 * @author danny
 *
 */
public abstract class DefaultParameter extends DefaultNamed implements Parameter {

	protected Object value;
	private ParameterList processor;
	// private String datatype = "";
	
	public DefaultParameter(){
	}
	
	public DefaultParameter(String name){
		super(name);
	}
	
	public DefaultParameter(ParameterList processor, String name){
		super(name);
		setProcessor(processor);
	}
	
	public DefaultParameter(ParameterList processor, String name, Object value){
		this(processor, name);
		setValue(value);
	}
	
	public DefaultParameter(String name, Object value){
		this(null, name, value);
	}
	
	/**
	 * @param processor the processor to set
	 */
	public void setProcessor(ParameterList processor) {
		this.processor = processor;
	}
	
	public Parameter clone(){
		Parameter clone = null;
		try {
			clone = this.getClass().newInstance();
			clone.setProcessor(processor);
			clone.setName(getName());
			clone.setValue(value);
		} catch (InstantiationException exception) {
			exception.printStackTrace();
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
		return clone;
		// Parameter clone = new DefaultParameter(this.processor, this.name);
	}
	/**
	 * @return the processor
	 */
	public ParameterList getProcessor() {
		return this.processor;
	}
	
	public String toString(){
		return getName() + " = " + value;
	}
	

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#getValue()
	 */
	@Override
	public void setValue(Object value) {
		this.value = value;
	}


}
