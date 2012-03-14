/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.beeps.parameters.SimpleParameter;
import org.hyperdata.common.describe.DefaultDescriber;
import org.hyperdata.common.describe.Describer;


/**
 * @author danny
 *
 */
public abstract class DefaultComponent extends DefaultNamed implements Component {

	ParameterList parameters;
	/**
	 * @param name
	 */
	public DefaultComponent(String name) {
		super(name);
	//	System.out.println("constructing "+name);
		parameters = new DefaultParameterList(name);
	}
	
	public void setParameter(String name, String valueString){
		Parameter parameter = parameters.getParameter(name);
		if(parameter == null){
			parameter = new SimpleParameter();
			parameter.setName(name);
			parameters.add(parameter);
		}
		parameter.setValue(valueString);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Processor#setParameter(org.hyperdata.beeps.system.Parameter)
	 */
	@Override
	public void setParameter(Parameter parameter) {
		parameters.setParameter(parameter);
		
	}
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Processor#setParameter(java.lang.String, java.lang.String)
	 */
//	@Override
//	public void setParameter(String string, String string2) {
//		parameters.se
//	}
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Component#update(org.hyperdata.beeps.system.ParameterList)
	 */
	@Override
	public void update(ParameterList incoming) {
		parameters.update(incoming);
	}
	
	public Object getLocal(String localName){
		try {
			return parameters.getLocal(localName);
		} catch (NotFoundException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Component#getParameters()
	 */
	@Override
	public ParameterList getParameters() {
		return parameters;
	}

	public String toString(){
		String string = "Component : "+getName()+" = "+this.getClass().toString()+"\n";
		string +=  parameters.toString();
		return string;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		String description = DefaultDescriber.getDescription(this);
		description += parameters.describe();
		return description;
	}

}
