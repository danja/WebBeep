/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.ArrayList;
import java.util.List;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.old.FixedEncoder;
import org.hyperdata.beeps.system.DefaultComponent;
import org.hyperdata.beeps.system.DefaultComponentList;
import org.hyperdata.beeps.system.DefaultParameterList;
import org.hyperdata.beeps.system.SplittingProcessor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;
import org.hyperdata.common.describe.DefaultDescriber;

/**
 * @author danny
 * 
 */
public class Chunker extends DefaultComponent implements SplittingProcessor {
	
	public Chunker(String name){
		super(name);
	}
	
	private double cropProportion = Constants.CROP_PROPORTION;

	public void initFromParameters() {
		try {
			cropProportion = (Double) getLocal("cropProportion");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
//		cropLength = (int) (cropProportion
//				* Constants.TONE_DURATION * Constants.SAMPLE_RATE); // was /2
	}
	
	static String IRI = "http://dannyayers.com/stuff"; // "OK" is good!
	static String filename = "./data/filtered.wav";

	public static void main(String[] args) {
		FixedEncoder encoder = new FixedEncoder();
		List<Double> tones = encoder.encode(IRI);
		Cropper cropper = new Cropper("test");
		int start = cropper.findStart(tones, 0.75);
		System.out.println(start);
		WavCodec.save(filename, tones);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.SplittingProcessor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Chunks process(Tone tones) {
		int cropLength = (int) (cropProportion
				* Constants.TONE_DURATION * Constants.SAMPLE_RATE);
		
		Chunks chunks = new Chunks();
		int chunkStart = 0;
		
		while (chunkStart + cropLength < tones.size()-1) {	
			int chunkEnd = chunkStart + cropLength;
			
			Tone chunk = new Tone(tones.subList(chunkStart, chunkEnd)); // without decay section
			chunks.add(chunk);
	//		System.out.println("chunks found="+chunks.size());
			// why /2 below !?
			chunkStart += (int) ((Constants.SILENCE_DURATION + Constants.TONE_DURATION) * Constants.SAMPLE_RATE); // /2
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

	/**
	 * @return the cropProportion
	 */
	public double getCropProportion() {
		return this.cropProportion;
	}

	/**
	 * @param cropProportion the cropProportion to set
	 */
	public void setCropProportion(double cropProportion) {
		this.cropProportion = cropProportion;
	}
	
	public String getURI(){
		return "http://hyperdata.org/beeps/HACK/"+getName();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		String description = DefaultDescriber.getDescription(this);
		description += "<"+getURI()+"> a beep:SplittingProcessor .\n";
		description += "<"+getURI()+"> a beep:Chunker .\n";
		// description += super.describe();
		return description;
	}
}
