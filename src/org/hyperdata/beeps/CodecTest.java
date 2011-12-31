/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.system.DefaultPipeline;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.ParameterListFile;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class CodecTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
			String input = ASCIICodec.getRandomASCII();
			input =  "http://danbri.org/foaf.rdf#danbri";
			
			 String filename = "./data/beeps.wav";

			 String configFilename = "./data/config.xml";
			
			System.out.println("Input : " + input);
			Debug.inform("Input : " + input);
			Debug.inform(input.length() + " characters\n");

			DefaultEncoder encoder = new DefaultEncoder("Encoder");
			DefaultDecoder decoder = new DefaultDecoder("Decoder");
			
			ParameterListFile plf = new ParameterListFile();
			ParameterList config = plf.load(configFilename);
			
		//	System.out.println("SETTING CONFIG \n"+config);
			
			encoder.setParameters(config);
			decoder.setParameters(config);
			
//			System.out.println("AFTER SET \n");
//			System.out.println(encoder);
//			System.exit(0);
			
		//	System.out.println(decoder);
			
			// PARAMETERS AREN't getting set 
			encoder.initFromParameters();
			decoder.initFromParameters();
			
			Debug.debug(((Encoder) encoder));

			System.out.println(encoder);
			System.out.println(decoder);
			
//			System.out.println("Encoder prams "
//					+ ((ParameterizedEncoder) encoder).parameters);

			long startTime = System.currentTimeMillis();
			Tone outTones = encoder.encode(input); // "http://danbri.org/foaf.rdf#danbri"
			
			WavCodec.save(filename, outTones); // SAVE
			
			
			long encodeTime = System.currentTimeMillis() - startTime;
			

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
		//	Line line = new Line();;
			// Tone inTones = line.process(outTones); // skip saving
			Tone 	inTones = outTones;
			// List<Double> inTones = WavCodec.read(filename);

			
			startTime = System.currentTimeMillis();
			// read here
			String output = decoder.decode(inTones);
			long decodeTime = System.currentTimeMillis() - startTime;


//			System.out.println("Decode time: " + (float) (decodeTime)
//					/ 1000 + " seconds");
			
			Debug.inform("Decode time: " + (float) (decodeTime)
					/ 1000 + " seconds");
			Debug.verbose((float) (decodeTime) / input.length()
					+ " mS per char");

			Debug.inform("Output : " + output);
			System.out.println("Output : " + output);
	
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
		
			System.out.println("Hits = " + 100 * (double) hits
					/ (double) input.length() + " %");
			Debug.inform("Hits = " + 100 * (double) hits
					/ (double) input.length() + " %");
			if (errs.length() > 0) {
				Debug.verbose("Bad chars = " + errs);
			}
		//	Debug.log(encoder.parameters +"\n\n"+decoder.parameters);
			}
}
