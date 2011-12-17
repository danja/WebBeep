/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.parameters.DefaultParameterSet;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.parameters.ParameterSet;
import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Parameterized;
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

	Chunker chunker;
	Cropper cropper;

	private FFTPitchFinder pitchFinder;
	
	public String decode(Tone tones) {
		Debug.inform("Decoding");
		
		// always normalise
		Normalise norm = new Normalise();
		tones = norm.process(tones);
		
//		EnvelopeShaper envelope = new EnvelopeShaper();
//		envelope.setAttackProportion(Constants.EDGE_WINDOW_PROPORTION);
//		envelope.setDecayProportion(Constants.EDGE_WINDOW_PROPORTION);
//		tones = envelope.process(tones);
		
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

	public void init() {
		initProcessors(); // clears them
		try {
			/*
			 * cropper start threshold 0.1...0.9
			 * 
			 * ChunkDetector snip duration 0.1...0.9
			 * 
			 * chunknorm on/off
			 * 
			 * chunkEnv on/off attackProportion tone length/2...tone length/8
			 * decayProportion
			 * 
			 * 
			 * HP FIR on/off HP FIR window Blackman/Hanning/Hamming HP FIR Fc
			 * 30Hz...250Hz HP stages 64...4096 LP1 FIR on/off LP1 FIR window
			 * Blackman/Hanning/Hamming LP1 FIR Fc 1kHz...12kHz LP1 stages
			 * 64...4096 LP2 FIR window Blackman/Hanning/Hamming LP2 FIR Fc
			 * 1kHz...12kHz LP2 stages 64...4096 FFT bits 8...16 peak detector
			 * 0.05...0.9
			 */

			// *** Cropper - applied in main path ***
			cropper = new Cropper();
			createParameter(cropper, "silenceThreshold");
			cropper.initFromParameters();

			// *** Chunker - applied in main path ***
			chunker = new Chunker();
			createParameter(chunker, "cropProportion");
			chunker.initFromParameters();
			
			// *** chunknorm - normalise individual chunks ***
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

			// *** HP ***
			Processor hp = new FIRProcessor("Decoder HP FIR");
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
			
			Processor lp1 = new FIRProcessor("Decoder LP FIR 1");
			lp1.setParameter("shape", "LP"); // overwrite - bit clunky
			createParameter(lp1, "LP_FIR1");
			createParameter(lp1, "window");
			createParameter(lp1, "cutoff");
			createParameter(lp1, "npoints");
			lp1.initFromParameters();
			
			if (parameters.getValue("LP_FIR1").equals("on")) {
				addPostProcessor(lp1);
			}
			
			Processor lp2 = new FIRProcessor("Decoder LP FIR2");
			lp2.setParameter("shape", "LP"); // overwrite - bit clunky
			createParameter(lp2, "LP_FIR2");
			createParameter(lp2, "window");
			createParameter(lp2, "cutoff");
			createParameter(lp2, "npoints");
			lp2.initFromParameters();
			
			if (parameters.getValue("LP_FIR2").equals("on")) {
				addPostProcessor(lp2);
			}
			pitchFinder = new FFTPitchFinder();
			createParameter(pitchFinder, "fftBits");
			createParameter(pitchFinder, "peakDelta");
			createParameter(pitchFinder, "repeatToFit");
			pitchFinder.initFromParameters();
			
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