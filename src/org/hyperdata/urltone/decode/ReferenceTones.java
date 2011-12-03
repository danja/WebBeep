/**
 * 
 */
package org.hyperdata.urltone.decode;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.urltone.WaveMaker;
import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Maps;

/**
 * @author danny
 *
 */
public class ReferenceTones {

	private static List<List<Double>> lowReferences = null;
	private static List<List<Double>> highReferences = null;
	
	public static List<List<Double>> getLowReferences(){ // lazy
		if(lowReferences == null){
			lowReferences = generateReferences(Maps.LOW_FREQ);
		}
		return lowReferences;
	}
	
	public static List<List<Double>> getHighReferences(){ // lazy
		if(highReferences == null){
			highReferences = generateReferences(Maps.HIGH_FREQ);
		}
		return highReferences;
	}
	
	public static List<List<Double>> generateReferences(double[] map){
		List<List<Double>> references = new ArrayList<List<Double>>();
		for(int i=0;i<Maps.LOW_FREQ.length;i++){
			List<Double> tone = WaveMaker.makeWaveform(i, Constants.TONE_DURATION,
					map);
			references.add(tone);
		}
		return references;
	}
	
	

}
