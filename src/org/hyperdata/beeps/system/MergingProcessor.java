/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.List;

import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public interface MergingProcessor extends ParameterList, Converter {
	public Tone process(Chunks input);
}
