/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hyperdata.beeps.Maps;
import org.hyperdata.beeps.parameters.DefaultParameterized;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class GoertzelPitchFinder  extends DefaultPitchFinder {
	
	private double threshold = 10000;
	private Goertzel goertzel = new Goertzel();

	/**
	 * @param name
	 */
	public GoertzelPitchFinder(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.PitchfinderGeneral#findPitches(org.hyperdata.beeps.util.Tone)
	 * 
	 * 
	 */
	@Override
	public Set<Double> findPitches(Tone tone) {
		Set<Double> pitches = new HashSet<Double>();
		for(int i=0;i<Maps.ALL_FREQS.length;i++){
			double power = goertzel.getPower(tone, Maps.ALL_FREQS[i]);
			
			if(power > threshold){
			//	System.out.println("adding point="+i+"  freq="+Maps.ALL_FREQS[i]+"  power="+power);
				pitches.add(Maps.ALL_FREQS[i]);
			}
		}
		return pitches;
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		threshold = (Double) getLocal("gThreshold");
	}


	/**
	 * @return the threshold
	 */
	public double getThreshold() {
		return this.threshold;
	}


	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}




}
