/**
 * 
 */
package org.hyperdata.beeps.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hyperdata.beeps.pipelines.Processor;

/**
 * @author danny
 *
 */
public class DefaultParameterList implements ParameterList {

	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	public DefaultParameterList() {
		
	}
	/**
	 * @param parameters2
	 */
	public DefaultParameterList(ParameterList parameters) {
		for(int i=0;i<parameters.size();i++){
			this.parameters.add(parameters.get(i).clone());
		}
	}

	public int size(){
		return parameters.size();
	}
	
	public Parameter get(int i){
		return parameters.get(i);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.ParameterSet#addParameter(org.hyperdata.beeps.optimize.Parameter)
	 */
	@Override
	public void add(Parameter param) {
		parameters.add(param);
	}
	
	public void addAll(ParameterList parameters){
		for(int i=0;i<parameters.size();i++){
			this.parameters.add(parameters.get(i));
		}
	}
	
//	public void copyParametersToProcessor(Processor processor){
//		Iterator<String> nameIterator = processor.parameterNames().iterator();
//		while(nameIterator.hasNext()){
//			String name = nameIterator.next();
//			try {
//				processor.setParameter(name, getValue(name));
//			} catch (Exception exception) {
//				exception.printStackTrace();
//			}
//		}
//	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.ParameterSet#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) throws Exception {
		for(int i=0;i<parameters.size();i++){
			if(parameters.get(i).getName().equals(name)) return parameters.get(i).getValue();
		}
		throw new Exception("Parameter named "+name+" not found.");
	}
	
	public String toString(){
		String string = "*** Parameter Set ***\n";
		for(int i=0;i<parameters.size();i++){
			string += parameters.get(i).toString()+"\n";
		}
		return string;
	}
}
