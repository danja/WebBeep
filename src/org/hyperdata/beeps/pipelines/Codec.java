package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.parameters.Named;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.util.Tone;


/**
 * 
 */

/**
 * @author danny
 * 
 *
 */
public interface Codec extends Named {
	
	public void addCoreComponent(ParameterList component);
	public void addPreProcessor(Processor processor);
	public void addPostProcessor(Processor processor);

	public Tone applyPreProcessors(Tone input);
	public Tone applyPostProcessors(Tone input);
}
