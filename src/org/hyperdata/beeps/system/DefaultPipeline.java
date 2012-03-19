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
import org.hyperdata.common.describe.DefaultDescriber;

/**
 * @author danny
 * 
 */
public class DefaultPipeline extends DefaultComponentList implements Pipeline { 

	private boolean enabled = true;
	
	/**
	 * @param name
	 */
	public DefaultPipeline(String name) {
		super(name);
	}


	public void addProcessor(Processor processor) {
		super.addComponent(processor);;
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

		for (int i = 0; i < size(); i++) {
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

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		String description = super.describe();
		description += DefaultDescriber.getTypedDescription(this, "proc:Pipeline");
	//	description += "<"+getURI()+"> a beep:Pipeline .";
	//	description += "# PIPELINE\n";
	//	description += super.describe();
		return description;
	}





	

}
