/**
 * 
 */
package org.hyperdata.urltone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Maps;
import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.common.WavCodec;
import org.hyperdata.urltone.decode.ChunkDetector;
import org.hyperdata.urltone.decode.FFTPitchFinder;
import org.hyperdata.urltone.decode.PitchFinderGeneral;
import org.hyperdata.urltone.decode.PreProcess;
import org.hyperdata.urltone.encode.EnvelopeShaper;
import org.hyperdata.urltone.filters.RunningAverage;

/**
 * @author danny
 * 
 */
public class Decoder {

	public static String decode(List<Double> tones) {
		// bandpass
		System.out.println("decoding");

		// tones = PreProcess.normalise(tones, true, true);
		// tones = ChunkDetector.rectify(tones);
		// tones = RunningAverage.filter(tones, 100);

	Plotter.plot(tones);

		int start = ChunkDetector.findStartThreshold(tones,
				Constants.SILENCE_THRESHOLD);
		int end = ChunkDetector.findEndThreshold(tones,
				Constants.SILENCE_THRESHOLD);

//		plotter.addPoint(start, tones.get(start));
//		plotter.addPoint(end, tones.get(end));
		System.out.println("cropping");
		tones = tones.subList(start, end);

		Plotter.plot(tones, "Cropped");

		System.out.println("chunking");
		int cropLength = (int) (Constants.CROP_PROPORTION
				* Constants.TONE_DURATION * Constants.SAMPLE_RATE / 2);
		List<List<Double>> chunks = ChunkDetector.chunk(tones, 0, cropLength);

		System.out.println("windowing chunks");
		// slope intro/outro of chunk, does it help?
		for (int i = 0; i < chunks.size(); i++) {
			chunks.set(i, EnvelopeShaper.applyEnvelope(chunks.get(i),
					Constants.EDGE_WINDOW_PROPORTION,
					Constants.EDGE_WINDOW_PROPORTION));
			Plotter.plot(chunks.get(i), "Chunk " + i);
		}

		System.out.println("finding pitches");
		for (int i = 0; i < chunks.size()-1; i++) {
			List<Double> leftChunk = chunks.get(i);
			leftChunk = PreProcess.normalise(leftChunk, true, true);
			
			List<Double> rightChunk = chunks.get(i+1);
			rightChunk = PreProcess.normalise(rightChunk, true, true);

			PitchFinderGeneral finder = new FFTPitchFinder();

			List<Double> leftFreqs = finder.findPitches(leftChunk);
			List<Integer> leftFreqIndices = new ArrayList<Integer>();
			System.out.println("leftFreqs = "+leftFreqs);
			
			List<Double> rightFreqs = finder.findPitches(rightChunk);
			List<Integer> rightFreqIndices = new ArrayList<Integer>();
			System.out.println("rightFreqs = "+rightFreqs);
		}

		return "qwe";
	}
	
//	public List<Integer> getIndices(List<Double> freqs){	
////		double[] notes;
////		int[] beats; 
//		for(int j=0;j<freqs.size();j++){
//			double freq = freqs.get(j);
//			if(Maps.MIN_LOW_FREQ < freq && freq < Maps.MAX_LOW_FREQ){
//				
//			}
//			if(Maps.MIN_HIGH_FREQ < freq && freq < Maps.MAX_HIGH_FREQ){
//				// leftFreqIndices.add(lookupIndex(freq, Maps.HIGH_FREQ));
//			}	
//		}
//	}

	public static void main(String[] args) {
		List<Double> tones = Encoder.encode("a");
		decode(tones);
	}
}
