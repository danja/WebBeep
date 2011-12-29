/**
 * 
 */
package org.hyperdata.beeps.old;

import org.hyperdata.beeps.ASCIICodec;
import org.hyperdata.beeps.DefaultCodec;
import org.hyperdata.beeps.DefaultDecoder;
import org.hyperdata.beeps.DefaultEncoder;
import org.hyperdata.beeps.Encoder;
import org.hyperdata.beeps.Line;
import org.hyperdata.beeps.system.DefaultParameterList;
import org.hyperdata.beeps.system.DefaultPipeline;
import org.hyperdata.beeps.system.Parameter;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.util.Plotter;
import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.go.Organism;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * 
 * PROBABLY NEEDS FIXING
 * @author danny
 * 
 */
public class CodecFFT extends DefaultCodec implements Organism { 
	
	/**
	 * @param name
	 */
	public CodecFFT(String name) {
		super(name);
	}

	private int minCharacters = 5;
private int maxCharacters = 30;
private int age = 0;

// somehow pass this
														// parameters

	/**
	 * @param characters the characters to set
	 * @param maxCharacters 
	 */
	public void setnCharacters(int minCharacters, int maxCharacters) {
		this.minCharacters = minCharacters;
		this.maxCharacters = maxCharacters;
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

	DefaultEncoder encoder;
	DefaultDecoder decoder;
	DefaultPipeline line;
	private double runTime;
	private double accuracy;

	public double getFitness() {
		return fitness;
	}

	/**
	 * @param args
	 */

	public void init() {
		encoder = new DefaultEncoder("Encoder");
		decoder = new DefaultDecoder("Decoder");
		line = new Line();
	}

	public void run() {
	//	String input = "http://dannyayers.com";
		String input = ASCIICodec.getRandomASCII(minCharacters, maxCharacters);
		if(Math.random() > 0.6){
			input = "http://"+input;
		}
		// input = "http://dannyayers.com";
		// System.out.println("Input:"+input);
		//Debug debug = new Debug();

		//String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";

		Debug.inform("Input : " + input);
		Debug.inform(input.length() + " characters\n");

		Debug.debug(((Encoder) encoder));
		
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

		Tone inTones = line.process(outTones); // skip saving
		
		startTime = System.currentTimeMillis();
		String output = decoder.decode(inTones);
		long decodeTime = System.currentTimeMillis() - startTime;

		// System.out.print("Output: "+ output + "   ");
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
		System.out.print(Plotter.roundToSignificantFigures(percent,2)+"% ");
		Debug.inform("Hits = " + percent + " %");
		if (errs.length() > 0) {
			Debug.verbose("Bad chars = " + errs);
		}
		//Debug.log(encoder.parameters + "\n\n" + decoder.parameters);

		this.runTime = (encodeTime + decodeTime) / 1000;
		
		this.fitness = accuracy * accuracy / (runTime + 1);
		if(accuracy < 0.02){
			fitness = fitness / 2; 
		}
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
		ParameterList encoderParameters = new DefaultParameterList("Encoder");
		ParameterList decoderParameters = new DefaultParameterList("Decoder");
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



	/* (non-Javadoc)
	 * @see org.hyperdata.go.Organism#getAge()
	 */
	@Override
	public int getAge() {
		return age;
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.go.Organism#getAgeFactor()
	 */
	@Override
	public double getMeanFitness() {
		// TODO Auto-generated method stub
		return 0;
	}


}
