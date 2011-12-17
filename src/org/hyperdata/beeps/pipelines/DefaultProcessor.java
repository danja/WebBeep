/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public abstract class DefaultProcessor extends DefaultParameterized implements Processor {

	public DefaultProcessor(String name){
		super(name);
	}
	
	public Chunks processMulti(Chunks input) {
		Chunks output = new Chunks();
		for(int i=0;i<input.size();i++){
			Tone chunk = input.get(i);
			output.add(process(chunk));
		}
		return output;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#processMulti(java.util.List)
	 */
//	public List<List<Double>> processMulti(List<List<Double>> input) {
//		List<List<Double>> output = new ArrayList<List<Double>>();
//		for(int i=0;i<input.size();i++){
//			List<Double> chunk = input.get(i);
//			output.add(process(chunk));
//		}
//		return output;
//	}
}
