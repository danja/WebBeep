/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public class RunningAverage extends DefaultProcessor {

	private int windowLength = 256;

	/**
	 * @param name
	 */
	public RunningAverage(String name) {
		super(name);
	}

	/**
	 * @return the windowLength
	 */
	public int getWindowLength() {
		return this.windowLength;
	}

	/**
	 * @param windowLength
	 *            the windowLength to set
	 */
	public void setWindowLength(int windowLength) {
		this.windowLength = windowLength;
	}

	public static List<Double> filter(List<Double> tones, int windowLength) {

		List<Double> filtered = new ArrayList<Double>();

		// int WINDOW_LENGTH = (int) ((double) Constants.SAMPLE_RATE *
		// WINDOW_TIME);
		// System.out.println(WINDOW_LENGTH);
		double max = 0;
		double total = 0;

		for (int i = 0; i < tones.size(); i++) {
			double sum = 0;
			for (int j = 0; j < windowLength; j++) {
				// sum += Math.abs(tones.get(i + j))/windowLength;

				if (i + j < tones.size()) {
					sum += tones.get(i + j);
				}
				// System.out.println(sum);
			}
			sum *= 2 / (double) windowLength; // not sure if/why the 2 is needed
			// System.out.println(sum);
			filtered.add(sum); // add to end of ArrayList
		}
		return filtered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util
	 * .Tone)
	 */
	@Override
	public Tone process(Tone input) {
		//System.out.println("this.windowLength" + this.windowLength);
		return new Tone(filter(input, this.windowLength));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		windowLength = (Integer) getLocal("windowLength");

	}

}
