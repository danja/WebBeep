/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.decode.Decoder;
import org.hyperdata.beeps.encode.Encoder;
import org.hyperdata.beeps.optimize.ParameterizedDecoder;
import org.hyperdata.beeps.optimize.ParameterizedEncoder;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class ParameterizedCodecTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// String input = "http://danbri.org/foaf.rdf#danbri";
		String input = "iffff";
		for (char i = 70; i < 128; i++) {
			input += new Character(i);
		}
		// http://danbri.org/foaf.rdf#danbri
		String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";

		Debug.inform("Input : " + input);
		Debug.inform(input.length() + " characters\n");

		long startTime = System.currentTimeMillis();
 
		for (int j = 0; j < 10; j++) { // start for loop
			Encoder encoder = new ParameterizedEncoder();
			Debug.debug(((ParameterizedEncoder) encoder));

			System.out.println("Encoder prams "+((ParameterizedEncoder)encoder).parameters);
			
			List<Double> outTones = encoder.encode(input); // "http://danbri.org/foaf.rdf#danbri"

			// WavCodec.save(filename, outTones); SAVE

			if (Debug.showPlots) {
				Plotter.plot(outTones, "encoder OutTones");
			}
			long thisTime = System.currentTimeMillis();

			System.out.println("Encode time: " + (float) (thisTime - startTime)
					/ 1000 + " seconds");
			
			Debug.inform("Encode time: " + (float) (thisTime - startTime)
					/ 1000 + " seconds");
			Debug.inform((float) (thisTime - startTime) / input.length()
					+ " mS per char");

			List<Double> inTones = outTones; // skip saving
			// List<Double> inTones = WavCodec.read(filename);

			startTime = System.currentTimeMillis();
			Decoder decoder = new ParameterizedDecoder();
			String output = decoder.decode(inTones);
			thisTime = System.currentTimeMillis();

			Debug.inform("Decode time: " + (float) (thisTime - startTime)
					/ 1000 + " seconds");
			Debug.verbose((float) (thisTime - startTime) / input.length()
					+ " mS per char");

			Debug.inform("Output : " + output);
			System.out.println("Output : " + output);
			if (output.equals(input)) {
				Debug.inform("\n*** Success!!! ***");
			} else {
				Debug.inform("\n*** FAIL! ***");
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
			System.out.println("Hits = " + 100 * (double) hits
					/ (double) input.length() + " %");
			Debug.inform("Hits = " + 100 * (double) hits
					/ (double) input.length() + " %");
			if (errs.length() > 0) {
				Debug.verbose("Bad chars = " + errs);
			}
		} // end for loop

	}

}
