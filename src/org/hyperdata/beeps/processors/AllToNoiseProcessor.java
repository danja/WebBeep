/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.List;

import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Noise;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class AllToNoiseProcessor extends DefaultProcessor {
	
	public AllToNoiseProcessor(String name){
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		setEnabled((Boolean)getLocal("on"));
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
//	@Override
//	public List<Double> process(List<Double> input) {
//		
//		return Noise.white(1.0);
//	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		return Noise.whiteTone(1.0);
	}
}
