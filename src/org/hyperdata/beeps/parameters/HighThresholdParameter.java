/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.system.DefaultParameter;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.Processor;


/**
 * @author danny
 *
 */
public class HighThresholdParameter extends DefaultParameter {

	public HighThresholdParameter(){ // // necessary for reflection-based instantiation
		
	}
	
	/**
	 * @param processor
	 * @param parameterName
	 */
	public HighThresholdParameter(ParameterList processor, String parameterName) {
		super(processor, parameterName);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		// TODO Auto-generated method stub
		ParameterList p = getParameterList();
		Object low = p.getParameter("Decoder.pre.compressor.lowThreshold").getValue();;
		
		
		if(low != null) {
			value = (Double)low + Math.random() * (1-(Double)low);
		}else { // give it a sane fallback, just in case
			value = 0.5 + Math.random()/2;
		}
	}

}
