/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FFTPitchFinder;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterSet;
import org.hyperdata.go.parameters.Parameter;
import org.hyperdata.go.parameters.ParameterSet;
import org.hyperdata.go.parameters.Parameterized;

/**
 * @author danny
 * 
 *         * preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 * 
 *         NEED TO CHECK SILENCE
 * 
 */
public class ParameterizedDecoder extends DefaultCodec {
	public ParameterSet parameters = new DefaultParameterSet();

	public String decode(Tone tones) {
		Debug.inform("Decoding");

		// always normalise
		Normalise norm = new Normalise();
		tones = norm.process(tones);

		// EnvelopeShaper envelope = new EnvelopeShaper();
		// envelope.setAttackProportion(Constants.EDGE_WINDOW_PROPORTION);
		// envelope.setDecayProportion(Constants.EDGE_WINDOW_PROPORTION);
		// tones = envelope.process(tones);

		tones = applyPreProcessors(tones);

		if (Debug.showPlots) {
			Plotter.plot(tones, "in decoder");
		}

		// always crop
		tones = cropper.process(tones);
		// Plotter.plot(tones, "Cropped");

		// always chunk
		Chunks chunks = chunker.process(tones);

		chunks = applyPostProcessors(chunks);

		String ascii = ASCIICodec.chunksToASCII(chunks, pitchFinder);

		try {
			ascii = Checksum.checksum(ascii);
			Debug.debug("ascii=" + ascii);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return IDN.toUnicode(ascii);
	}

	public ParameterizedDecoder() {
		init();
	}

	private Chunker chunker;
	private Cropper cropper;
	private FFTPitchFinder pitchFinder;
	private Processor chunknorm;
	private Processor chunkEnv;
	private Processor hp;
	private Processor lp1;
	private Processor lp2;

	public void init() {
		cropper = new Cropper();
		chunker = new Chunker();
		chunknorm = new Normalise();
		chunkEnv = new EnvelopeShaper();
		
		hp = new FIRProcessor("Decoder HP FIR");
		hp.setParameter("shape", "HP");
		
		lp1 = new FIRProcessor("Decoder LP FIR 1");
		lp1.setParameter("shape", "LP"); 
		
		lp2 = new FIRProcessor("Decoder LP FIR2");
		lp2.setParameter("shape", "LP"); // overwrite - bit clunky
		
		pitchFinder = new FFTPitchFinder();

		initProcessors(); // clears them
		initRandomParameters();
		initFromParameters();
	}

	public void initFromParameters() {
		try {
			// *** Cropper - applied in main path ***
			cropper.initFromParameters();

			// *** Chunker - applied in main path ***
			chunker.initFromParameters();

			// *** chunknorm - normalise individual chunks ***
			if (parameters.getValue("chunkNorm").equals("on")) {
				addPreProcessor(chunknorm);
			}

			chunkEnv.initFromParameters();
			if (parameters.getValue("chunkEnv").equals("on")) {
				addPreProcessor(chunkEnv);
			}

			// *** HP ***
			hp.initFromParameters();
			if (parameters.getValue("HP_FIR").equals("on")) {
				addPostProcessor(hp);
			}
			
			lp1.initFromParameters();
			if (parameters.getValue("LP_FIR1").equals("on")) {
				addPostProcessor(lp1);
			}

			lp2.initFromParameters();
			if (parameters.getValue("LP_FIR2").equals("on")) {
				addPostProcessor(lp2);
			}
			pitchFinder.initFromParameters();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void initRandomParameters() { // rename to encoder.silenceThreshold etc?
		createParameter(cropper, "silenceThreshold");
		createParameter(chunker, "cropProportion");
		createParameter(chunknorm, "chunkNorm");
		createParameter(chunkEnv, "chunkEnv");
		createParameter(chunkEnv, "attackProportion");
		createParameter(chunkEnv, "decayProportion");
		createParameter(hp, "HP_FIR");
		createParameter(hp, "window");
		createParameter(hp, "cutoff");
		createParameter(hp, "npoints");
		createParameter(lp1, "LP_FIR1");
		createParameter(lp1, "window");
		createParameter(lp1, "cutoff");
		createParameter(lp1, "npoints");
		createParameter(lp2, "LP_FIR2");
		createParameter(lp2, "window");
		createParameter(lp2, "cutoff");
		createParameter(lp2, "npoints");
		createParameter(pitchFinder, "fftBits");
		createParameter(pitchFinder, "peakDelta");
		createParameter(pitchFinder, "repeatToFit");
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