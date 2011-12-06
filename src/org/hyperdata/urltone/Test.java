/**
 * 
 */
package org.hyperdata.urltone;

import java.util.List;

import org.hyperdata.urltone.util.Plotter;
import org.hyperdata.urltone.util.WavCodec;

/**
 * @author danny
 *
 */
public class Test {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// NOTE FFT is at low value
		
		String input = "o"; // http://danbri.org/foaf.rdf#danbri
		String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";
		
		System.out.println("Input : "+input);
		System.out.println(input.length()+" characters\n");
		
		long startTime = System.currentTimeMillis();
		
		List<Double> outTones = Encoder.encode(input); // "http://danbri.org/foaf.rdf#danbri"
		
		WavCodec.save(filename, outTones);
		Plotter.plot(outTones);
		
		long thisTime = System.currentTimeMillis();
		
		System.out.println("Encode time: " + (float)(thisTime - startTime)/1000 + " seconds");
		System.out.println((float)(thisTime - startTime)/input.length()+" mS per char");
		
		List<Double> inTones = WavCodec.read(filename);
		
		startTime = System.currentTimeMillis();
		String output = Decoder.decode(inTones);
		System.out.println();
		thisTime = System.currentTimeMillis();

		System.out.println("Decode time: " + (float)(thisTime - startTime)/1000 + " seconds");
		System.out.println((float)(thisTime - startTime)/input.length()+" mS per char");
		
		if(output.equals(input)){
			System.out.println("\n*** Success!!! ***");
		} else {
			System.out.println("\n*** FAIL! ***");
		}
	}

}
