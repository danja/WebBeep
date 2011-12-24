/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.List;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterized;

/**
 * @author danny
 *
 */
public class Cropper extends DefaultProcessor {

	private double silenceThreshold = Constants.SILENCE_THRESHOLD;
	
	public Cropper(String name){
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		silenceThreshold = (Double) getLocal("silenceThreshold");
	}
			
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		Debug.inform("DETECTOR - do something with me");
		int start = findStart(input, silenceThreshold);
		int end = findEnd(input, silenceThreshold);
		// System.out.println("cropping");

		try {
			input = new Tone(input.subList(start, end));
		} catch (Exception exception) {
			System.out.println("DETECTOR problem");
			// Plotter.plot(tones, "DETECTOR");
		}
		return input;
	}

	public int findStart(List<Double> tones, double threshold){
		for(int i=0;i<tones.size();i++){
			if(tones.get(i)>threshold) return i;
		}
		System.out.println("START not found");
		return -1;
	}

	public int findEnd(List<Double> tones, double threshold){
		for(int i=tones.size()-1;i>=0;i--){
			if(tones.get(i)>threshold) return i;
		}
		return -1;
	}

	/**
	 * @return the silenceThreshold
	 */
	public double getSilenceThreshold() {
		return this.silenceThreshold;
	}

	/**
	 * @param silenceThreshold the silenceThreshold to set
	 */
	public void setSilenceThreshold(double silenceThreshold) {
		this.silenceThreshold = silenceThreshold;
	}
}
