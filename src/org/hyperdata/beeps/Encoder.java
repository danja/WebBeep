/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.*;

import org.hyperdata.beeps.fft.FFT;
import org.hyperdata.beeps.fft.PeakDetector;
import org.hyperdata.beeps.pipelines.DefaultCodec;
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
 */
public class Encoder extends DefaultCodec {

	public Encoder() {
		super();
	}


	/**
	 * 
	 * preprocessors in Encoder are applied to individual dual-tone chunks
	 * postprocessors are applied to the whole outgoing constructed waveform
	 */
	public Tone encode(String idn) {

		String ascii = IDN.toASCII(idn); // Punycode encode

		ascii = Checksum.makeChecksumString(ascii) + ascii;

		// List<List<Double>> chunks = new ArrayList<List<Double>>();

		Chunks chunks = new Chunks();
		
		for (int i = 0; i < ascii.length(); i++) {

			int val = (int) ascii.charAt(i);
			int lsVal = val % 16; // least significant hex digit of val is for
									// high tone
			int msVal = (val - val % 16) / 16;
			
			Tone chunk = WaveMaker.makeDualTone(msVal, lsVal,
					Constants.TONE_DURATION);
			
			chunk = applyPreProcessors(chunk);
			chunks.add(chunk);
		}
//		Tone tones = new Tone(merge(chunks));
		Merger merger = new Merger();
		Tone tones = new Tone(merger.process(chunks));
		tones = new Tone(applyPostProcessors(tones));
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
