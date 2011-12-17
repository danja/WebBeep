/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.Iterator;
import java.util.Random;

import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.parameters.DefaultParameterSet;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.parameters.ParameterSet;
import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Parameterized;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.processors.AllToNoiseProcessor;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Merger;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.optimize.*;

/**
 * @author danny
 * 
 *         preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 * 
 *         gen Fs 44, 22, 11, 6 kHz chunknorm on/off chunkEnv on/off
 *         attackProportion tone length/2...tone length/8 decayProportion "
 *         LP_FIR on/off LP_window Blackman/Hanning/Hamming LP_Fc 1kHz...12kHz
 *         LP_points 64...4096 HP_FIR on/off HP_window Blackman/Hanning/Hamming
 *         HP_Fc 30Hz...250Hz HP_points 64...4096
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

	public void init() {
		initProcessors(); // clears them
		try {
//			Processor chunknorm = new Normalise();
//			createParameter(chunknorm, "chunkNorm");
//			if (parameters.getValue("chunkNorm").equals("on")) {
//				addPreProcessor(chunknorm);
//			}
			Processor chunkEnv = new EnvelopeShaper();
			createParameter(chunkEnv, "chunkEnv");
			createParameter(chunkEnv, "attackProportion");
			createParameter(chunkEnv, "decayProportion");
			chunkEnv.initFromParameters();
			if (parameters.getValue("chunkEnv").equals("on")) {
				addPreProcessor(chunkEnv);
			}
//			Processor toNoise = new AllToNoiseProcessor();
//			createParameter(toNoise, "toNoise"); // dummy test
//			toNoise.initFromParameters();
//			if (parameters.getValue("toNoise").equals("on")) { // test dummy
//				addPreProcessor(toNoise);
//			}
			Processor lp = new FIRProcessor("LP_FIR");
			lp.setParameter("shape", "LP"); // overwrite - bit clunky
			createParameter(lp, "LP_FIR");
			createParameter(lp, "window");
			createParameter(lp, "cutoff");
			createParameter(lp, "npoints");
			
			lp.initFromParameters();
			if (parameters.getValue("LP_FIR").equals("on")) {
				addPostProcessor(lp);
			}
			Processor hp = new FIRProcessor("HP_FIR");
			hp.setParameter("shape", "HP"); // overwrite - bit clunky
			// parameters.copyParametersToProcessor(hp);
			createParameter(hp, "HP_FIR");
			createParameter(hp, "window");
			createParameter(hp, "cutoff");
			createParameter(hp, "npoints");
			
			hp.initFromParameters();
			if (parameters.getValue("HP_FIR").equals("on")) {
				addPostProcessor(hp);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
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