/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hyperdata.beeps.system.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

/**
 * @author danny
 * 
 */
public class ShellProcess extends DefaultProcessor {

	/**
	 * @param name
	 */
	public ShellProcess(String name) {
		super(name);
	}

	private String wavFilename = "/home/danny/workspace/WebBeep/data/beeps.wav";
	private String mp3Filename = "/home/danny/workspace/WebBeep/data/beeps.mp3";
	private String shellCommand = "ls";

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
		WavCodec.save(wavFilename, input);
		doShell();
		return new Tone(WavCodec.read(mp3Filename));
	}

	protected void doShell() {
		Runtime run = Runtime.getRuntime();
		Process pr;
		try {
			pr = run.exec(shellCommand);

			pr.waitFor();

			BufferedReader buf = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			String line = "";
			while ((line = buf.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.go.parameters.Parameterized#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the outputFilename
	 */
	public String getWavFilename() {
		return this.wavFilename;
	}

	/**
	 * @param outputFilename
	 *            the outputFilename to set
	 */
	public void setWavFilename(String outputFilename) {
		this.wavFilename = outputFilename;
	}

	/**
	 * @return the inputFilename
	 */
	public String getMp3Filename() {
		return this.mp3Filename;
	}

	/**
	 * @param inputFilename
	 *            the inputFilename to set
	 */
	public void setMp3Filename(String inputFilename) {
		this.mp3Filename = inputFilename;
	}

	/**
	 * @return the shellCommand
	 */
	public String getShellCommand() {
		return this.shellCommand;
	}

	/**
	 * @param shellCommand
	 *            the shellCommand to set
	 */
	public void setShellCommand(String shellCommand) {
		this.shellCommand = shellCommand;
	}
}
