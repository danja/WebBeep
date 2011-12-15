/**
 * 
 */
package org.hyperdata.beeps.optimize;

import java.util.Iterator;
import java.util.Random;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.encode.Encoder;
import org.hyperdata.beeps.encode.EnvelopeShaper;
import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.pipelines.AllToNoiseProcessor;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.process.FIRProcessor;
import org.hyperdata.beeps.process.Normalise;
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
public class ParameterizedEncoder extends Encoder {

	public ParameterSet parameters = new DefaultParameterSet();

	public ParameterizedEncoder() {
		// createRandomParameters();
		init();
	}

	// private Random random = new Random();

	public void init() {
		initProcessors(); // clears them
		try {

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

	private void createParameter(Processor processor, String name) {
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