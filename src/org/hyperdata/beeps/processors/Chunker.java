/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.Encoder;
import org.hyperdata.beeps.pipelines.DefaultParameterized;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class Chunker extends DefaultParameterized implements SplittingProcessor {
	
	private double cropProportion = Constants.CROP_PROPORTION;
	
	int cropLength = (int) (cropProportion
			* Constants.TONE_DURATION * Constants.SAMPLE_RATE / 2);
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		cropProportion = (Double) parameters.get("cropProportion");
		cropLength = (int) (cropProportion
				* Constants.TONE_DURATION * Constants.SAMPLE_RATE / 2);
	}
	
	static String IRI = "http://dannyayers.com/stuff"; // "OK" is good!
	static String filename = "/home/danny/workspace/UTone/data/filtered.wav";

	public static void main(String[] args) {
		Encoder encoder = new Encoder();
		List<Double> tones = encoder.encode(IRI);
		int start = Cropper.findStartThreshold(tones, 0.75);
		System.out.println(start);
		WavCodec.save(filename, tones);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.SplittingProcessor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Chunks process(Tone tones) {
		Chunks chunks = new Chunks();
		int chunkStart = 0;
		
		while (chunkStart + cropLength < tones.size()-1) {	
			int chunkEnd = chunkStart + cropLength;
			
			Tone chunk = new Tone(tones.subList(chunkStart, chunkEnd)); // without decay section
			chunks.add(chunk);
	//		System.out.println("chunks found="+chunks.size());
			chunkStart += (int) ((Constants.SILENCE_DURATION + Constants.TONE_DURATION) * Constants.SAMPLE_RATE/2);
		} 
		// UGLY HACK
		if(chunks.size() % 2 != 0){
	//		System.out.println("chunk size = "+chunks.size()+"  so adding dummy");
			Tone dummychunk = new Tone();
			for(int i=0;i<cropLength;i++){
				dummychunk.add(0.0);
			}
			chunks.add(dummychunk);
		}
		
		return chunks;
	}


}
