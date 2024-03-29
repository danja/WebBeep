/**
 * 
 */
package org.hyperdata.beeps.old;

import java.net.IDN;
import java.util.*;

import org.hyperdata.beeps.ASCIICodec;
import org.hyperdata.beeps.DefaultCodec;
import org.hyperdata.beeps.pitchfinders.FFT;
import org.hyperdata.beeps.pitchfinders.PeakDetector;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.Merger;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 * Sequence of processors and the their parameters are hardcoded
 * 
 */
public class FixedEncoder {

	public FixedEncoder() {
		super();
	}


	/**
	 * 
	 */
	public Tone encode(String idn) {
		
		String ascii = IDN.toASCII(idn); // Punycode encode
		ascii = Checksum.makeChecksumString(ascii) + ascii;
		Chunks chunks = ASCIICodec.asciiToChunks(ascii);
		
		EnvelopeShaper chunkEnv = new EnvelopeShaper("Encoder.chunkEnv");
		chunkEnv.setAttackProportion(0.003);
		chunkEnv.setDecayProportion(0.14);
		chunks = chunkEnv.process(chunks);
		
		Processor chunkNorm = new Normalise("Encoder.chunkNorm");
		chunks = chunkNorm.process(chunks);
		
		// preprocess
		Merger merger = new Merger("Encoder.merger");
		Tone tones = merger.process(chunks);
		// postprocess
		
		return tones;
	}

	static String IRI = "http://danbri.org/foaf.rdf#danbri";

	// "http://danbri.org/foaf.rdf#danbri"; // "OK" is good!
	// http://dannyayers.com/stuff
	// Aa
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String iri = IRI;
		if (args.length > 0) {
			iri = args[0];
		}
		String filename = "./data/beeps.wav";
		FixedEncoder encoder = new FixedEncoder();
		List<Double> tones = encoder.encode(iri);
		Plotter.plot(tones, "Tones");
		System.out.println("tones=" + tones.size());
		 WavCodec.save(filename, tones);
	}



}
