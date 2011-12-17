/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public class DefaultPipeline implements Pipeline {

	List<Processor> processors = new ArrayList<Processor>();

	public int size(){
		return processors.size();
	}
	
	public void addProcessor(Processor processor) {
		processors.add(processor);
	}

//	public List<Double> applyProcessors(List<Double> input) {
//		if(processors.size() == 0) return input;
//		Debug.verbose("Applying "+processors.size()+" processors");
//		List<Double> output = input;
//		for(int i=0;i<processors.size();i++){
//			Processor processor = processors.get(i);
//			Debug.verbose("Applying process : "+processor);
//			output = processor.process(output);
//		}
//		return output;
//	}
	
	public Tone applyProcessors(Tone tone) {
		if(processors.size() == 0) return tone;
		Debug.verbose("Applying "+processors.size()+" processors");
		//Tone output = input;
		for(int i=0;i<processors.size();i++){
			Processor processor = processors.get(i);
			Debug.verbose("Applying process : "+processor);
			tone = processor.process(tone);
		}
		return tone;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
