/**
WRONGMINDED! but maybe useful elsewhere
need tweak to look like version in TestCorr (return List)

 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.decode.Chunker;
import org.hyperdata.beeps.decode.correlate.ReferenceTones;
import org.hyperdata.beeps.encode.Encoder;
import org.hyperdata.beeps.encode.WaveMaker;
import org.hyperdata.beeps.pipelines.DefaultParameterized;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public class Correlator extends DefaultProcessor {
	
	public Correlator(){
		super("Correlator");
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		return process(input);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.urltone.decode.PitchFinderGeneral#findPitches(java.util.List)
	 */
	@Override
	public List<Double> process(List<Double> tones) {

		Map<Double,Double> correlations = new HashMap<Double,Double>();
		
		Iterator<Double> refFreqs = ReferenceTones.tones.keySet().iterator();
		double max = 0;
		while(refFreqs.hasNext()){
			double refFreq = refFreqs.next();
			List<Double> ref = ReferenceTones.tones.get(refFreq);
			double correlate = Math.abs(correlate(tones,ref));
			correlations.put(refFreq, correlate);
			if(correlate > max) max = correlate;
		}
		
		List<Double> pitches = new ArrayList<Double>();
		List<Double> all = new ArrayList<Double>();
		
		Iterator<Double> freqs = correlations.keySet().iterator();
		double gold = 0;
		double goldFreq = 0;
		double silver = 0;
		double silverFreq = 0;
		while(freqs.hasNext()){
			double freq = freqs.next();
			double correlate = correlations.get(freq);
			all.add(correlate);
			if(correlate > gold) {
				gold = correlate;
				goldFreq = freq;
			}
			
			if(correlate > silver && correlate != gold){
				silver = correlate;
				silverFreq = freq;
			}
		}
		Plotter plotter = Plotter.plot(all, "eek", 8);
		pitches.add(goldFreq);
		pitches.add(silverFreq);
		return pitches;
	}
	
	public static void main(String[] args) {
		 double testFreq = 571;
		List<Double> result = new ArrayList<Double>();
		List<Double> testTone = WaveMaker.makeShapedWaveform(testFreq, 0.99,
				Constants.TONE_DURATION);
		
		
		Plotter.plot(testTone, "Test Tone", "Time", "Amplitude", 2, true);
		double max = 0;
		int maxPoint = 0;
		double min = 0;
		int minPoint = 0;
		double sum = 0;
		
		for (int i = 100; i<2000; i++) {
	// 	for (int i = (int) (testFreq / 2); i < testFreq * 2; i++) {		
			
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
		
//		List<Double> data = PreProcess.normalise(result, true, true);
//		data = ChunkDetector.rectify(data);
		// Plotter.plot(data, "Rectified", 3, true);
		System.out.println("min point : "+minPoint + "  value at "+Plotter.roundToSignificantFigures(min,2));
		System.out.println("freq point : "+testFreq + "  value at  "+Plotter.roundToSignificantFigures(result.get((int)testFreq- (int) (testFreq / 2)), 2));
		System.out.println("max point : "+maxPoint + "  value at  "+Plotter.roundToSignificantFigures(max,2));
		
		System.out.println("mean of abs values : "+ Plotter.roundToSignificantFigures(sum/result.size(),2)); 
		
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
	//	 return result;
		return maxSum>Math.abs(minSum) ? maxSum : minSum;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}
}
