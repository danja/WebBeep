/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;


/**
 * @author danny
 *
 */
public class DefaultCodec implements Codec {

	private Pipeline preprocessors = new DefaultPipeline();
	private Pipeline postprocessors = new DefaultPipeline();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Codec#addPreProcessor(org.hyperdata.beeps.Processor)
	 */
	@Override
	public void addPreProcessor(Processor processor) {
		preprocessors.addProcessor(processor);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Codec#addPostProcessor(org.hyperdata.beeps.Processor)
	 */
	@Override
	public void addPostProcessor(Processor processor) {
		postprocessors.addProcessor(processor);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Codec#applyPreProcessors(java.util.List)
	 */
	@Override
	public List<Double> applyPreProcessors(List<Double> input) {
		return preprocessors.applyProcessors(input);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Codec#applyPostProcessors(java.util.List)
	 */
	@Override
	public List<Double> applyPostProcessors(List<Double> input) {
		return postprocessors.applyProcessors(input);
	}
}
