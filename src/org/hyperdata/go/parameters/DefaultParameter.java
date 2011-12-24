/**
 * 
 */
package org.hyperdata.go.parameters;


/**
 * @author danny
 *
 */
public abstract class DefaultParameter implements Parameter {

	public DefaultParameter(){
	}
	
	public DefaultParameter(Parameterized processor, String name){
		this.processor = processor;
		this.name = name;
		initRandom();
	}
	
	public DefaultParameter(Parameterized processor, String name, Object value){
		this.processor = processor;
		this.name = name;
		this.value = value;
	}
	
	public DefaultParameter(String name, Object value){
		this(null, name, value);
	}
	
	/**
	 * @param processor the processor to set
	 */
	public void setProcessor(Parameterized processor) {
		this.processor = processor;
	}

	protected String name = "unnamed";
	protected Object value;
	private Parameterized processor;
	
	
	public Parameter clone(){
		Parameter clone = null;
		try {
			clone = this.getClass().newInstance();
			clone.setProcessor(processor);
			clone.setName(name);
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
	public Parameterized getProcessor() {
		return this.processor;
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
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#getValue()
	 */
	@Override
	public void setValue(Object value) {
		this.value = value;
	}


}
