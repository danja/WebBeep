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
import org.hyperdata.beeps.processors.Compressor;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FFTPitchFinder;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterList;
import org.hyperdata.go.parameters.Parameter;
import org.hyperdata.go.parameters.ParameterList;
import org.hyperdata.go.parameters.Parameterized;

/**
 * @author danny
 * 
 * 
 *         NEED TO CHECK SILENCE
 * 
 */
public class ParameterizedDecoder extends DefaultCodec {
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

	public String decode(Tone tones) {
		Debug.inform("Decoding");

		// always normalise
		Normalise norm = new Normalise("Decoder.input.normalise");
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
	
	private Processor norm1;
	private Processor norm2;
	private Processor norm3;
	private Processor norm4;
	
	private Compressor compressor;

	public void init() {
		cropper = new Cropper("Decoder.cropper");
		compressor = new Compressor("Decoder.compressor");
		
		hp = new FIRProcessor("Decoder.HP_FIR");
		hp.setParameter("Decoder.HP_FIR.shape", "HP");  // fixed parameter
		
		lp1 = new FIRProcessor("Decoder.LP_FIR1");
		lp1.setParameter("Decoder.LP_FIR1.shape", "LP"); // fixed parameter 
		
		lp2 = new FIRProcessor("Decoder.LP_FIR2");
		lp2.setParameter("Decoder.LP_FIR2.shape", "LP");  // fixed parameter
		
		chunker = new Chunker("Decoder.chunker");
		chunknorm = new Normalise("Decoder.normalise");
		chunkEnv = new EnvelopeShaper("Decoder.chunkEnv");
		
		norm1 = new Normalise("Decoder.norm1");
		norm2 = new Normalise("Decoder.norm2");
		norm3 = new Normalise("Decoder.norm3");
		
		pitchFinder = new FFTPitchFinder("Decoder.pitchFinder");

		
		initRandomParameters();
		initFromParameters();
	}

	public void initFromParameters() {
		initProcessors();// clears pre/post lists
		try {
			// *** Cropper - applied in main path ***
			cropper.initFromParameters();
			
			// *** HP ***
			hp.initFromParameters();
			if (parameters.getValue("Decoder.HP_FIR.on").equals("true")) {
				addPreProcessor(hp);
			}
			
			addPreProcessor(norm1);
			
			lp1.initFromParameters();
			if (parameters.getValue("Decoder.LP_FIR1.on").equals("true")) {
				addPreProcessor(lp1);
			}
			
			addPreProcessor(norm2);

			lp2.initFromParameters();
			if (parameters.getValue("Decoder.LP_FIR2.on").equals("true")) {
				addPreProcessor(lp2);
			}
			
			addPreProcessor(norm3);
			
			compressor.initFromParameters();
			if (parameters.getValue("Decoder.compressor.on").equals("true")) {
			addPreProcessor(compressor);
			}

			// *** Chunker - applied in main path ***
			chunker.initFromParameters();

		//	System.out.println(parameters);
			// chunknorm.initFromParameters();
			// *** chunknorm - normalise individual chunks ***
			if (parameters.getValue("Decoder.chunkNorm.on").equals("true")) {
				addPreProcessor(chunknorm);
			}

			chunkEnv.initFromParameters();
			if (parameters.getValue("Decoder.chunkEnv.on").equals("true")) {
				addPreProcessor(chunkEnv);
			}
			pitchFinder.initFromParameters();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void initRandomParameters() { // rename to encoder.silenceThreshold etc?
		createParameter(cropper, "Decoder.cropper.silenceThreshold");
		createParameter(chunker, "Decoder.chunker.cropProportion");
		createParameter(chunknorm, "Decoder.chunkNorm.on");
		createParameter(chunkEnv, "Decoder.chunkEnv.on");
		createParameter(chunkEnv, "Decoder.chunkEnv.attackProportion");
		createParameter(chunkEnv, "Decoder.chunkEnv.decayProportion");
		createParameter(hp, "Decoder.HP_FIR.on");
		createParameter(hp, "Decoder.HP_FIR.window");
		createParameter(hp, "Decoder.HP_FIR.cutoff");
		createParameter(hp, "Decoder.HP_FIR.npoints");
		createParameter(lp1, "Decoder.LP_FIR1.on");
		createParameter(lp1, "Decoder.LP_FIR1.window");
		createParameter(lp1, "Decoder.LP_FIR1.cutoff");
		createParameter(lp1, "Decoder.LP_FIR1.npoints");
		createParameter(lp2, "Decoder.LP_FIR2.on");
		createParameter(lp2, "Decoder.LP_FIR2.window");
		createParameter(lp2, "Decoder.LP_FIR2.cutoff");
		createParameter(lp2, "Decoder.LP_FIR2.npoints");
		createParameter(compressor, "Decoder.compressor.on");
		createParameter(compressor, "Decoder.compressor.windowLength");
		createParameter(compressor, "Decoder.compressor.lowThreshold");
		createParameter(compressor, "Decoder.compressor.highThreshold");
		createParameter(pitchFinder, "Decoder.pitchFinder.fftBits");
		createParameter(pitchFinder, "Decoder.pitchFinder.peakDelta");
		createParameter(pitchFinder, "Decoder.pitchFinder.repeatToFit");
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