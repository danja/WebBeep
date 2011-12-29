/**
NOT YET TESTED!!!
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;



/**
 * @author danny
 * 
 */
public class Normalise extends DefaultProcessor {

	public Normalise(String name){
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		if(!isEnabled()) return input;
		return normalise(input,true,true);
	}
	
	public static void main(String[] args) {
		List<Double> data = new ArrayList<Double>();
		int nPoints = 20;
		for (int i = 0; i < nPoints; i++) {
			double x = i * 2 * Math.PI / nPoints;
			double y = Math.sin(x)/3+1;
		//	System.out.println(i + "  " + y);
			data.add(y);
		}
	//	Plotter.plot(data, "Raw", 8, true);
	//	Plotter.plot(normalise(data, true, true),"Normalised", 8, true);
	}
	

	/**
	 * Normalises values in List scaling to peak value = +/- 1 and/or removing
	 * zero offset)
	 * 
	 * @param tones
	 *            input data
	 * @return normalised data
	 */
	public Tone normalise(Tone tones,
			boolean normaliseScale, boolean normaliseOffset) {
		if(tones.size() == 0){
		//	Debug.log("in normalize, tones.size()=0");
			return tones;
		}
		Tone normals = new Tone();
		if (!normaliseScale && !normaliseOffset) {
			return tones; // silly caller
		}
		double min = tones.get(0);
		double max = tones.get(0);
		for (int i = 1; i < tones.size(); i++) {
			if (tones.get(i) < min) {
				min = tones.get(i);
			}
			if (tones.get(i) > max) {
				max = tones.get(i);
			}
		}
		double peakAmplitude = max - min;
//		System.out.println("min="+min);
//		System.out.println("max="+max);
//		System.out.println("peakAmplitude="+peakAmplitude);
		
		double offset = 0;
		double scale = 1;
		
		if (normaliseOffset) {
			offset = (max+min) / 2;
		} 
		if (normaliseScale) {
			scale = 2 / peakAmplitude;
		}
//		System.out.println("offset="+offset);
//		System.out.println("scale="+scale);
//		System.out.println("(max-min)/2="+(max-min)/2);
		
		for (int i = 0; i < tones.size(); i++) {
			normals.add((tones.get(i) - offset)*scale);
		}
		return normals;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
	//	System.out.println("NOON in "+this);
		setEnabled((Boolean)getLocal("on"));
	}
}
