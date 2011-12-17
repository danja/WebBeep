/**
 * 
 */
package org.hyperdata.go.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author danny
 *
 */
public abstract class DefaultParameterized implements Parameterized {

	/**
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return this.parameters;
	}


	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}



	private String name = "anonymous";
	protected Map<String, Object> parameters = new HashMap<String, Object>();
	

	public DefaultParameterized(){
	}
	
	
	public DefaultParameterized(String name){
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* 
	 * Default process is to pass through unchanged
	 * 
	 * (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
//	@Override
//	public List<Double> process(List<Double> input) {
//		return input;
//	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setParameter(String name, Object value) {
		parameters.put(name, value);
	}
	
	@Override
	public void setParameter(Parameter parameter) {
		parameters.put(parameter.getName(), parameter.getValue());
	}
	
	public Set<String> parameterNames(){
		return parameters.keySet();
	}
	
	public String toString(){
		String string = name;
		Iterator<String> iterator = parameters.keySet().iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			string += "\n" + name + " = " + parameters.get(name);
		}
		return string;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}



	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#getParameter(java.lang.String)
	 */
	@Override
	public Object getParameter(String name) {
		return parameters.get(name);
	}





}
