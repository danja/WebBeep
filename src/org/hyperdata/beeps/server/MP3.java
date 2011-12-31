/**
 * 
 */
package org.hyperdata.beeps.server;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 *
 */
public class MP3 extends Shelley {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util
	 * .Tone)
	 */

	public void mp3(Tone input) {
		WavCodec.save(getWavFilename(), input);
		setShellCommand("lame "+Constants.LAME+" "+getWavFilename() + " " + getMp3Filename());
		doShell();
	}
	
	public Tone fromMp3() {
		setShellCommand("lame --decode "+getMp3Filename() + " " + getWavFilename());
		// System.out.println("DOING SHELL : \n"+getShellCommand());
		doShell();
		return new Tone(WavCodec.read(getWavFilename()));
	}

}
