/**
 * 
 */
package org.hyperdata.urltone;

import java.net.IDN;
import java.util.*;

import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.common.WavCodec;

/**
 * @author danny
 *
 */
public class Encoder {
	
	static String IRI = "http://danbri.org/foaf.rdf#danbri"; // "OK" is good! http://dannyayers.com/stuff
	// 140 bpm
//	0.58333333 barsps
//	/2 = 0.291666665
//	*2 =1.16666666
//	*2 = 2.33333333
	//0.145833333;
	// 0.0729166667
	static double TONE_DURATION = 0.145833333;
	static double SILENCE_DURATION = 0;
	static double START_PAD_DURATION = 0.0729166667;
	static double END_PAD_DURATION = 0.145833333;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String iri = IRI;
		if(args.length > 0){
			iri = args[0];
		}
		String filename = "/home/danny/workspace/UTone/data/beeps.wav";
		List<Double> tones = makeTones(IRI);
		Plotter.plot("Tones", tones);
		WavCodec.save(filename, tones);
	}
	/**
	 * 
	 */
	public static List<Double> makeTones(String iri) {

		
		String ascii = IDN.toASCII(iri); // Punycode encode
		
		List<Double> tones = new ArrayList<Double>();
		
		tones.addAll(WaveMaker.makeSilence(START_PAD_DURATION));
		
		int checksum = 0;
		
		for(int i=0;i<ascii.length();i++){
			System.out.print(ascii.charAt(i));
			
			int val = (int)ascii.charAt(i);
			checksum += val;
			// System.out.println(val);
			int lsVal = val % 16; // least significant hex digit of val is for high tone
			
			int msVal = (val - val % 16)/16;
			tones.addAll(WaveMaker.makeDualtone(msVal, lsVal,TONE_DURATION));
			tones.addAll(WaveMaker.makeSilence(SILENCE_DURATION));
		}
		tones.addAll(WaveMaker.makeSilence(END_PAD_DURATION));
		return tones;
	}
}
