/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hyperdata.beeps.parameters.SimpleParameter;

/**
 * @author danny
 * 
 */
public class DefaultParameterList extends DefaultNamed implements ParameterList {

	private List<Parameter> parameters = new ArrayList<Parameter>();

	public DefaultParameterList(String name) {
		super(name);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.optimize.ParameterSet#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) throws NotFoundException {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName().equals(name))
				return parameters.get(i).getValue();
		}
		throw new NotFoundException("Parameter named " + name + " not found in "+this);
	}

	public Object getLocal(String localName) { // i.e. get("Encoder.pre.HP.window")
				try {
					return getValue(getName() + "." + localName);
				} catch (NotFoundException exception) {
					exception.printStackTrace();
				}
				return null;
	}

	/**
	 * @param parameters2
	 */
	public DefaultParameterList(ParameterList parameters) {
		this(parameters.getName());
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
	public void update(ParameterList incoming) {
//		System.out.println("EXISTING:\n"+parameters);
//		System.out.println("INCOMING:\n"+incoming);
		
		for (int i = 0; i < incoming.size(); i++) {
			Parameter parameter = incoming.get(i);
			if(parameter.getName().startsWith(getName())){
				
			int index = findParameter(parameter.getName());
//			System.out.println("Incoming parameter = \n"+parameter);
//			System.out.println(index);
			if (index != -1) {
				// parameters.set(index, parameter);
				parameters.get(index).setValue(parameter.getValue());
			} else {
			//	System.out.println("^^^^^^^^^^^^^^^^adding "+parameter);
				parameters.add(parameter);
			}
			}
		}
	//	System.out.println("AFTER CONSUME:\n"+parameters);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Pipeline#setParameters(org.hyperdata.beeps
	 * .parameters.ParameterList)
	 * 
	 * this is horrible!
	 */

//	public void updateParameters(ParameterList parameters) {
//
//		for (int i = 0; i < parameters.size(); i++) {
//			String name = parameters.get(i).getName();

//			String[] split = name.split("\\.");
//			// System.out.println("PIPENAME=" + getName());
//			Processor processor = getProcessor(split[0] + "." + split[1]);
//
//			// System.out.println("Processor=" + processor);
//			Parameter parameter = parameters.get(i);
//			if (split[0].equals(getName()) && getProcessor(split[1]) != null) { // Decoder.LP_FIR1.window
//				parameter.setProcessor(processor);
//				processor.setParameter(parameter);
//				// System.out.println("Processor=" + processor);
//			}
//		}
//	}

	public int findParameter(String parameterName) {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).getName().equals(parameterName)) {
				// System.out.println(parameterName+" == "+parameters.get(i).getName());
				return i;
			}
		}
		return -1;
	}

	/**
	 * rename?
	 */
	public void setParameter(Parameter parameter) {
	//	System.out.println("FIND "+parameter);
		int index = findParameter(parameter.getName());
		if (index != -1) {
		//	System.out.println("SETTING "+parameters.get(index));
			parameters.get(index).setValue(parameter.getValue());
		//	System.out.println("SET "+parameters.get(index));
			return;
		}
		parameters.add(parameter);
	//	System.out.println("PQ "+parameters);
	}

	/**
	 * rename
	 * 
	 * @param name
	 * @param value
	 */
	public void setParameter(String name, String value) {
		int index = findParameter(name);
		if (index != -1) {
			get(index).setValue(value);
			return;
		}
		add(new SimpleParameter(name, value));

	}

	public Parameter getParameter(String name) {
		int index = findParameter(name);
		return parameters.get(index);
	}

	public void randomizeValues() {
		for (int i = 0; i < parameters.size(); i++) {
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



	public String toString() {
		String string = ""; // *** Parameter Set ***\n
		for (int i = 0; i < parameters.size(); i++) {
			string += "\t\t"+parameters.get(i).toString() + "\n";
		}
		return string;
	}
}
