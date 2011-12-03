/**
 * 
 */
package org.hyperdata.urltone.decode;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.urltone.Encoder;
import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.WavCodec;

/**
 * @author danny
 * 
 *         rectifier+ simple moving-average filter
 * 
 *         used to locate start of tone
 */
public class ChunkDetector {

	static double WINDOW_TIME = 0.04; // seconds

	/** make all values positive
	 * 
	 * @param tones input signal
	 * @return rectified signal
	 */
	public static List<Double> rectify(List<Double> tones) {

		List<Double> rectified = new ArrayList<Double>();

		for (int i = 0; i < tones.size();i++ ) {
			double sum = 0;
			rectified.add(Math.abs(tones.get(i)));
			}
		return rectified;
	}
	
	public static int findStartThreshold(List<Double> tones, double threshold){
		for(int i=0;i<tones.size();i++){
			if(tones.get(i)>threshold) return i;
		}
		return -1;
	}
	
	public static int findEndThreshold(List<Double> tones, double threshold){
		for(int i=tones.size();i>0;i--){
			if(tones.get(i)>threshold) return i;
		}
		return -1;
	}


	/**
	 * Splits up continuous series into separate (per-char) blocks
	 * 
	 * @param tones
	 * @return
	 */
	public static List<List<Double>> chunk(List<Double> tones, int startTime,
			int cropLength) {
		List<List<Double>> chunks = new ArrayList<List<Double>>();
		int chunkStart = startTime;
		while (chunkStart < tones.size()) {
			int chunkEnd = chunkStart
					+ (int) (Constants.TONE_DURATION * Constants.SAMPLE_RATE);
			List<Double> chunk = tones.subList(chunkStart, chunkStart
					+ cropLength); // without decay section
			System.out.println("chunkStart=" + chunkStart);
			chunks.add(chunk);
			chunkStart = chunkEnd + 1;
		}
		return chunks;
	}

	static String IRI = "http://dannyayers.com/stuff"; // "OK" is good!
	static String filename = "/home/danny/workspace/UTone/data/filtered.wav";

	public static void main(String[] args) {
		List<Double> tones = Encoder.encode(IRI);
		int start = findStartThreshold(tones, 0.75);
		System.out.println(start);
		WavCodec.save(filename, tones);
	}
}
