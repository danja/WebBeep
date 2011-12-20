/**
 * 
 */
package org.hyperdata.beeps;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.goertzel.Goertzel;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterized;

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
	public List<Double> findPitches(Tone tone) {
		List<Double> pitches = new ArrayList<Double>();

		for(int i=0;i<Maps.LOW_FREQ.length;i++){
			double power = goertzel.getPower(tone, Maps.LOW_FREQ[i]);
			
			if(power > threshold){
				System.out.println("adding low freq="+Maps.LOW_FREQ[i]+"  power="+power);
				pitches.add(Maps.LOW_FREQ[i]);
			}
		}
		
		for(int i=0;i<Maps.HIGH_FREQ.length;i++){
			double power = goertzel.getPower(tone, Maps.HIGH_FREQ[i]);
			
			if(power > threshold){
				System.out.println("adding high freq="+Maps.HIGH_FREQ[i]+"  power="+power);
				pitches.add(Maps.HIGH_FREQ[i]);
			}
		}
		return pitches;
	}


	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
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
