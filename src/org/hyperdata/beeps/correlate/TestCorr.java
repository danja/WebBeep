/**
 * 
 */
package org.hyperdata.beeps.correlate;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.WaveMaker;
import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.old.FixedEncoder;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.util.Plotter;

/**
 * @author danny
 *
 */
public class TestCorr {

	public static void main(String[] args){
	//	 double testFreq = 571;
	//	List<Double> result = new ArrayList<Double>();
//		List<Double> testTone = WaveMaker.makeWaveform(testFreq, 0.99,
//				Constants.TONE_DURATION);
		
		
		////////////////////////////////////////////
		 FixedEncoder encoder = new FixedEncoder();
		List<Double> testTone = encoder.encode("o");
		Cropper cropper = new Cropper("TestCorr.cropper");
		int startx = cropper.findStart(testTone,
				Constants.SILENCE_THRESHOLD);
		int endx = cropper.findEnd(testTone,
				Constants.SILENCE_THRESHOLD);
		testTone = testTone.subList(startx, endx);
		
		List<Double> result = correlate(testTone,testTone);
		
		Plotter.plot(result, "Autocorrelation");
		
//	good	[1571.923828125, 290.6982421875]
//		bad		[293.66, 261.63]
		///////////////////////////////////////////////////
		
	}
	
	/**
	 * 
	 * @param tone List to compare with reference
	 * @param reference reference waveform
	 * @return
	 */
	public static List<Double> correlate(List<Double> tone, List<Double> reference) {
		List<Double> result = new ArrayList<Double>();
		// System.out.println("tone.size()="+tone.size());
		// System.out.println("reference.size()="+reference.size());
		if (reference.size() > tone.size()) {
			System.out.println("reference is too big");
			System.exit(1);
		}
		double sum = 0;
		double maxSum = 0;
		double minSum = 0;
		// for(int startPoint =
		// 0;startPoint+reference.size()<tone.size();startPoint +=
		// reference.size()){
		for (int shift = 0; shift < tone.size(); shift++) {
			
			for (int i = 0; i < tone.size(); i = i + reference.size()) { //
				for (int j = 0; j < reference.size(); j++) {
					// System.out.println();
					// System.out.println("tone.size()="+tone.size());
					// System.out.println("reference.size()="+reference.size());
					// System.out.println("i="+i);
					// System.out.println("j="+j);
//					int refPoint = j + shift;
//					if (refPoint >= reference.size()) {
//						refPoint -= reference.size();
//					}
					double ref = reference.get(j);
					
					int testPoint = i + j + shift;
					if (testPoint >= tone.size()) {
						testPoint = testPoint - tone.size();
					}
					// System.out.println("testPoint="+testPoint);
					double sample = tone.get(testPoint);
					sum += ref * sample;
			//		sum += (ref - sample)*(ref - sample); for the fun of it
				}
			}
			if(sum>maxSum){
				maxSum = sum;
			}
			if(sum<minSum){
				minSum = sum;
			}
			result.add(sum);
		} // end shift
		 return result;
		// return maxSum>Math.abs(minSum) ? maxSum : minSum;
	}
}
