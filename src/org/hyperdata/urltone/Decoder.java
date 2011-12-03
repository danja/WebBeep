/**
 * 
 */
package org.hyperdata.urltone;

import java.util.List;

import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Plotter;
import org.hyperdata.urltone.common.WavCodec;
import org.hyperdata.urltone.decode.ChunkDetector;

/**
 * @author danny
 *
 */
public class Decoder {
	
	public static String decode(List<Double> tones) {
		// bandpass
		
		// CROP!!
		
		// locate start
		int startTime = ChunkDetector.findStartThreshold(tones, Constants.START_LEVEL);
		System.out.println("START="+startTime);
		
		int cropLength = (int)(Constants.CROP_PROPORTION * Constants.TONE_DURATION * Constants.SAMPLE_RATE);
		List<List<Double>> chunks = ChunkDetector.chunk(tones, startTime, cropLength);
		
		for(int i=0;i<chunks.size();i++){
			Plotter.plot(chunks.get(i), "N "+i);
		}
		// foreach
		// normalise
		// decode
		
		return "qwe";
	}
	
	public static void main(String[] args) {
		List<Double> tones = Encoder.encode("aA");
		decode(tones);
	}
}
