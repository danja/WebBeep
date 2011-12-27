/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.Iterator;
import java.util.Random;

import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.parameters.Parameterized;
import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.processors.AllToNoiseProcessor;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Merger;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 *         preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 */
public class ParameterizedEncoder extends DefaultCodec {

	/**
	 * @return the parameters
	 */
	public ParameterList getParameters() {
		return this.parameters;
	}

	public ParameterList parameters = new DefaultParameterList();

	public ParameterizedEncoder(String name) {
		super(name);
		init();
	}

	/**
	 * 
	 * preprocessors in Encoder are applied to individual dual-tone chunks
	 * postprocessors are applied to the whole outgoing constructed waveform
	 */
	public Tone encode(String idn) {
		String ascii = IDN.toASCII(idn); // Punycode encode
		ascii = Checksum.makeChecksumString(ascii) + ascii;
		Chunks chunks = ASCIICodec.asciiToChunks(ascii);
		chunks = applyPreProcessors(chunks);
		Merger merger = new Merger();
		Tone tones = merger.process(chunks);
		tones = applyPostProcessors(tones);
		return tones;
	}

	private Processor chunkEnv;

	private Processor chunkNorm;

	public void init() {
		chunkEnv = new EnvelopeShaper("Encoder.chunkEnv");
		chunkNorm = new Normalise("Encoder.chunkNorm");

		createParameters();
		// initFromParameters();
	}

	public void initFromParameters() {
		// initProcessors(); // clears pre/post lists
		try {
			chunkEnv.initFromParameters();
			addPreProcessor(chunkEnv);
			chunkNorm.initFromParameters();
			addPreProcessor(chunkNorm);
		} catch (Exception exception) {
			System.out.println("---- dodgy parameters:\n" + parameters);
			exception.printStackTrace();
		}
	}

	public void createParameters() {
		createParameter(chunkEnv, "Encoder.chunkEnv.on");
		createParameter(chunkEnv, "Encoder.chunkEnv.attackProportion");
		createParameter(chunkEnv, "Encoder.chunkEnv.decayProportion");
		createParameter(chunkNorm, "Encoder.chunkNorm.on");
	}

	private void createParameter(Parameterized processor, String name) {
		Parameter parameter = ParameterFactory.createParameter(processor, name);
		processor.setParameter(parameter);
		Debug.debug("Created : " + parameter);
		parameters.add(parameter);
	}

	public void setParameters(ParameterList parameters) {
		super.setParameters(parameters);
		this.parameters.consume(parameters);

		System.out.println("PARQMETERS=" + parameters);
	}

	// public String toString() {
	// String string = "Encoder : "+ this.getClass().toString();
	// // if (parameters != null) {
	// // string += "\n" + parameters;
	// // }
	// return string + "\n" + super.toString();
	// }
}