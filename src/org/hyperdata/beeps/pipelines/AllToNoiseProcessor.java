/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.util.Noise;

/**
 * @author danny
 *
 */
public class AllToNoiseProcessor extends DefaultProcessor {
	
	public AllToNoiseProcessor(){
		super("All to Noise");
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
	@Override
	public List<Double> process(List<Double> input) {
		
		return Noise.white(1.0);
	}

}
