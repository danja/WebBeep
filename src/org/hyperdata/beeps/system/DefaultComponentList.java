/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperdata.common.describe.DefaultDescriber;
import org.hyperdata.common.describe.Described;
import org.hyperdata.common.describe.Describer;


/**
 * @author danny
 *
 */
public class DefaultComponentList extends DefaultNamed implements ComponentList {

	
 private List<Component> components = new ArrayList<Component>();
	
 public int size(){
	 return components.size();
 }
	/**
	 * @param name
	 */
	public DefaultComponentList(String name) {
		super(name);
	}
	
	public Component getComponent(String name) {
			for(int i=0;i<components.size();i++){
				if(components.get(i).getName().equals(name)){
					return components.get(i);
				}
			}
			return null;
		}
	
	public Component getComponent(int i) {
		 return components.get(i);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.ComponentList#addComponent(org.hyperdata.beeps.parameters.Parameterized)
	 */
	@Override
	public void addComponent(Component component) {
		components.add(component);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		for(int i=0;i<components.size();i++){
			components.get(i).initFromParameters();
		}
	}
	
	public void updateParameters(ParameterList parameters) {
		for(int i=0;i<components.size();i++){
			
		//	System.out.println("updating in DefPip"+components.get(i));
			components.get(i).update(parameters);
		}
	}
	
	public ParameterList getParameters(){
		ParameterList parameters =new DefaultParameterList(getName());
		for(int i=0;i<components.size();i++){
		//	System.out.println("ADDING "+components.get(i));
			parameters.addAll(components.get(i).getParameters()); // .getParameters()
		}
		return parameters;
	}
	
	public String toString() {
		String string = getName() + "\n";
		if (components.size() == 0) {
			return "\tEmpty Pipeline";
		}
		for (int i = 0; i < components.size(); i++) {
			string += "\t" + components.get(i).toString();
		}
		return string;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Component#update(org.hyperdata.beeps.system.ParameterList)
	 */
	public void update(ParameterList parameters) {
		for(int i=0;i<components.size();i++){
			components.get(i).getParameters().update(parameters); // EVILPRAMS
		}
		
	}
	
	/**
	 * TODO needs making more systematic
	 */
	public String getURI(){
		return "http://hyperdata.org/beeps/HACK/"+getName();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		String description = DefaultDescriber.getDescription(this);
		description += "<"+getURI()+"> a beep:ComponentList .";
		
		if (components.size() == 0) {
			return "# Empty Pipeline";
		}
		// description += "# COMPONENTLIST\n";
		
		description +="<"+getURI()+"> beep:components ( \n";
		for (int i = 0; i < components.size(); i++) {
			description += "<"+components.get(i).getURI()+"> \n";
		}
		description += ") . \n";
		for (int i = 0; i < components.size(); i++) {
			description += components.get(i).describe();
		}
		return description;
	}
}
