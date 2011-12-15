/**
 * 
 */
package org.hyperdata.beeps.optimize;

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
public class DefaultParameterSet implements ParameterSet {

	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.ParameterSet#addParameter(org.hyperdata.beeps.optimize.Parameter)
	 */
	@Override
	public void addParameter(Parameter param) {
		parameters.add(param);
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
			string += parameters.get(i).toString() + "\n";
		}
		return string;
	}
}
