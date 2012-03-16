/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.describe.DefaultDescriber;
import org.hyperdata.common.describe.Describer;

/**
 * @author danny
 * 
 */
public abstract class DefaultParameter extends DefaultNamed implements
		Parameter {

	protected Object value;
	private ParameterList parameterList;

	// private String datatype = "";

	public DefaultParameter() {
	}

	public DefaultParameter(String name) {
		super(name);
	}

	public DefaultParameter(ParameterList processor, String name) {
		super(name);
		setParameterList(processor);
	}

	public DefaultParameter(ParameterList processor, String name, Object value) {
		this(processor, name);
		setValue(value);
	}

	public DefaultParameter(String name, Object value) {
		this(null, name, value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param processor
	 *            the processor to set
	 */
	public void setParameterList(ParameterList processor) {
		this.parameterList = processor;
	}

	public Parameter clone() {
		Parameter clone = null;
		try {
			clone = this.getClass().newInstance();
			clone.setParameterList(parameterList);
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
	public ParameterList getParameterList() {
		return this.parameterList;
	}

	public String toString() {
		return getName() + " = " + value;
	}
	
	/**
	 * TODO needs making more systematic
	 */
	public String getURI(){
		return "http://hyperdata.org/beeps/HACK/"+getName();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.Parameter#getValue()
	 */
	@Override
	public void setValue(Object value) {
		this.value = value;
	}
	

	public String describe() {
		String description = "<"+getURI()+">";
		description += " "+DefaultDescriber.getJavaDatatypeAsProperty(value)+" \""+value+"\" .\n";
		return description;
	}
}
