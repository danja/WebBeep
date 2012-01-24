package org.hyperdata.beeps.system;

import java.util.List;

import org.hyperdata.beeps.util.Tone;
import org.hyperdata.common.describe.Described;
import org.hyperdata.common.describe.Named;


/**
 * 
 */

/**
 * @author danny
 * 
 *
 */
public interface Codec extends Named, Described {
	
	
	/**
	 * @param processor
	 */
	public void addPreProcessor(Processor processor);
	/**
	 * @param processor
	 */
	public void addPostProcessor(Processor processor);

	/**
	 * @param input
	 * @return
	 */
	public Tone applyPreProcessors(Tone input);
	/**
	 * @param input
	 * @return
	 */
	public Tone applyPostProcessors(Tone input);

	/**
	 * @param component
	 */
	void addCoreComponent(Component component);
}
