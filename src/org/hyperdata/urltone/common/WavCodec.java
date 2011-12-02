/**
 * 
 */
package org.hyperdata.urltone.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.hyperdata.urltone.WaveMaker;

/**
 * @author danny
 * 
 */
public class WavCodec {

	/**
	 * Read audio samples from a file (in .wav or .au format) and return them as
	 * a double array with values between -1.0 and +1.0.
	 */
	public static double[] read(String filename) {
		byte[] data = WavCodec.readByte(filename);
		int N = data.length;
		double[] d = new double[N / 2];
		for (int i = 0; i < N / 2; i++) {
			d[i] = ((short) (((data[2 * i + 1] & 0xFF) << 8) + (data[2 * i] & 0xFF)))
					/ ((double) Constants.MAX_VALUE);
		}
		return d;
	}

	// return data as a byte array
	static byte[] readByte(String filename) {
		byte[] data = null;
		AudioInputStream ais = null;
		try {
			URL url = WaveMaker.class.getResource(filename);
			ais = AudioSystem.getAudioInputStream(url);
			data = new byte[ais.available()];
			ais.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Save the double array as a sound file (using .wav or .au format).
	 */
	// public static void save(String filename, double[] input) {
	//
	// // assumes 44,100 samples per second
	// // use 16-bit audio, mono, signed PCM, little Endian
	// AudioFormat format = new AudioFormat(WaveMaker.SAMPLE_RATE, 16, 1, true,
	// false);
	// byte[] data = new byte[2 * input.length];
	// for (int i = 0; i < input.length; i++) {
	// int temp = (short) (input[i] * WaveMaker.MAX_VALUE);
	// data[2 * i + 0] = (byte) temp;
	// data[2 * i + 1] = (byte) (temp >> 8);
	// }

	public static void save(String filename, List<Double> input) {

		// assumes 44,100 samples per second
		// use 16-bit audio, mono, signed PCM, little Endian
		AudioFormat format = new AudioFormat(Constants.SAMPLE_RATE, 16, 1,
				true, false);
		byte[] data = new byte[2 * input.size()];
		for (int i = 0; i < input.size(); i++) {
			int temp = (short) (input.get(i).doubleValue() * Constants.MAX_VALUE);
			data[2 * i + 0] = (byte) temp;
			data[2 * i + 1] = (byte) (temp >> 8);
		}

		// now save the file
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			AudioInputStream ais = new AudioInputStream(bais, format,
					input.size());
			if (filename.endsWith(".wav") || filename.endsWith(".WAV")) {
				AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(
						filename));
			} else if (filename.endsWith(".au") || filename.endsWith(".AU")) {
				AudioSystem.write(ais, AudioFileFormat.Type.AU, new File(
						filename));
			} else {
				throw new RuntimeException("File format not supported: "
						+ filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
