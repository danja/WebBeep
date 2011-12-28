/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.parameters.ComponentList;
import org.hyperdata.beeps.parameters.DefaultComponentList;
import org.hyperdata.beeps.parameters.DefaultNamed;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.pipelines.Codec;
import org.hyperdata.beeps.pipelines.DefaultComponent;
import org.hyperdata.beeps.pipelines.DefaultPipeline;
import org.hyperdata.beeps.pipelines.Pipeline;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public abstract class DefaultCodec extends DefaultNamed implements Codec {

	//private ParameterList parameters;
	
	private ComponentList coreComponents;
	
	private Pipeline preProcessors;
	private Pipeline postProcessors;
	private String name = null;

	public DefaultCodec(String name) {
		super(name);
		// = new DefaultParameterList("DefaultCodec");
		coreComponents = new DefaultComponentList(name+".core");
		
		preProcessors = new DefaultPipeline(name+".pre");
		postProcessors = new DefaultPipeline(name+".post");
	}
	
	protected void createParameter(ParameterList component, String name) {
		Parameter parameter = ParameterFactory.createParameter(component, name);
		component.setParameter(parameter);
//		System.out.println("PARAMETER="+parameter);
//System.out.println("COMPONENT="+component);
	//	parameters.add(parameter);
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(ParameterList parameters) {
	//	System.out.println("PRAMS"+parameters);
		// TODO make these consistent consume/update
		coreComponents.consume(parameters);
		preProcessors.updateParameters(parameters);
		postProcessors.updateParameters(parameters);
	//	System.out.println("PREEEEEEEEEEEEEEEEEE"+preProcessors);
	}
	
	public void initFromParameters() {
		coreComponents.initFromParameters();
		preProcessors.initFromParameters();
		postProcessors.initFromParameters();
	}

	public ParameterList getParameters() {
		ParameterList parameters = new DefaultParameterList("All");
		parameters.addAll(coreComponents);
		parameters.addAll(preProcessors.getParameters());
		parameters.addAll(postProcessors.getParameters());
		return parameters;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.Codec#addPreProcessor(org.hyperdata.beeps.Processor)
	 */
	@Override
	public void addCoreComponent(ParameterList component) {
		coreComponents.consume(component);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.Codec#addPreProcessor(org.hyperdata.beeps.Processor)
	 */
	@Override
	public void addPreProcessor(Processor processor) {
		Debug.verbose("Adding Processor : " + processor);
		preProcessors.addProcessor(processor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.Codec#addPostProcessor(org.hyperdata.beeps.Processor)
	 */
	@Override
	public void addPostProcessor(Processor processor) {
		postProcessors.addProcessor(processor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.Codec#applyPreProcessors(java.util.List)
	 */
	// @Override
	// public List<Double> applyPreProcessors(List<Double> input) {
	// if(preprocessors.size() == 0) return input;
	// Debug.verbose("Preprocessing...");
	// return preprocessors.applyProcessors(input);
	// }

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.Codec#applyPostProcessors(java.util.List)
	 */
	// @Override
	// public List<Double> applyPostProcessors(List<Double> input) {
	// if(postprocessors.size() == 0) return input;
	// Debug.verbose("Postprocessing...");
	// return postprocessors.applyProcessors(input);
	// }


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Codec#applyPreProcessors(org.hyperdata.
	 * beeps.util.Tone)
	 */
	@Override
	public Tone applyPreProcessors(Tone input) {
		if (preProcessors.size() == 0)
			return input;
		Debug.verbose("Preprocessing...");
		return preProcessors.process(input);
	}

	public Chunks applyPreProcessors(Chunks chunks) {
		if (preProcessors.size() == 0)
			return chunks;
		return preProcessors.process(chunks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Codec#applyPostProcessors(org.hyperdata
	 * .beeps.util.Tone)
	 */
	@Override
	public Tone applyPostProcessors(Tone input) {
		if (postProcessors.size() == 0)
			return input;
		Debug.verbose("Postprocessing...");
		return postProcessors.process(input);
	}

	public Chunks applyPostProcessors(Chunks chunks) {
		if (postProcessors.size() == 0)
			return chunks;
		return postProcessors.process(chunks);
	}

	public String toString() {
		String string = "Codec : " + this.getClass().toString();
		string += "\nPreProcessors:\n" + preProcessors.toString();
		string += "\nPostProcessors:\n" + postProcessors.toString();
		return string;
	}

}
