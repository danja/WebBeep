/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.decode.Decoder;
import org.hyperdata.beeps.encode.Encoder;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class BasicCodecTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// String input = "http://danbri.org/foaf.rdf#danbri";
		// String input = "iffff";
//		for (char i = 70; i < 128; i++) {
//			input += new Character(i);
//		}
		String input = Helpers.getRandomASCII();
		// http://danbri.org/foaf.rdf#danbri
		String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";

		System.out.println("Input : " + input);
		System.out.println(input.length() + " characters\n");

		long startTime = System.currentTimeMillis();

		Encoder encoder = new Encoder();

		List<Double> outTones = encoder.encode(input); // "http://danbri.org/foaf.rdf#danbri"

		WavCodec.save(filename, outTones);
		if (Debug.showPlots) {
			Plotter.plot(outTones, "OutTones");
		}

		long thisTime = System.currentTimeMillis();

		System.out.println("Encode time: " + (float) (thisTime - startTime)
				/ 1000 + " seconds");
		System.out.println((float) (thisTime - startTime) / input.length()
				+ " mS per char");

		List<Double> inTones = WavCodec.read(filename);

		startTime = System.currentTimeMillis();
		Decoder decoder = new Decoder();
		String output = decoder.decode(inTones);
		System.out.println();
		thisTime = System.currentTimeMillis();

		System.out.println("Decode time: " + (float) (thisTime - startTime)
				/ 1000 + " seconds");
		System.out.println((float) (thisTime - startTime) / input.length()
				+ " mS per char");

		System.out.println("Output : " + output);
		if (output.equals(input)) {
			System.out.println("\n*** Success!!! ***");
		} else {
			System.out.println("\n*** FAIL! ***");
		}
		int hits = 0;
		String errs = "";
		for (int i = 0; i < input.length(); i++) {
			try {
				if (input.charAt(i) == output.charAt(i)) {
					hits++;
				} else {
					errs += input.charAt(i);
				}
			} catch (Exception e) {
				// ignore
			}
		}
		System.out.println("Hits = " + 100 * ((double) hits)
				/ ((double) input.length()) + " %");
		if (errs.length() > 0) {
			System.out.println("Bad chars = " + errs);
		}

	}

}
