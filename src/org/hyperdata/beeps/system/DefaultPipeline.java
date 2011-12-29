/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public class DefaultPipeline extends DefaultComponentList implements Pipeline, Processor { // extends DefaultProcessor 

	/**
	 * @param name
	 */
	public DefaultPipeline(String name) {
		super(name);
	}

//	List<Processor> processors = new ArrayList<Processor>();
//	Map<String, Processor> processorNames = new HashMap<String, Processor>(); // yuck...
	private boolean enabled = true;


	// public Processor get(int i){
	// return processors.get(i);
	// }

	public void addProcessor(Processor processor) {
		super.addComponent(processor);;
//		processors.add(processor);
//		processorNames.put(processor.getName(), processor);
	}
	
	

	public Processor getProcessor(String name) {
		// return processorNames.get(name);
		return (Processor)super.getComponent(name);
	}

	public Tone process(Tone tone) {
		if(!enabled) return tone;
		if (size() == 0)
			return tone;
		Debug.verbose("Applying " + size() + " processors");
		// Tone output = input;
		for (int i = 0; i < size(); i++) {
			Processor processor = (Processor)getComponent(i);
			//Debug.verbose("Applying process : " + processor);
			// System.out.println("Applying process : "+processor);
			tone = processor.process(tone);
		}
		return tone;
	}

	public Chunks process(Chunks chunks) {
		if(!enabled) return chunks;
		if (size() == 0)
			return chunks;
		Debug.verbose("Applying " + size() + " processors");
		// Tone output = input;
		for (int i = 0; i < size(); i++) {
			//Processor processor = processors.get(i);
			Processor processor = (Processor)getComponent(i);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */

//	public void initFromParameters() {
//		
////		for(int i=0;i<processors.size();i++){
////			processors.get(i).initFromParameters();
////		}
//	}


	


//	/* (non-Javadoc)
//	 * @see org.hyperdata.beeps.parameters.ComponentList#addComponent(org.hyperdata.beeps.parameters.Component)
//	 */
//	@Override
//	public void addComponent(Component component) {
//		// TODO Auto-generated method stub
//		
//	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
