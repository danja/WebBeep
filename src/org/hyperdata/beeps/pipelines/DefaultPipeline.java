/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */







public class DefaultPipeline extends DefaultProcessor implements Pipeline {

	/**
	 * @param name
	 */
	public DefaultPipeline(String name) {
		super(name);
	}

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
	
	public Tone process(Tone tone) {
		if(processors.size() == 0) return tone;
		Debug.verbose("Applying "+processors.size()+" processors");
		//Tone output = input;
		for(int i=0;i<processors.size();i++){
			Processor processor = processors.get(i);
			Debug.verbose("Applying process : "+processor);
			// System.out.println("Applying process : "+processor);
			tone = processor.process(tone);
		}
		return tone;
	}
	
	public Chunks process(Chunks chunks) {
		if(processors.size() == 0) return chunks;
		Debug.verbose("Applying "+processors.size()+" processors");
		//Tone output = input;
		for(int i=0;i<processors.size();i++){
			Processor processor = processors.get(i);
			Debug.verbose("Applying process : "+processor);
			chunks = processor.process(chunks);
			if(chunks.size() == 0){
				Debug.log("DefaultPipeline****************************** Zero chunks :"+processor);
			}else {
			if(chunks.get(0).size() == 0){
				Debug.log("DefaultPipeline****************************** Zero *in* chunk(0) : "+processor);
			}
			}
		}
		return chunks;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		String string = "";
		if(processors.size() == 0){
			return "\tEmpty Pipeline";
		}
		for(int i=0;i<processors.size();i++){
			string += "\t"+processors.get(i).toString();
		}
		return string;
	}
}
