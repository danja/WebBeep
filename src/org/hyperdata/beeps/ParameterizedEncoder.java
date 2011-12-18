/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.Iterator;
import java.util.Random;

import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.parameters.ParameterFactory;
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
import org.hyperdata.go.parameters.DefaultParameterList;
import org.hyperdata.go.parameters.Parameter;
import org.hyperdata.go.parameters.ParameterList;
import org.hyperdata.go.parameters.Parameterized;

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

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(ParameterList parameters) {
		this.parameters = parameters;
		initFromParameters();
	}

	public ParameterList parameters = new DefaultParameterList();

	public ParameterizedEncoder() {
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
	private Processor lp;
	private Processor hp;

	public void init() {
		chunkEnv = new EnvelopeShaper("Encoder.chunkEnv");

		lp = new FIRProcessor("Encoder.LP_FIR");
		lp.setParameter("Encoder.LP_FIR.shape", "LP"); // fixed parameter

		hp = new FIRProcessor("Encoder.HP_FIR");
		hp.setParameter("Encoder.HP_FIR.shape", "HP"); // fixed parameter

		initRandomParameters();
		initFromParameters();
	}

	public void initFromParameters() {
		initProcessors(); // clears pre/post lists
		try {
			chunkEnv.initFromParameters();
			if (parameters.getValue("Encoder.chunkEnv.on").equals("true")) {
				addPreProcessor(chunkEnv);
			}

			lp.initFromParameters();
			if (parameters.getValue("Encoder.LP_FIR.on").equals("true")) {
				addPostProcessor(lp);
			}

			hp.initFromParameters();
			if (parameters.getValue("Encoder.HP_FIR.on").equals("true")) {
				addPostProcessor(hp);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void initRandomParameters() {
		createParameter(chunkEnv, "Encoder.chunkEnv.on");
		createParameter(chunkEnv, "Encoder.chunkEnv.attackProportion");
		createParameter(chunkEnv, "Encoder.chunkEnv.decayProportion");
		createParameter(lp, "Encoder.LP_FIR.on");
		createParameter(lp, "Encoder.LP_FIR.window");
		createParameter(lp, "Encoder.LP_FIR.cutoff");
		createParameter(lp, "Encoder.LP_FIR.npoints");
		createParameter(hp, "Encoder.HP_FIR.on");
		createParameter(hp, "Encoder.HP_FIR.window");
		createParameter(hp, "Encoder.HP_FIR.cutoff");
		createParameter(hp, "Encoder.HP_FIR.npoints");
	}

	private void createParameter(Parameterized processor, String name) {
		Parameter parameter = ParameterFactory.createParameter(processor, name);
		processor.setParameter(parameter);
		Debug.debug("Created : " + parameter);
		parameters.add(parameter);
	}

	public String toString() {
		String string = this.getClass().toString();
		if (parameters != null) {
			string += "\n" + parameters;
		}
		return string;
	}
}