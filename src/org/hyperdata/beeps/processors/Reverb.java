/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * crude reverb simulation
 * 
 * trying to handle scale here didn't really work, so post-normalising
 * 
 * @author danny
 * 
 */
public class Reverb extends DefaultProcessor {

	/**
	 * @return the level
	 */
	public double getLevel() {
		return this.level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(double level) {
		this.level = level;
	}

	/**
	 * @return the reflections
	 */
	public int getReflections() {
		return this.reflections;
	}

	/**
	 * @param reflections the reflections to set
	 */
	public void setReflections(int reflections) {
		this.reflections = reflections;
	}

	/**
	 * @return the walls
	 */
	public double[] getWalls() {
		return this.walls;
	}

	/**
	 * @param walls the walls to set
	 */
	public void setWalls(double[] walls) {
		this.walls = walls;
	}

	/**
	 * @return the wallFactor
	 */
	public double[] getWallFactor() {
		return this.wallFactor;
	}

	/**
	 * @param wallFactor the wallFactor to set
	 */
	public void setWallFactor(double[] wallFactor) {
		this.wallFactor = wallFactor;
	}

	static final double speedOfSound = 343;

	double level = 0.2;
	int reflections = 5;

	/*
	 * wall is 300m away, delay is approx 1 sec
	 * 30m, 0.1
	 * 3m, 0.01
	 */
	double[] walls = { 3, 5, 7, 0.25 }; // metres away
	double[] wallFactor = { 0.5, 0.3, 0.1, 0.4 };

	/**
	 * @param name
	 */
	public Reverb(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util
	 * .Tone)
	 */
	@Override
	public Tone process(Tone input) {
		if(!isEnabled()) return input;

		Tone output = new Tone();

		for (int reflection = 0; reflection < reflections; reflection++) {
			if (reflection != 0) {
				input = new Tone(output);
				output = new Tone();
			}
		//	System.out.println("reflecting");
			for (int i = 0; i < input.size(); i++) {
				double out = input.get(i);

				for (int j = 0; j < walls.length; j++) {

					// System.out.println((int)(Constants.SAMPLE_RATE *walls[j] / speedOfSound));
					int delay = (int) (Constants.SAMPLE_RATE * walls[j] / speedOfSound);
					if (i - delay >0) {
						out += level * wallFactor[j] * input.get(i - delay);
					}
				}
				output.add(out);
			}
		}
		Processor normalise = new Normalise("in reverb");
		output = normalise.process(output);
		return output;
	}

//	public double getScale() {
//		double sum = 1;
//		for (int i = 0; i < walls.length; i++) {
//			sum += wallFactor[i];
//		}
//		return 1 / sum;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		setEnabled((Boolean)  getLocal("on"));

	}
	
	public static void main(String[] args){
		String infile = "/home/danny/workspace/WebBeep/data/testin.wav";
		String outfile = "/home/danny/workspace/WebBeep/data/reverby.wav";
		Tone inTones = WavCodec.readTone(infile);
		Reverb reverb = new Reverb("test");
		Tone outTones = reverb.process(inTones);
		WavCodec.save(outfile, outTones);
	}

}
