/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public abstract class DefaultCodec implements Codec {

	private Pipeline preprocessors;
	private Pipeline postprocessors;
	
	public DefaultCodec(){
		 initProcessors();
	}
	
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
		Debug.verbose("Adding Processor : "+processor);
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
//	@Override
//	public List<Double> applyPreProcessors(List<Double> input) {
//		if(preprocessors.size() == 0) return input;
//		Debug.verbose("Preprocessing...");
//		return preprocessors.applyProcessors(input);
//	}
	
	public void checkType(List list){
		System.out.println(list.getClass());
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Codec#applyPostProcessors(java.util.List)
	 */
//	@Override
//	public List<Double> applyPostProcessors(List<Double> input) {
//		if(postprocessors.size() == 0) return input;
//		Debug.verbose("Postprocessing...");
//		return postprocessors.applyProcessors(input);
//	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Codec#initProcessors()
	 */
	@Override
	public void initProcessors() {
		preprocessors = new DefaultPipeline();
		postprocessors = new DefaultPipeline();
		Debug.debug("Processes cleared in "+this);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Codec#applyPreProcessors(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone applyPreProcessors(Tone input) {
		if(preprocessors.size() == 0) return input;
		Debug.verbose("Preprocessing...");
		return preprocessors.applyProcessors(input);
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Codec#applyPostProcessors(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone applyPostProcessors(Tone input) {
		if(postprocessors.size() == 0) return input;
		Debug.verbose("Postprocessing...");
		return postprocessors.applyProcessors(input);
	}
}
