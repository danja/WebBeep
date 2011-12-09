/**
 * 
 */
package org.hyperdata.urltone.encode;

import java.net.IDN;
import java.util.*;

import org.hyperdata.urltone.WaveMaker;
import org.hyperdata.urltone.decode.PreProcess;
import org.hyperdata.urltone.decode.fft.FFT;
import org.hyperdata.urltone.decode.fft.PeakDetector;
import org.hyperdata.urltone.util.Constants;
import org.hyperdata.urltone.util.Maps;
import org.hyperdata.urltone.util.Plotter;
import org.hyperdata.urltone.util.WavCodec;

/**
 * @author danny
 * 
 */
public class Encoder {

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
		List<Double> tones = encode(IRI);
		Plotter.plot(tones, "Tones");
		System.out.println("tones=" + tones.size());
		// WavCodec.save("/home/danny/workspace/WebBeep/data/beeps1.wav",
		// tones);
	}

	/**
	 * 
	 */
	public static List<Double> encode(String idn) {

		String ascii = IDN.toASCII(idn); // Punycode encode
		
		ascii = Checksum.makeChecksumString(ascii) + ascii;

		List<Double> tones = new ArrayList<Double>();

		tones.addAll(WaveMaker.makeSilence(Constants.START_PAD_DURATION));

		for (int i = 0; i < ascii.length(); i++) {
			// System.out.print(ascii.charAt(i));

			int val = (int) ascii.charAt(i);
			// System.out.println(val);
			int lsVal = val % 16; // least significant hex digit of val is for
									// high tone

			int msVal = (val - val % 16) / 16;
			tones.addAll(WaveMaker.makeDualtone(msVal, lsVal,
					Constants.TONE_DURATION));
			tones.addAll(WaveMaker.makeSilence(Constants.SILENCE_DURATION));
		}
		tones.addAll(WaveMaker.makeSilence(Constants.END_PAD_DURATION));
		return tones;
	}
}
