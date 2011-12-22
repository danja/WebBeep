/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.*;

import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pitchfinders.FFT;
import org.hyperdata.beeps.pitchfinders.PeakDetector;
import org.hyperdata.beeps.processors.Merger;
import org.hyperdata.beeps.processors.Normalise;
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
public class Encoder {

	public Encoder() {
		super();
	}


	/**
	 * 
	 */
	public Tone encode(String idn) {

		String ascii = IDN.toASCII(idn); // Punycode encode
		ascii = Checksum.makeChecksumString(ascii) + ascii;
		Chunks chunks = ASCIICodec.asciiToChunks(ascii);
		Merger merger = new Merger();
		Tone tones = merger.process(chunks);
		// tones = applyPostProcessors(tones);
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
		String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";
		Encoder encoder = new Encoder();
		List<Double> tones = encoder.encode(IRI);
		Plotter.plot(tones, "Tones");
		System.out.println("tones=" + tones.size());
		// WavCodec.save("/home/danny/workspace/WebBeep/data/beeps1.wav",
		// tones);
	}



}
