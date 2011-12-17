/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.pipelines.DefaultParameterized;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public class Rectifier extends DefaultProcessor {

	public Rectifier(){
		super("Rectifier");
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// no parameters
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		return process(input);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
	@Override
	public List<Double> process(List<Double> input) {
		List<Double> rectified = new ArrayList<Double>();

		for (int i = 0; i < input.size(); i++) {
			rectified.add(Math.abs(input.get(i)));
		}
		return rectified;
	}

}
