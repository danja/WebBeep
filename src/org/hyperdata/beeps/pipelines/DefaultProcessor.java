/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danny
 *
 */
public abstract class DefaultProcessor extends DefaultParameterized implements Processor {

	public DefaultProcessor(String name){
		super(name);
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
