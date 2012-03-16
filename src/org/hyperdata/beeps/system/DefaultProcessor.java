/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.common.describe.DefaultDescriber;

/**
 * @author danny
 *
 */
public abstract class DefaultProcessor extends DefaultComponent implements Processor {

	private boolean enabled = true;
	
	public DefaultProcessor(String name){
		super(name);
	}
	
	public Chunks process(Chunks input) {
		Chunks output = new Chunks();
		for(int i=0;i<input.size();i++){
			Tone chunk = input.get(i);
			Tone processed = process(chunk);
//			if(processed.size() == 0){
//				Debug.log("[DefaultProcessor] zero output from processor "+getName());
//				Debug.log("parameters:\n"+getParameters());
//				Debug.log("input.size() "+input.size());
//			}
			output.add(processed);
		}
		return output;
	}
	


	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		String description = DefaultDescriber.getDescription(this);
		description += "<"+getURI()+"> a beep:Processor .";
		description += super.describe();
		return description;
	}
}
