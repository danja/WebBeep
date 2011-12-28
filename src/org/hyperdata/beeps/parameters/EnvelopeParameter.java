/**
 * 
 */
package org.hyperdata.beeps.parameters;

import java.util.Random;

import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.util.Plotter;

/**
 * @author danny
 *
 */
public class EnvelopeParameter extends DefaultParameter {
	
	public EnvelopeParameter(){ // necessary for reflection-based instantiation
		
	}
	
	public EnvelopeParameter(ParameterList processor, String name){
		super(processor, name);
		// initRandom();
	}
	


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#mutate()
	 */
	@Override
	public void initRandom() {
		value = Plotter.roundToSignificantFigures(Math.random()/4,2); // 0...0.125
	}
}
