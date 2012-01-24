/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.system.*;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.common.describe.Describer;

/**
 * @author danny
 * 
 */
public abstract class DefaultCodec extends DefaultNamed implements Codec {
	
	private ComponentList coreComponents;
	private Pipeline preProcessors;
	private Pipeline postProcessors;

	public DefaultCodec(String name) {
		super(name);
		coreComponents = new DefaultComponentList(name+".core");
		preProcessors = new DefaultPipeline(name+".pre");
		postProcessors = new DefaultPipeline(name+".post");
	}
	
	protected void createParameter(Component component, String name) {
		Parameter parameter = ParameterFactory.createParameter(component.getParameters(), name);
		component.setParameter(parameter);
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(ParameterList parameters) {
	//	System.out.println("PRAMS"+parameters);
		// TODO make these consistent consume/update
		coreComponents.updateParameters(parameters);
		preProcessors.updateParameters(parameters); // was update
		postProcessors.updateParameters(parameters);
	}
	
	public void initFromParameters() {
		coreComponents.initFromParameters();
		preProcessors.initFromParameters();
		postProcessors.initFromParameters();
	}
	
	public void initRandom(){
		ParameterList all = getParameters();
		for(int i=0;i<all.size();i++){
			all.get(i).initRandom();
		}
	}

	public ParameterList getParameters() {
		ParameterList parameters = new DefaultParameterList("All");
		parameters.addAll(coreComponents.getParameters());
		// System.out.println("PRE:\n"+preProcessors);
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
	public void addCoreComponent(Component component) {
	//	coreComponents.updateParameters(component); // UPDATE
		coreComponents.addComponent(component);
		// consume(component);
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
		string += coreComponents.toString();
		string += preProcessors.toString();
		string += postProcessors.toString();
		return string;
	}

	public String describe(){
		String description = Describer.getDescription(this);
		description += coreComponents.describe();
		description += preProcessors.describe();
		description += postProcessors.describe();
		return description;
	}
}
