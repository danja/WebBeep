/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.Iterator;
import java.util.Random;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.processors.AllToNoiseProcessor;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Merger;
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
import org.hyperdata.common.describe.DefaultDescriber;
import org.hyperdata.common.describe.Named;

/**
 * @author danny
 * 
 *         preprocessors in Encoder are applied to individual dual-tone chunks
 *         postprocessors are applied to the whole outgoing constructed waveform
 */
public class DefaultEncoder extends DefaultCodec implements Encoder{

	public DefaultEncoder(String name) {
		super(name);
		init();
	}

	/**
	 * @param string
	 * @param string2
	 */
	public DefaultEncoder(String name, String uri) {
		this(name);
		setURI(uri);
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
	//	Plotter.plot(tones, "Encoding for the string \"abc\"");
		Tone padded = new Tone(WaveMaker.makeSilence(1.0));
		padded.addAll(tones);
		padded.addAll(new Tone(WaveMaker.makeSilence(1.0)));
		
		return padded;
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

	public void createParameters() {
		createParameter(chunkEnv, "Encoder.pre.chunkEnv.on");
		createParameter(chunkEnv, "Encoder.pre.chunkEnv.attackProportion");
		createParameter(chunkEnv, "Encoder.pre.chunkEnv.decayProportion");
		
		createParameter(chunkNorm, "Encoder.pre.chunkNorm.on");
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		String description =super.describe();
				// DefaultDescriber.getDescription(this);
	//	System.out.println("************\n"+description+"\n8888888888\n");
		// description += super.describe();
		description += "<"+getURI()+"> a beep:Encoder .";
		description += "<"+getURI()+"> a proc:Processor .";
	//	description += super.describe();
		return description;
	}
}