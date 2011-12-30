/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.parameters.SimpleParameter;
import org.hyperdata.beeps.pitchfinders.GoertzelPitchFinder;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Compressor;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.system.DefaultParameterList;
import org.hyperdata.beeps.system.Parameter;
import org.hyperdata.beeps.system.ParameterFactory;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.Processor;
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
public class DefaultDecoder extends DefaultCodec {

	public String decode(Tone tones) {
		Debug.inform("Decoding");

		Normalise norm = new Normalise("Decoder.input.normalise");
		
		tones = norm.process(tones);

		tones = applyPreProcessors(tones);

		// always crop
		tones = cropper.process(tones);

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

	public DefaultDecoder(String name) {
		super(name);
		init();
	}

	// components/processors
	private Chunker chunker;
	private Cropper cropper;
	private GoertzelPitchFinder pitchFinder;
	private Processor chunkNorm;
	private Processor chunkEnv;
	private Processor hp;
	private Processor lp1;
	private Processor lp2;
	private Processor norm;
	private Processor compressor;

	public void init() {
		
		// core components (always on) ---------------------------------------
		cropper = new Cropper("Decoder.core.cropper");
		addCoreComponent(cropper);
		
		norm = new Normalise("Decoder.core.normalise");
		addCoreComponent(norm);
		
		chunker = new Chunker("Decoder.core.chunker");
		addCoreComponent(chunker);
		
		pitchFinder = new GoertzelPitchFinder("Decoder.core.pitchFinder");
		addCoreComponent(pitchFinder);
		
		// preprocessors ------------------------------------------------------
		compressor = new Compressor("Decoder.pre.compressor");
		addPreProcessor(compressor);
		
		hp = new FIRProcessor("Decoder.pre.HP_FIR");
		hp.setParameter("Decoder.pre.HP_FIR.shape", "HP"); // fixed parameter
		addPreProcessor(hp);
		addPreProcessor(norm);
		
		lp1 = new FIRProcessor("Decoder.pre.LP_FIR1");
		lp1.setParameter("Decoder.pre.LP_FIR1.shape", "LP"); // fixed parameter
		addPreProcessor(lp1);
		addPreProcessor(norm);
		
		lp2 = new FIRProcessor("Decoder.pre.LP_FIR2");
		lp2.setParameter("Decoder.pre.LP_FIR2.shape", "LP"); // fixed parameter
		addPreProcessor(lp2);
		addPreProcessor(norm);
		
		// postprocessors -----------------------------------------------------
		chunkNorm = new Normalise("Decoder.post.chunkNorm");
		addPostProcessor(chunkNorm);
		
		chunkEnv = new EnvelopeShaper("Decoder.post.chunkEnv");
		addPostProcessor(chunkEnv);

		createParameters();
	}

	public void createParameters() { 
		createParameter(norm, "Decoder.core.normalise.on");
		
		createParameter(pitchFinder, "Decoder.core.pitchFinder.gThreshold");
		
		createParameter(cropper, "Decoder.core.cropper.silenceThreshold");
		createParameter(cropper, "Decoder.core.cropper.on");
		
		createParameter(chunker, "Decoder.core.chunker.cropProportion");
		createParameter(chunker, "Decoder.core.chunker.on");
		
		createParameter(hp, "Decoder.pre.HP_FIR.on");
		createParameter(hp, "Decoder.pre.HP_FIR.window");
		createParameter(hp, "Decoder.pre.HP_FIR.cutoff");
		createParameter(hp, "Decoder.pre.HP_FIR.npoints");
		createParameter(lp1, "Decoder.pre.LP_FIR1.on");
		createParameter(lp1, "Decoder.pre.LP_FIR1.window");
		createParameter(lp1, "Decoder.pre.LP_FIR1.cutoff");
		createParameter(lp1, "Decoder.pre.LP_FIR1.npoints");
		createParameter(lp2, "Decoder.pre.LP_FIR2.on");
		createParameter(lp2, "Decoder.pre.LP_FIR2.window");
		createParameter(lp2, "Decoder.pre.LP_FIR2.cutoff");
		createParameter(lp2, "Decoder.pre.LP_FIR2.npoints");
		
		createParameter(compressor, "Decoder.pre.compressor.on");
		createParameter(compressor, "Decoder.pre.compressor.windowLength");
		createParameter(compressor, "Decoder.pre.compressor.lowThreshold");
		createParameter(compressor, "Decoder.pre.compressor.highThreshold");
		
		createParameter(chunkNorm, "Decoder.post.chunkNorm.on");
		
		createParameter(chunkEnv, "Decoder.post.chunkEnv.on");
		createParameter(chunkEnv, "Decoder.post.chunkEnv.attackProportion");
		createParameter(chunkEnv, "Decoder.post.chunkEnv.decayProportion");
	}



	public String toString() {
		String string = this.getClass().toString();
		string += super.toString();
		return string;
	}
}