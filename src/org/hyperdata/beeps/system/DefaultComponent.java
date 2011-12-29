/**
 * 
 */
package org.hyperdata.beeps.system;


/**
 * @author danny
 *
 */
public class DefaultComponent extends DefaultParameterList implements Component {

	/**
	 * @param name
	 */
	public DefaultComponent(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}

//	private ParameterList parameters = new DefaultParameterList();
//	
//	public void initFromParameters() {
//		
//		for(int i=0;i<size();i++){
//			get(i).initFromParameters();
//		}
//	}

}
