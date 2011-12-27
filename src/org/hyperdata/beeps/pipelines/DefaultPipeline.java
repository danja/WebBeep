/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterList;
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
	Map<String, Processor> processorNames = new HashMap<String, Processor>(); // yuck...

	public int size() {
		return processors.size();
	}

	// public Processor get(int i){
	// return processors.get(i);
	// }

	public void addProcessor(Processor processor) {
		processors.add(processor);
		;
		processorNames.put(processor.getName(), processor);
	}

	public Processor getProcessor(String name) {
		return processorNames.get(name);
	}

	public Tone process(Tone tone) {
		if (processors.size() == 0)
			return tone;
		Debug.verbose("Applying " + processors.size() + " processors");
		// Tone output = input;
		for (int i = 0; i < processors.size(); i++) {
			Processor processor = processors.get(i);
			Debug.verbose("Applying process : " + processor);
			// System.out.println("Applying process : "+processor);
			tone = processor.process(tone);
		}
		return tone;
	}

	public Chunks process(Chunks chunks) {
		if (processors.size() == 0)
			return chunks;
		Debug.verbose("Applying " + processors.size() + " processors");
		// Tone output = input;
		for (int i = 0; i < processors.size(); i++) {
			Processor processor = processors.get(i);
			Debug.verbose("Applying process : " + processor);
			chunks = processor.process(chunks);
			if (chunks.size() == 0) {
				Debug.log("DefaultPipeline****************************** Zero chunks :"
						+ processor);
			} else {
				if (chunks.get(0).size() == 0) {
					Debug.log("DefaultPipeline****************************** Zero *in* chunk(0) : "
							+ processor);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */

	public void initFromParameters() {
		// TODO Auto-generated method stub

	}

	public String toString() {
		String string = getName() + "\n";
		if (processors.size() == 0) {
			return "\tEmpty Pipeline";
		}
		for (int i = 0; i < processors.size(); i++) {
			string += "\t" + processors.get(i).toString();
		}
		return string;
	}
	
	public ParameterList getParameters(){
		ParameterList parameters =new DefaultParameterList();
		for(int i=0;i<processors.size();i++){
			parameters.addAll(processors.get(i)); // .getParameters()
		}
		return parameters;
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
	@Override
	public void setParameters(ParameterList parameters) {
		// System.out.println("THIS=" + this);
		if (processors.size() == 0)
			return;

		for (int i = 0; i < parameters.size(); i++) {
			String name = parameters.get(i).getName();
			// System.out.println("PARAMETERNAME=" + name);
			String[] split = name.split("\\.");
			// System.out.println("PIPENAME=" + getName());
			Processor processor = getProcessor(split[0] + "." + split[1]);

			// System.out.println("Processor=" + processor);
			Parameter parameter = parameters.get(i);
			if (split[0].equals(getName()) && getProcessor(split[1]) != null) { // Decoder.LP_FIR1.window
				parameter.setProcessor(processor);
				processor.setParameter(parameter);
				// System.out.println("Processor=" + processor);
			}
		}
	}
}
