/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pitchfinders.GoertzelPitchFinder;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Compressor;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 * 
 *         NEED TO CHECK SILENCE
 * 
 */
public class ParameterizedDecoderGoertzel extends DefaultCodec {
	/**
	 * @return the parameters
	 */
	public ParameterList getParameters() {
		return this.parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(ParameterList parameters) {
		super.setParameters(parameters);
	//	 this.parameters = parameters;
	//	System.out.println("BEFORE\n"+this.parameters);
		this.parameters.consume(parameters);
	//	System.out.println("AFTER\n"+this.parameters);
		// initFromParameters();
	}

	

	public String decode(Tone tones) {
		Debug.inform("Decoding");

		// always normalise
		Normalise norm = new Normalise("Decoder.input.normalise");
//		tones = norm.process(tones);

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

	public ParameterizedDecoderGoertzel(String name) {
		super(name);
		init();
	}

	private Chunker chunker;
	private Cropper cropper;
	private GoertzelPitchFinder pitchFinder;
	private Processor chunkNorm;
	private Processor chunkEnv;
	private Processor hp;
	private Processor lp1;
	private Processor lp2;

	private Processor norm;

	private Compressor compressor;

	public void init() {
		cropper = new Cropper("Decoder.cropper");
		compressor = new Compressor("Decoder.compressor");
		norm = new Normalise("Decoder.normalise");

		addPreProcessor(compressor);
		
		hp = new FIRProcessor("Decoder.HP_FIR");
		hp.setParameter("Decoder.HP_FIR.shape", "HP"); // fixed parameter
		addPreProcessor(hp);
		addPreProcessor(norm);
		
		lp1 = new FIRProcessor("Decoder.LP_FIR1");
		lp1.setParameter("Decoder.LP_FIR1.shape", "LP"); // fixed parameter
		addPreProcessor(lp1);
		addPreProcessor(norm);
		
		lp2 = new FIRProcessor("Decoder.LP_FIR2");
		lp2.setParameter("Decoder.LP_FIR2.shape", "LP"); // fixed parameter
		addPreProcessor(lp2);
		addPreProcessor(norm);
		
		chunker = new Chunker("Decoder.chunker");
		
		chunkNorm = new Normalise("Decoder.chunkNorm");
		addPostProcessor(chunkNorm);
		
		chunkEnv = new EnvelopeShaper("Decoder.chunkEnv");
		addPostProcessor(chunkEnv);

		pitchFinder = new GoertzelPitchFinder("Decoder.pitchFinder");

		createParameters();
		//parameters.
	//	initFromParameters();
	}

	public void initFromParameters() {
		try {
			// *** Cropper - applied in main path ***
			cropper.initFromParameters();
			hp.initFromParameters();
			lp1.initFromParameters();
			lp2.initFromParameters();
			compressor.initFromParameters();
				
			// *** Chunker - applied in main path ***
			chunker.initFromParameters();
			
			chunkNorm.initFromParameters();
			chunkEnv.initFromParameters();
			pitchFinder.initFromParameters();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void createParameters() { 
		createParameter(cropper, "Decoder.cropper.silenceThreshold");
		createParameter(cropper, "Decoder.cropper.on");
		
		createParameter(chunker, "Decoder.chunker.cropProportion");
		createParameter(chunker, "Decoder.chunker.on");
		
		createParameter(chunkNorm, "Decoder.chunkNorm.on");
		
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
		createParameter(pitchFinder, "Decoder.pitchFinder.gThreshold");
	}



	public String toString() {
		String string = this.getClass().toString();
		string += super.toString();
		return string;
	}
}