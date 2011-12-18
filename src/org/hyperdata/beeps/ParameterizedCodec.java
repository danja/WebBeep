/**
 * 
 */
package org.hyperdata.beeps;

import org.hyperdata.beeps.util.Plotter;
import java.util.List;

import org.hyperdata.beeps.pipelines.DefaultPipeline;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;
import org.hyperdata.go.Organism;
import org.hyperdata.go.parameters.DefaultParameterList;
import org.hyperdata.go.parameters.Parameter;
import org.hyperdata.go.parameters.ParameterList;

/**
 * @author danny
 * 
 */
public class ParameterizedCodec implements Organism { // somehow pass this
														// parameters

	/**
	 * @param characters the characters to set
	 */
	public void setnCharacters(int characters) {
		this.nCharacters = characters;
	}

	/**
	 * @return the runTime
	 */
	public double getRunTime() {
		return this.runTime;
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return this.accuracy;
	}

	boolean hasRun = false;
	double fitness = -1;

	ParameterizedEncoder encoder;
	ParameterizedDecoder decoder;
	DefaultPipeline line;
	private double runTime;
	private double accuracy;
	private int nCharacters;

	public ParameterizedCodec() {

	}

	public double getFitness() {
		return fitness;
	}

	/**
	 * @param args
	 */

	public void init() {
		encoder = new ParameterizedEncoder();
		decoder = new ParameterizedDecoder();
		line = new DefaultPipeline();
	}

	public void run() {
	//	String input = "http://dannyayers.com";
		String input = "http://"+ASCIICodec.getRandomASCII(nCharacters);
		// System.out.println("Input:"+input);
		Debug debug = new Debug();

		//String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";

		Debug.inform("Input : " + input);
		Debug.inform(input.length() + " characters\n");

		Debug.debug(((ParameterizedEncoder) encoder));
		
		long startTime = System.currentTimeMillis();
		Tone outTones = encoder.encode(input); // "http://danbri.org/foaf.rdf#danbri"
		long encodeTime = System.currentTimeMillis() - startTime;

		// encodeTimeSum += encodeTime;

		if (Debug.showPlots) {
			Plotter.plot(outTones, "encoder OutTones");
		}

		Debug.inform("Encode time: " + (float) (encodeTime) / 1000 + " seconds");
		Debug.inform((float) (encodeTime - startTime) / input.length()
				+ " mS per char");

		// line will be the Real World between systems

		Tone inTones = line.applyProcessors(outTones); // skip saving
		
		startTime = System.currentTimeMillis();
		String output = decoder.decode(inTones);
		long decodeTime = System.currentTimeMillis() - startTime;

		System.out.print("Output: "+ output + "   ");
		Debug.inform("Decode time: " + (float) (decodeTime) / 1000 + " seconds");
		Debug.verbose((float) (decodeTime) / input.length() + " mS per char");

		Debug.inform("Output : " + output);
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
		this.accuracy =  (double) hits / (double) input.length();
		double percent = 100 * (double) hits / (double) input.length();
		System.out.println(Plotter.roundToSignificantFigures(percent,2)+"%");
		Debug.inform("Hits = " + percent + " %");
		if (errs.length() > 0) {
			Debug.verbose("Bad chars = " + errs);
		}
		Debug.log(encoder.parameters + "\n\n" + decoder.parameters);

		this.runTime = (encodeTime + decodeTime) / 1000;
		
		this.fitness = accuracy * accuracy / (runTime + 1);
	//	this.fitness = accuracy;
		if(accuracy == 1.0){
			fitness = fitness * 10;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.Organism#setParameters(org.hyperdata.go.parameters.
	 * ParameterSet)
	 */
	@Override
	public void setParameters(ParameterList parameters) { // clunky
		ParameterList encoderParameters = new DefaultParameterList();
		ParameterList decoderParameters = new DefaultParameterList();
		for(int i=0;i<parameters.size();i++){
			Parameter parameter = parameters.get(i);
			if(parameter.getName().startsWith("Encoder")){
				encoderParameters.add(parameter);
			}
			if(parameter.getName().startsWith("Decoder")){
				decoderParameters.add(parameter);
			}
		}
		encoder.setParameters(encoderParameters); 
		decoder.setParameters(decoderParameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.Organism#getParameters()
	 */
	@Override
	public ParameterList getParameters() {
		ParameterList all = new DefaultParameterList();
		all.addAll(encoder.getParameters());
		all.addAll(decoder.getParameters());
		return all;
	}
}
