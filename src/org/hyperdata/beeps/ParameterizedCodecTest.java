/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.pipelines.DefaultPipeline;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
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
Debug debug = new Debug();
		// String input = "http://danbri.org/foaf.rdf#danbri";
		String input = ASCIICodec.getRandomASCII();
		// http://danbri.org/foaf.rdf#danbri
		String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";

		Debug.inform("Input : " + input);
		Debug.inform(input.length() + " characters\n");

		int runs = 10;
		
		int successCount = 0;
		double mostAccurate = 0;
		double accuracySum = 0;
		long encodeTimeSum = 0;
		long decodeTimeSum = 0;
		long encodeHitTimeSum = 0;
		long decodeHitTimeSum = 0;
		
		for (int j = 0; j < runs; j++) { // start for loop
			System.out.println(j);
//			System.out.println("============== Start Run =========");
		//	Encoder encoder = new Encoder();
			ParameterizedEncoder encoder = new ParameterizedEncoder();
			Debug.debug(((ParameterizedEncoder) encoder));

//			System.out.println("Encoder prams "
//					+ ((ParameterizedEncoder) encoder).parameters);

			long startTime = System.currentTimeMillis();
			Tone outTones = encoder.encode(input); // "http://danbri.org/foaf.rdf#danbri"
			// WavCodec.save(filename, outTones); // SAVE
			long encodeTime = System.currentTimeMillis() - startTime;
			
			encodeTimeSum += encodeTime;

			if (Debug.showPlots) {
				Plotter.plot(outTones, "encoder OutTones");
			}
			
//			System.out.println("Encode time: " + (float) (encodeTime)
//					/ 1000 + " seconds");

			Debug.inform("Encode time: " + (float) (encodeTime)
					/ 1000 + " seconds");
			Debug.inform((float) (encodeTime - startTime) / input.length()
					+ " mS per char");

			// line will be the Real World between systems
			DefaultPipeline line = new DefaultPipeline();
			Tone inTones = line.applyProcessors(outTones); // skip saving
			
			// List<Double> inTones = WavCodec.read(filename);
			ParameterizedDecoder decoder = new ParameterizedDecoder();
			
			startTime = System.currentTimeMillis();
			// read here
			String output = decoder.decode(inTones);
			long decodeTime = System.currentTimeMillis() - startTime;
			decodeTimeSum += decodeTime;

//			System.out.println("Decode time: " + (float) (decodeTime)
//					/ 1000 + " seconds");
			
			Debug.inform("Decode time: " + (float) (decodeTime)
					/ 1000 + " seconds");
			Debug.verbose((float) (decodeTime) / input.length()
					+ " mS per char");

			Debug.inform("Output : " + output);
		//	System.out.println("Output : " + output);
			if (output.equals(input)) {
				successCount++;
				decodeHitTimeSum += decodeTime;
				encodeHitTimeSum += encodeTime;
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
			double accuracy = 100 * (double) hits
					/ (double) input.length();
			accuracySum += accuracy;
			if(accuracy > mostAccurate){
				mostAccurate = accuracy;
			}
//			System.out.println("Hits = " + 100 * (double) hits
//					/ (double) input.length() + " %");
			Debug.inform("Hits = " + 100 * (double) hits
					/ (double) input.length() + " %");
			if (errs.length() > 0) {
				Debug.verbose("Bad chars = " + errs);
			}
			Debug.log(encoder.parameters +"\n\n"+decoder.parameters);
		} // end for loop
		System.out.println("Success count = " + successCount + " out of "
				+ runs);
		System.out.println("Most accurate = "+mostAccurate + " %");
		System.out.println("Mean accuracy = "+accuracySum/runs + " %");
		System.out.println("Average encode time = "+Plotter.roundToSignificantFigures((float)encodeTimeSum/(1000*runs), 2)+" seconds");
		System.out.println("Average decode time = "+Plotter.roundToSignificantFigures((float)decodeTimeSum/(1000*runs), 2)+" seconds");
		System.out.println("Average total time = "+Plotter.roundToSignificantFigures(((float)encodeTimeSum+decodeTimeSum)/(1000*runs), 2)+" seconds");
		System.out.println("Mean encode time for 100% transfer = "+Plotter.roundToSignificantFigures((float)encodeHitTimeSum/(1000*successCount), 2)+" seconds");
		System.out.println("Mean decode time for 100% transfer = "+Plotter.roundToSignificantFigures((float)decodeHitTimeSum/(1000*successCount), 2)+" seconds");
	}
}
