/**
NOT YET TESTED!!!
 * 
 */
package org.hyperdata.beeps.decode;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.util.Plotter;



/**
 * @author danny
 * 
 */
public class PreProcess {

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
	
	public static List<Double> normalise(List<Double> tones) {
		return normalise(tones,true,true);
	}
	/**
	 * Normalises values in List scaling to peak value = +/- 1 and/or removing
	 * zero offset)
	 * 
	 * @param tones
	 *            input data
	 * @return normalised data
	 */
	public static List<Double> normalise(List<Double> tones,
			boolean normaliseScale, boolean normaliseOffset) {
		List<Double> normals = new ArrayList<Double>();
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
	
	// TODO
	public static void filter(){
//		// Determine cutoff frequency for filters.
//		int cutoffFrequency = (int) (noteTable[noteTable.length - 1] * 1.1);	
//
//		// Design the low pass filter. Cutoff is 2 * 12 db/octave when
//		// two filter sections are used.
//		IIRLowpassFilterDesign lpfd = 
//			new IIRLowpassFilterDesign(cutoffFrequency, DEFAULTSAMPLERATE, 1);
//		lpfd.doFilterDesign();
//
//		// Implement the filter design
//		lowPassShelf = new IIRLowpassFilter(lpfd);
		
	}
}
