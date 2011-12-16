/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.decode.Decoder;
import org.hyperdata.beeps.encode.EnvelopeShaper;
import org.hyperdata.beeps.pipelines.Parameterized;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;

/**
 * @author danny
 * 
 *  *         preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 *         
 *         NEED TO CHECK SILENCE
 *
 */
public class ParameterizedDecoder extends Decoder {
	public ParameterSet parameters = new DefaultParameterSet();

	public ParameterizedDecoder() {
		init();
	}

	// private Random random = new Random();

	public void init() {
		initProcessors(); // clears them
		try {
/*
 *     cropper start threshold     0.1...0.9
 *     ChunkDetector snip duration       0.1...0.9
 *     
 *     chunkEnv         on/off
 *     attackProportion       tone length/2...tone length/8
 *     decayProportion  
 *
 *
 *     HP FIR              on/off
 *     HP FIR window       Blackman/Hanning/Hamming
 *     HP FIR Fc           30Hz...250Hz
 *     HP stages           64...4096
 *     LP1 FIR             on/off
 *     LP1 FIR window      Blackman/Hanning/Hamming
 *     LP1 FIR Fc          1kHz...12kHz
 *     LP1 stages          64...4096
 *     LP2 FIR window      Blackman/Hanning/Hamming
 *     LP2 FIR Fc          1kHz...12kHz
 *     LP2 stages          64...4096
 *     FFT bits            8...16
 *     peak detector       0.05...0.9
 */
			Processor chunknorm = new Normalise();
			createParameter(chunknorm, "chunkNorm");
			if (parameters.getValue("chunkNorm").equals("on")) {
				addPreProcessor(chunknorm);
			}
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