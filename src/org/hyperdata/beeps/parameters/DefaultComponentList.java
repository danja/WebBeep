/**
 * 
 */
package org.hyperdata.beeps.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danny
 *
 */
public class DefaultComponentList extends DefaultParameterList implements ComponentList {

	private List<Component> components = new ArrayList<Component>();
	
	/**
	 * @param name
	 */
	public DefaultComponentList(String name) {
		super(name);
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
}
