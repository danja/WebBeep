/**
 * 
 */
package org.hyperdata.beeps;

import org.hyperdata.beeps.util.Plotter;
import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.go.DefaultOrganism;
import org.hyperdata.beeps.go.Organism;
import org.hyperdata.beeps.parameters.DefaultParameterList;
import org.hyperdata.beeps.parameters.Parameter;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.pipelines.DefaultPipeline;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class ParameterizedCodecGoertzel extends DefaultOrganism  {

	private int minCharacters = 5;
	private int maxCharacters = 30;
	/**
	 * @param characters
	 *            the characters to set
	 * @param maxCharacters
	 */
	public void setnCharacters(int minCharacters, int maxCharacters) {
		this.minCharacters = minCharacters;
		this.maxCharacters = maxCharacters;
	}

	DefaultEncoder encoder;
	ParameterizedDecoderGoertzel decoder;
	DefaultPipeline line;
	public ParameterizedCodecGoertzel() {

	}

	/**
	 * @param args
	 */

	public void init() {
		encoder = new DefaultEncoder("Encoder");
		decoder = new ParameterizedDecoderGoertzel("Decoder");
		line = new Line();
	}

	public void run() {
		super.run(); // ages it
		// String input = "http://dannyayers.com";
		String input = ASCIICodec.getRandomASCII(minCharacters, maxCharacters);
		if (Math.random() > 0.7) {
			input = ASCIICodec
					.getRandomWebbyASCII(minCharacters, maxCharacters);
		}
		// input = "http://dannyayers.com";

		// input = "http://dannyayers.com";
		// System.out.println("Input:"+input);
		// Debug debug = new Debug();

		// String filename = "/home/danny/workspace/WebBeep/data/beeps.wav";

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

		Tone inTones;
		if (Math.random() > .75) { // 1/4 of the time, zero distortion
			inTones = outTones;
		} else {
			inTones = line.process(outTones); // skip saving
		}
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
		this.accuracy = (double) hits / (double) input.length();
		double percent = 100 * (double) hits / (double) input.length();
		System.out.print(Plotter.roundToSignificantFigures(percent, 2) + "% ");
		Debug.inform("Hits = " + percent + " %");
		if (errs.length() > 0) {
			Debug.verbose("Bad chars = " + errs);
		}
		Debug.log(encoder.parameters + "\n\n" + decoder.parameters);

		this.runTime = ((double) encodeTime + (double) decodeTime) / 1000;


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
		for (int i = 0; i < parameters.size(); i++) {
			Parameter parameter = parameters.get(i);
			if (parameter.getName().startsWith("Encoder")) {
				encoderParameters.add(parameter);
			}
			if (parameter.getName().startsWith("Decoder")) {
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

	public String toString() {
		String string = "\n" + this.getClass().toString() + "\n";
		string += "\n" + encoder.toString() + "\n";
		string += "\n" + decoder.toString() + "\n";
		return string;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.go.Organism#getFitness()
	 */
	@Override
	public double getFitness() {
		double fitness = (1 + (double) getAge() / 100) * getAccuracy() * getAccuracy()
				/ (getRunTime() + 1); // need to tweak age bit
		if (getAccuracy() < 0.02) {
			fitness = fitness / 2;
		}
		// this.fitness = accuracy;
		if (getAccuracy() == 1.0) {
			fitness = fitness * 10;
		}
		return fitness;
	}
}
