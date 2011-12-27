/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public abstract class DefaultCodec implements Codec {

	private Pipeline preProcessors;
	private Pipeline postProcessors;
	private String name = null;
	
	public DefaultCodec(String name){
		this.name  = name;
		 // initProcessors();
	}
	
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(ParameterList parameters) {
		initProcessors();
preProcessors.setParameters(parameters);
postProcessors.setParameters(parameters);
	//	initFromParameters();
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
		preProcessors.addProcessor(processor);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Codec#addPostProcessor(org.hyperdata.beeps.Processor)
	 */
	@Override
	public void addPostProcessor(Processor processor) {
		postProcessors.addProcessor(processor);
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
	//	System.out.println("MYNAMEHERE="+getName());
		preProcessors = new DefaultPipeline(getName());
		postProcessors = new DefaultPipeline(getName());
		Debug.debug("Processes cleared in "+this);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Codec#applyPreProcessors(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone applyPreProcessors(Tone input) {
		if(preProcessors.size() == 0) return input;
		Debug.verbose("Preprocessing...");
		return preProcessors.process(input);
	}
	
	public Chunks applyPreProcessors(Chunks chunks){
		if(preProcessors.size() == 0) return chunks;
		return preProcessors.process(chunks);
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Codec#applyPostProcessors(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone applyPostProcessors(Tone input) {
		if(postProcessors.size() == 0) return input;
		Debug.verbose("Postprocessing...");
		return postProcessors.process(input);
	}
	
	public Chunks applyPostProcessors(Chunks chunks){
		if(postProcessors.size() == 0) return chunks;
		return postProcessors.process(chunks);
	}
	
	public String toString(){
		String string = "Codec : "+this.getClass().toString();
		string += "\nPreProcessors:\n" + preProcessors.toString();
		string += "\nPostProcessors:\n" + postProcessors.toString();
		return string;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
