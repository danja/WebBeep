/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.WaveMaker;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.pipelines.MergingProcessor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class Merger extends DefaultParameterList implements MergingProcessor {

	public Merger(){
		
	}
	
	/**
	 * @param name
	 */
	public Merger(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */

	public void initFromParameters() {
		// no parameters (yet)
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.MergingProcessor#process(org.hyperdata.beeps.util.Chunks)
	 */
	@Override
	public Tone process(Chunks input) {
		Tone output = new Tone();
		output.addAll(WaveMaker.makeSilence(Constants.START_PAD_DURATION));
		for (int i = 0; i < input.size(); i++) {
			output.addAll(input.get(i));
			output.addAll(WaveMaker.makeSilence(Constants.SILENCE_DURATION));
		}
		output.addAll(WaveMaker.makeSilence(Constants.END_PAD_DURATION));
		return output;
	}
}
