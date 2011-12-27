/**
 * 
 */
package org.hyperdata.beeps.processors;

import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class ToMP3 extends ShellProcess {

	/**
	 * @param name
	 */
	public ToMP3(String name) {
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
		WavCodec.save(getWavFilename(), input);
		
		// V7 is 100Kbit/s
		setShellCommand("lame -V7 "+getWavFilename() + " " + getMp3Filename());
		doShell();
		
		return input; // unchanged
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
