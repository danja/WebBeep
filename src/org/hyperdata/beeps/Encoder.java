/**
 * 
 */
package org.hyperdata.beeps;

import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public interface Encoder {

	/**
	 * 
	 * preprocessors in Encoder are applied to individual dual-tone chunks
	 * postprocessors are applied to the whole outgoing constructed waveform
	 */
	public abstract Tone encode(String idn);

}