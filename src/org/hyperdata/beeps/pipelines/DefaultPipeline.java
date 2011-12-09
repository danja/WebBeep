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
public class DefaultPipeline implements Pipeline {

	List<Processor> processors = new ArrayList<Processor>();

	public void addProcessor(Processor processor) {
		processors.add(processor);
	}

	public List<Double> applyProcessors(List<Double> input) {
		List<Double> output = input;
		for(int i=0;i<processors.size();i++){
			output = processors.get(i).process(output);
		}
		return output;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
