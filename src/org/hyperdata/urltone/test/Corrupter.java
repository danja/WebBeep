/**
 * 
 */
package org.hyperdata.urltone.test;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.urltone.decode.Detector;

/**
 * @author danny
 *
 */
public class Corrupter {

	public static List<Double> CorruptLF(List<Double> tones) {
		List<Double> noisy = makeNoise(tones.size());
		noisy = Detector.filter(noisy);
		for(int i=0;i<noisy.size();i++){
			noisy.set(i, noisy.get(i) * tones.get(i));
		}
		return noisy;
	}
	
	public static  List<Double> makeNoise(int nValues){
		List<Double> noise = new ArrayList<Double>();
		for(int i=0;i<nValues;i++){
			noise.add(Math.random()); // 0 <= rnd < 1
		}
		return noise;
	}
}
