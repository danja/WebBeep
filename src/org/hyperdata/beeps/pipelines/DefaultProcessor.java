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
public abstract class DefaultProcessor extends DefaultParameterized implements Processor {

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
}
