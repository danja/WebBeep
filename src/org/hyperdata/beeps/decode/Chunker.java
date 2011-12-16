/**
 * 
 */
package org.hyperdata.beeps.decode;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.encode.Encoder;
import org.hyperdata.beeps.pipelines.DefaultParameterized;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class Chunker extends DefaultParameterized implements SplittingProcessor {
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) REFACTOR!!!
	 * @see org.hyperdata.beeps.pipelines.SplittingProcessor#process(java.util.List)
	 */
	@Override
	public List<List<Double>> process(List<Double> input) {
		int cropLength = (int) (Constants.CROP_PROPORTION
				* Constants.TONE_DURATION * Constants.SAMPLE_RATE / 2);
		List<List<Double>> chunks = Chunker.chunk(input, 0, cropLength);
		return chunks;
	}
	
	public static int findStartThreshold(List<Double> tones, double threshold){
		for(int i=0;i<tones.size();i++){
			if(tones.get(i)>threshold) return i;
		}
		return -1;
	}
	
	public static int findEndThreshold(List<Double> tones, double threshold){
		for(int i=tones.size()-1;i>=0;i--){
			if(tones.get(i)>threshold) return i;
		}
		return -1;
	}


	/**
	 * Splits up continuous series into separate (per-char) blocks
	 * Silence between tones not handled correctly : tone/tone/gap etc
	 * 
	 * @param tones
	 * @return
	 */
	public static List<List<Double>> chunk(List<Double> tones, int startTime,
			int cropLength) {
		// System.out.println("tones.size()=" + tones.size());
		List<List<Double>> chunks = new ArrayList<List<Double>>();
		int chunkStart = startTime;
		
		while (chunkStart + cropLength < tones.size()-1) {	
			int chunkEnd = chunkStart + cropLength;
//			System.out.println("cropLength=" + cropLength);
//			System.out.println("chunkStart=" + chunkStart);
//			System.out.println("chunkEnd=" + chunkEnd);
//			System.out.println();
			
			List<Double> chunk = tones.subList(chunkStart, chunkEnd); // without decay section
			chunks.add(chunk);
	//		System.out.println("chunks found="+chunks.size());
			chunkStart += (int) ((Constants.SILENCE_DURATION + Constants.TONE_DURATION) * Constants.SAMPLE_RATE/2);
		} 
		// UGLY HACK
		if(chunks.size() % 2 != 0){
	//		System.out.println("chunk size = "+chunks.size()+"  so adding dummy");
			List<Double> dummychunk = new ArrayList<Double>();
			for(int i=0;i<cropLength;i++){
				dummychunk.add(0.0);
			}
			chunks.add(dummychunk);
		}
		
		return chunks;
	}

	static String IRI = "http://dannyayers.com/stuff"; // "OK" is good!
	static String filename = "/home/danny/workspace/UTone/data/filtered.wav";

	public static void main(String[] args) {
		Encoder encoder = new Encoder();
		List<Double> tones = encoder.encode(IRI);
		int start = findStartThreshold(tones, 0.75);
		System.out.println(start);
		WavCodec.save(filename, tones);
	}


}
