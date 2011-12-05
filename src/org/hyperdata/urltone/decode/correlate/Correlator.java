/**
 * 
 */
package org.hyperdata.urltone.decode.correlate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hyperdata.urltone.WaveMaker;
import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.decode.ChunkDetector;
import org.hyperdata.urltone.decode.PitchFinderGeneral;
import org.hyperdata.urltone.decode.PreProcess;

/**
 * @author danny
 * 
 */
public class Correlator implements PitchFinderGeneral {

	/* (non-Javadoc)
	 * @see org.hyperdata.urltone.decode.PitchFinderGeneral#findPitches(java.util.List)
	 */
	@Override
	public List<Double> findPitches(List<Double> tones) {

		Iterator<Double> freqs = ReferenceTones.tones.keySet().iterator();
		while(freqs.hasNext()){
			double freq = freqs.next();
			List<Double> tone = ReferenceTones.tones.get(freq);
		}
		return null;
	}
	
	public static void main(String[] args) {
		double testFreq = 300;
		List<Double> result = new ArrayList<Double>();
		List<Double> testTone = WaveMaker.makeShapedWaveform(testFreq, 0.99,
				Constants.TONE_DURATION);
		double sampleProportion = 0.8;
		
		int midPoint = testTone.size()/2;
		int start = midPoint-(int)(testTone.size()*sampleProportion/2);
		
		int end = midPoint+(int)(testTone.size()*sampleProportion/2);
	//	System.out.println(start+"   "+end);
	//	System.out.println("original size : "+testTone.size());
		testTone = testTone.subList(start,end);
	//	System.out.println("cropped size : "+testTone.size());
		Plotter.plot(testTone, "Test Tone", "Time", "Amplitude", 2, true);
		double max = 0;
		int maxPoint = 0;
		double min = 0;
		int minPoint = 0;
		double sum = 0;
		
		for (int i = (int) (testFreq / 2); i < testFreq * 2; i++) {
			// 2Hz tone, one cycle every 1/2 seconds
			double duration = 1.0 / (double) i;
			// System.out.println("duration="+duration);
			List<Double> ref = WaveMaker.makeWaveform(i, 0.99, duration);
			if (i == (int) testFreq) {
			//	System.out.println("i=" + i);
			//	System.out.println("duration=" + duration);
						Plotter.plot(ref, "Reference", "Time", "Amplitude", 2, true);
			}
//			long startTime = System.currentTimeMillis();

			double correlation = correlate(testTone, ref);
			
		//	System.out.println("freq=" + i+"   correlation=" + Plotter.roundToSignificantFigures(correlation,2));
		
//			long thisTime = System.currentTimeMillis();
//			System.out.println("time: " + (float)(thisTime - startTime)/1000 + " seconds");
			sum +=Math.abs(correlation);
			result.add(correlation);
			if (correlation > max) {
				max = correlation;
				maxPoint = i;
			}
			if (correlation < min) {
				min = correlation;
				minPoint = i;
			}
		}
		
		// 	public static Plotter plot(List<Double> data, String title, int pointSize, boolean drawLines)
		Plotter plotter = Plotter.plot(result, "Correlation", "Frequency", "Amplitude", 2, true);
//		plotter.addPoint(minPoint- (int) (testFreq / 2), min);
//		plotter.addPoint(maxPoint- (int) (testFreq / 2), max);
//		plotter.highlight(minPoint);
		plotter.highlight((int)testFreq- (int) (testFreq / 2));
//		plotter.highlight(maxPoint);
		
		List<Double> data = PreProcess.normalise(result, true, true);
		data = ChunkDetector.rectify(data);
		// Plotter.plot(data, "Rectified", 3, true);
		System.out.println("min : "+minPoint + "   "+Plotter.roundToSignificantFigures(min,2));
		System.out.println("freq : "+testFreq + "   "+Plotter.roundToSignificantFigures(result.get((int)testFreq- (int) (testFreq / 2)), 2));
		System.out.println("max : "+maxPoint + "    "+Plotter.roundToSignificantFigures(max,2));
		
		System.out.println("sum of abs values : "+ (sum/testFreq * 1.5)); 
		
		int estimate = (minPoint + maxPoint)/2;
		System.out.println("target : "+testFreq + "   estimate : "+estimate);
		double error = Math.abs(estimate  - testFreq);
		System.out.println("error = " + error + ",   " + Plotter.roundToSignificantFigures(100 * error
				/ testFreq,2) + " %");
		
	}

	/**
	 * 
	 * @param tone List to compare with reference
	 * @param reference reference waveform
	 * @return
	 */
	public static double correlate(List<Double> tone, List<Double> reference) {
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
		for (int shift = 0; shift < reference.size(); shift++) {
			
			for (int i = 0; i < tone.size(); i = i + reference.size()) { //
				for (int j = 0; j < reference.size(); j++) {
					// System.out.println();
					// System.out.println("tone.size()="+tone.size());
					// System.out.println("reference.size()="+reference.size());
					// System.out.println("i="+i);
					// System.out.println("j="+j);
					int refPoint = j + shift;
					if (refPoint >= reference.size()) {
						refPoint -= reference.size();
					}
					double ref = reference.get(refPoint);
					int testPoint = i + j;
					if (testPoint >= tone.size()) {
						testPoint = testPoint - tone.size();
					}
					// System.out.println("testPoint="+testPoint);
					double sample = tone.get(testPoint);
					sum += ref * sample;
				}
			}
			if(sum>maxSum){
				maxSum = sum;
			}
			if(sum<minSum){
				minSum = sum;
			}
			
		} // shift
		return maxSum>Math.abs(minSum) ? maxSum : minSum;
	}


}
