/**
 * 
 */
package org.hyperdata.beeps;

import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public interface Decoder {

	public abstract String decode(Tone tones);
	public String chunksToASCII(Chunks chunks);
}