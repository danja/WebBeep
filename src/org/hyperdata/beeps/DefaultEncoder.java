/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.Iterator;
import java.util.Random;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterFactory;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.processors.AllToNoiseProcessor;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Merger;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 *         preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 */
public class DefaultEncoder extends DefaultCodec implements Encoder {

	public DefaultEncoder(String name) {
		super(name);
		init();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Encoder#encode(java.lang.String)
	 */
	@Override
	public Tone encode(String idn) {
		String ascii = IDN.toASCII(idn); // Punycode encode
		ascii = Checksum.makeChecksumString(ascii) + ascii;
		Chunks chunks = ASCIICodec.asciiToChunks(ascii);
		
		chunks = applyPreProcessors(chunks);
		
		Merger merger = new Merger("Encoder.merger");
		Tone tones = merger.process(chunks);
		
		tones = applyPostProcessors(tones);
		
		return tones;
	}

	private Processor chunkEnv;

	private Processor chunkNorm;

	public void init() {
		chunkEnv = new EnvelopeShaper("Encoder.pre.chunkEnv");
		addPreProcessor(chunkEnv);
		chunkNorm = new Normalise("Encoder.pre.chunkNorm");
		addPreProcessor(chunkNorm);
		
		createParameters();
	}

//	public void initFromParameters() {
//			chunkEnv.initFromParameters();
//			chunkNorm.initFromParameters();
//	}

	public void createParameters() {
		createParameter(chunkEnv, "Encoder.pre.chunkEnv.on");
		createParameter(chunkEnv, "Encoder.pre.chunkEnv.attackProportion");
		createParameter(chunkEnv, "Encoder.pre.chunkEnv.decayProportion");
		
		createParameter(chunkNorm, "Encoder.pre.chunkNorm.on");
	}
}