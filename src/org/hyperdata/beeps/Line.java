/**
 * 
 */
package org.hyperdata.beeps;


import org.hyperdata.beeps.processors.Clip;
import org.hyperdata.beeps.processors.Distort;
import org.hyperdata.beeps.processors.LFNoisy;
import org.hyperdata.beeps.processors.Noisy;
import org.hyperdata.beeps.processors.Reverb;
import org.hyperdata.beeps.processors.ThroughMP3;
import org.hyperdata.beeps.system.DefaultPipeline;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class Line extends DefaultPipeline {

	public Line(){
		super("Line");
		if(Math.random() > .1){
		ThroughMP3 mp3a = new ThroughMP3 ("Line.throughMP3");
		addProcessor(mp3a);
		
		Distort distort = new Distort("Line.distort");
		distort.setDistortProportion(Math.random()*.4); // 0.5
		addProcessor(distort);
		
		Noisy noisy = new Noisy("Line.noisy");
		noisy.setNoiseProportion(Math.random()*.4); // 0.3
		addProcessor(noisy);
		
		LFNoisy lfNoisy = new LFNoisy("Line.lfNoisy");
		lfNoisy.setNoiseProportion(Math.random()*.2); // 0.2
		addProcessor(lfNoisy);
		
		Reverb reverb = new Reverb("Line.reverb");
		reverb.setLevel(Math.random()*.2); // 0.2
		addProcessor(reverb);
		
		Clip clip = new Clip("Line.clip");
		clip.setClipLevel(1 - Math.random()*.2); // 0.6
		addProcessor(clip);
		
		if(Math.random() > .1){
		ThroughMP3 mp3b = new ThroughMP3 ("Line.throughMP3");
		addProcessor(mp3b);
		}
		}
		
		
		
	}
	
	public static void main(String[] args){
		String infile = "/home/danny/workspace/WebBeep/data/testin.wav";
		String outfile = "/home/danny/workspace/WebBeep/data/line.wav";
		Tone inTones = WavCodec.readTone(infile);
		Line line = new Line();
		
		Tone outTones = line.process(inTones);
		WavCodec.save(outfile, outTones);
	}
}
