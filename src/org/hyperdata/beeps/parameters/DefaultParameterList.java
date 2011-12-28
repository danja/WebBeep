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
	private String name;

	public DefaultParameterList(String name) {
this.name = name;
	}
	
	public DefaultParameterList() {

	}
	

	
	public Object getLocal(String key) { // i.e. get("Encoder.HP.window")
		return getValue(getName()+"."+key);
	}

	/**
	 * @param parameters2
	 */
	public DefaultParameterList(ParameterList parameters) {
		for (int i = 0; i < parameters.size(); i++) {
			this.parameters.add(parameters.get(i).clone());
		}
	}

	public int size() {
		return parameters.size();
	}

	public Parameter get(int i) {
		return parameters.get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.optimize.ParameterSet#addParameter(org.hyperdata.
	 * beeps.optimize.Parameter)
	 */
	@Override
	public void add(Parameter param) {
		parameters.add(param);
	}

	public void addAll(ParameterList parameters) {
		for (int i = 0; i < parameters.size(); i++) {
			this.parameters.add(parameters.get(i));
		}
	}

	/**
	 * Adds incoming parameters unless parameter already exists, in which case 
	 * it's given the new value
	 * 
	 * @param incoming
	 */
	public void consume(ParameterList incoming) {
		for (int i = 0; i < incoming.size(); i++) {
			Parameter parameter = incoming.get(i);
			int index = findParameter(parameter.getName());
		//	System.out.println("Incoming parameter = "+parameter);
			if (index != -1) {
				// parameters.set(index, parameter);
				parameters.get(index).setValue(parameter.getValue());
			} else {
				parameters.add(parameter);
			}
		}
	}

	public int findParameter(String parameterName) {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName().equals(parameterName)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * rename?
	 */
	public void setParameter(Parameter parameter){
		int index = findParameter(parameter.getName());
		if(index != -1){
		parameters.get(index).setValue(parameter.getValue());
		return;
		}
		parameters.add(parameter);
	}
	
	/**
	 * rename
	 * @param name
	 * @param value
	 */
	public void setParameter(String name, String value){
		int index = findParameter(name);
		if(index != -1){
			get(index).setValue(value);
			return;
		}
		add(new SimpleParameter(name, value));
		
	}
	
	public Parameter getParameter(String name){
		int index = findParameter(name);
		return parameters.get(index);
	}
	
	
	public void randomizeValues(){
		for(int i=0;i<parameters.size();i++){
			parameters.get(i).initRandom();
		}
	}


	// public void copyParametersToProcessor(Processor processor){
	// Iterator<String> nameIterator = processor.parameterNames().iterator();
	// while(nameIterator.hasNext()){
	// String name = nameIterator.next();
	// try {
	// processor.setParameter(name, getValue(name));
	// } catch (Exception exception) {
	// exception.printStackTrace();
	// }
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.ParameterSet#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName().equals(name))
				return parameters.get(i).getValue();
		}
		System.out.println("*** Dodgy parameters :\n" + this);
//		throw new Exception("Parameter named " + name + " not found.");
		return null;
	}

	public String toString() {
		String string = "*** Parameter Set ***\n";
		for (int i = 0; i < parameters.size(); i++) {
			string += parameters.get(i).toString() + "\n";
		}
		return string;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
