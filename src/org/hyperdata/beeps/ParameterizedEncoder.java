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
import org.hyperdata.go.parameters.DefaultParameterSet;
import org.hyperdata.go.parameters.Parameter;
import org.hyperdata.go.parameters.ParameterSet;
import org.hyperdata.go.parameters.Parameterized;

/**
 * @author danny
 * 
 *         preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 */
public class ParameterizedEncoder extends DefaultCodec {

	public ParameterSet parameters = new DefaultParameterSet();

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
		chunkEnv = new EnvelopeShaper();

		lp = new FIRProcessor("Encoder LP FIR");
		lp.setParameter("shape", "LP");

		hp = new FIRProcessor("Encoder HP FIR");
		hp.setParameter("shape", "HP");

		initRandomParameters();
		initProcessors(); // clears pre/post
		initFromParameters();
	}

	public void initFromParameters() {
		try {
			chunkEnv.initFromParameters();
			if (parameters.getValue("chunkEnv").equals("on")) {
				addPreProcessor(chunkEnv);
			}

			lp.initFromParameters();
			if (parameters.getValue("LP_FIR").equals("on")) {
				addPostProcessor(lp);
			}

			hp.initFromParameters();
			if (parameters.getValue("HP_FIR").equals("on")) {
				addPostProcessor(hp);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void initRandomParameters() {
		createParameter(chunkEnv, "chunkEnv");
		createParameter(chunkEnv, "attackProportion");
		createParameter(chunkEnv, "decayProportion");
		createParameter(lp, "LP_FIR");
		createParameter(lp, "window");
		createParameter(lp, "cutoff");
		createParameter(lp, "npoints");
		createParameter(hp, "HP_FIR");
		createParameter(hp, "window");
		createParameter(hp, "cutoff");
		createParameter(hp, "npoints");
	}

	private void createParameter(Parameterized processor, String name) {
		Parameter parameter = ParameterFactory.createParameter(processor, name);
		processor.setParameter(parameter);
		Debug.debug("Created : " + parameter);
		parameters.addParameter(parameter);
	}

	public String toString() {
		String string = this.getClass().toString();
		if (parameters != null) {
			string += "\n" + parameters;
		}
		return string;
	}
}