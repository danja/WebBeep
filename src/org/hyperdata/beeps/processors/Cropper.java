/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.List;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.decode.Chunker;
import org.hyperdata.beeps.pipelines.DefaultParameterized;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class Cropper extends DefaultProcessor {

	public Cropper(){
		super("Cropper");
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		return process(input);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
	@Override
	public List<Double> process(List<Double> input) {
		Debug.inform("DETECTOR - do something with me");
		int start = Chunker.findStartThreshold(input,
				Constants.SILENCE_THRESHOLD);
		int end = Chunker.findEndThreshold(input,
				Constants.SILENCE_THRESHOLD);
		// System.out.println("cropping");

		try {
			input = input.subList(start, end);
		} catch (Exception exception) {
			System.out.println("DETECTOR problem");
			// Plotter.plot(tones, "DETECTOR");
		}
		return input;
	}
}
