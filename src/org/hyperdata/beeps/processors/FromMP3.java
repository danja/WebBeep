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
public class FromMP3 extends ShellProcess {

	/**
	 * @param name
	 */
	public FromMP3(String name) {
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
	public Tone process(Tone input) { // input is ignored
		if(!isEnabled()) return input;
		setShellCommand("lame --decode "+getMp3Filename() + " " + getWavFilename());
		doShell();
		
		return new Tone(WavCodec.read(getWavFilename()));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
