/**
 * 
 */
package org.hyperdata.urltone;

import java.net.IDN;
import java.util.*;

import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.common.WavCodec;

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
		WavCodec.save(filename, tones);
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
