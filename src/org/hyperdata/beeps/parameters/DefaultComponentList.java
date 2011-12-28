/**
 * 
 */
package org.hyperdata.beeps.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperdata.beeps.pipelines.Processor;

/**
 * @author danny
 *
 */
public class DefaultComponentList extends DefaultParameterList implements ComponentList {

	 private List<Component> components = new ArrayList<Component>();
//	List<Processor> components = new ArrayList<Processor>();
	Map<String, Component> componentNames = new HashMap<String, Component>(); // yuck...
	
	/**
	 * @param name
	 */
	public DefaultComponentList(String name) {
		super(name);
	}
	
	public Component getComponent(String name) {
		 return componentNames.get(name);
	}
	
	public Component getComponent(int i) {
		 return components.get(i);
	}
	
	public int size() {
		return components.size();
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.ComponentList#addComponent(org.hyperdata.beeps.parameters.Parameterized)
	 */
	@Override
	public void addComponent(Component component) {
		components.add(component);
		componentNames.put(component.getName(), component);
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
			System.out.println("updating in DefPip");
			components.get(i).consume(parameters);
		}
	}
	
	public ParameterList getParameters(){
		ParameterList parameters =new DefaultParameterList(getName());
		for(int i=0;i<size();i++){
			// System.out.println("ADDING "+processors.get(i));
			parameters.addAll(components.get(i)); // .getParameters()
		}
		return parameters;
	}
	
	public String toString() {
		String string = getName() + "\n";
		if (size() == 0) {
			return "\tEmpty Pipeline";
		}
		for (int i = 0; i < size(); i++) {
			string += "\t" + components.get(i).toString();
		}
		return string;
	}
}
