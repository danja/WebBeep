/**
 * 
 */
package org.hyperdata.beeps;


import org.hyperdata.beeps.pipelines.DefaultPipeline;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.processors.Clip;
import org.hyperdata.beeps.processors.Distort;
import org.hyperdata.beeps.processors.LFNoisy;
import org.hyperdata.beeps.processors.Noisy;
import org.hyperdata.beeps.processors.Reverb;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class Line extends DefaultPipeline {

	public Line(){
		Distort distort = new Distort("Line.distort");
		distort.setDistortProportion(0.4);
		addProcessor(distort);
		
		Noisy noisy = new Noisy("Line.noisy");
		noisy.setNoiseProportion(0.1);
		addProcessor(noisy);
		
		LFNoisy lfNoisy = new LFNoisy("Line.lfNoisy");
		lfNoisy.setNoiseProportion(0.1);
		addProcessor(lfNoisy);
		
		Reverb reverb = new Reverb("Line.reverb");
		reverb.setLevel(0.1);
		addProcessor(reverb);
		
		Clip clip = new Clip("Line.clip");
		clip.setClipLevel(0.6);
		addProcessor(clip);
		
	}
	
	public static void main(String[] args){
		String infile = "/home/danny/workspace/WebBeep/data/testin.wav";
		String outfile = "/home/danny/workspace/WebBeep/data/line.wav";
		Tone inTones = WavCodec.readTone(infile);
		Line line = new Line();
		
		Tone outTones = line.applyProcessors(inTones);
		WavCodec.save(outfile, outTones);
	}
}
