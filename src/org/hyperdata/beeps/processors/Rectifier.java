/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterized;

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
		Tone rectified = new Tone();

		for (int i = 0; i < input.size(); i++) {
			rectified.add(Math.abs(input.get(i)));
		}
		return rectified;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
//	@Override
//	public List<Double> process(List<Double> input) {
//		List<Double> rectified = new ArrayList<Double>();
//
//		for (int i = 0; i < input.size(); i++) {
//			rectified.add(Math.abs(input.get(i)));
//		}
//		return rectified;
//	}

}
