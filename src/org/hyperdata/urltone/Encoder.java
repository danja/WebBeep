/**
 * 
 */
package org.hyperdata.urltone;

import java.net.IDN;
import java.util.*;

import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.common.WavCodec;
import org.hyperdata.urltone.decode.PreProcess;
import org.hyperdata.urltone.fft.FFT;

/**
 * @author danny
 *
 */
public class Encoder {
	
	static String IRI = "aA";
			// "http://danbri.org/foaf.rdf#danbri"; // "OK" is good! http://dannyayers.com/stuff
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String iri = IRI;
		if(args.length > 0){
			iri = args[0];
		}
		String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";
		List<Double> tones = encode(IRI);
		Plotter.plot(tones, "Tones");
		System.out.println("tones="+tones.size());
		// WavCodec.save("/home/danny/workspace/WebBeep/data/beeps1.wav", tones);
		FFT fft = new FFT(15);
		List<Double> freqs = fft.doPowerFFT(tones, false).subList(0,1000);
		freqs = PreProcess.normalise(freqs, true, true);
		freqs = freqs.subList(300,500);
		// plot(String title, List<Double> data, int pointSize, boolean drawLines)
		Plotter.plot(freqs, "Freqs", 4, true);
		PreProcess.findPeaks(freqs);

		
		
	}
	
	/**
	 * 
	 */
	public static List<Double> encode(String idn) {

		String ascii = IDN.toASCII(idn); // Punycode encode
		
		List<Double> tones = new ArrayList<Double>();
		
		tones.addAll(WaveMaker.makeSilence(Constants.START_PAD_DURATION));
		
		int checksum = 0;
		
		for(int i=0;i<ascii.length();i++){
			// System.out.print(ascii.charAt(i));
			
			int val = (int)ascii.charAt(i);
			checksum += val;
			// System.out.println(val);
			int lsVal = val % 16; // least significant hex digit of val is for high tone
			
			int msVal = (val - val % 16)/16;
			tones.addAll(WaveMaker.makeDualtone(msVal, lsVal,Constants.TONE_DURATION));
			tones.addAll(WaveMaker.makeSilence(Constants.SILENCE_DURATION));
		}
		tones.addAll(WaveMaker.makeSilence(Constants.END_PAD_DURATION));
		return tones;
	}
}
