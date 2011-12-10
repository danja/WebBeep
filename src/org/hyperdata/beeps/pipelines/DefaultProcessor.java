/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author danny
 *
 */
public class DefaultProcessor implements Processor {

	private String name = "anonymous";
	private Map parameters = new HashMap();
	
	public DefaultProcessor(String name){
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
	@Override
	public List<Double> process(List<Double> input) {
		return input;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setParameter(String name, Object value) {
		parameters.put(name, value);
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



	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#processMulti(java.util.List)
	 */
	@Override
	public List<List<Double>> processMulti(List<List<Double>> input) {
		List<List<Double>> output = new ArrayList<List<Double>>();
		for(int i=0;i<input.size();i++){
			List<Double> chunk = input.get(i);
			output.add(process(chunk));
		}
		return output;
	}

}
