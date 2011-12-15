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
	
	public ParameterizedEncoder(){
		createRandomParameters();
		init();
	}

	// private Random random = new Random();

	public void init() {
		initProcessors(); // clears them
		try {
			if (parameters.getValue("chunkNorm").equals("on")) {
				Processor chunknorm = new Normalise();
				addPreProcessor(chunknorm);
			}
			if (parameters.getValue("chunkEnv").equals("on")) {
				Processor chunkEnv = new EnvelopeShaper();
				parameters.copyParametersToProcessor(chunkEnv);
				chunkEnv.initFromParameters();
				addPreProcessor(chunkEnv);
			}
			if (parameters.getValue("toNoise").equals("on")) { // test dummy
				Processor toNoise = new AllToNoiseProcessor();
				parameters.copyParametersToProcessor(toNoise);
				toNoise.initFromParameters();
				addPreProcessor(toNoise);
			}
			
			if (parameters.getValue("LP_FIR").equals("on")) {
				Processor lp = new FIRProcessor("LP_FIR");
				parameters.copyParametersToProcessor(lp);
				lp.initFromParameters();
				lp.setParameter("shape", "LP"); // overwrite - bit clunky
				addPostProcessor(lp);
			}
			if (parameters.getValue("HP_FIR").equals("on")) {
				Processor hp = new FIRProcessor("HP_FIR");
				parameters.copyParametersToProcessor(hp);
				hp.initFromParameters();
				hp.setParameter("shape", "HP"); // overwrite - bit clunky
				addPostProcessor(hp);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void p(String name) {
		Parameter parameter = ParameterFactory.createParameter(name);
		Debug.debug("Created : "+parameter);
		parameters.addParameter(parameter);
	}

	private void createRandomParameters() {
		// * gen Fs 44, 22, 11, 6 kHz
		p("chunkNorm");
		p("chunkEnv");
		p("attackProportion");
		p("decayProportion");
		p("toNoise"); // dummy test
		p("LP_FIR");
		p("LP_window");
		p("LP_Fc");
		p("LP_points");
		p("HP_FIR");
		p("HP_window");
		p("HP_Fc");
		p("HP_points");
	}
	
	public String toString(){
		return parameters.toString();
	}
}