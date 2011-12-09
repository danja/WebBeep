/**
 * 
 */
package org.hyperdata.beeps.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.hyperdata.beeps.Constants;
import org.hyperdata.beeps.WaveMaker;

/**
 * @author danny
 * 
 */
public class WavCodec {

	/**
	 * Read audio samples from a .wav file and return them as
	 * a double array with values between -1.0 and +1.0.
	 */
	public static double[] readArray(String filename) {
		byte[] data = WavCodec.readByte(filename);
		int N = data.length;
		double[] d = new double[N / 2];
		for (int i = 0; i < N / 2; i++) {
			d[i] = ((short) (((data[2 * i + 1] & 0xFF) << 8) + (data[2 * i] & 0xFF)))
					/ ((double) Constants.MAX_VALUE);
		}
		return d;
	}
	
	/**
	 * 
	 * Read audio samples from a .wav file and return them as
	 * a double array with values between -1.0 and +1.0.
	 * 
	 * @param filename .wav filename
	 * @return List of Doubles with values between -1.0 and +1.0
	 */
	public static  List<Double> read(String filename){
		double[] data = readArray(filename);
		List<Double> signal = new ArrayList<Double>();
		for(int i=0;i< data.length;i++){
			signal.add(data[i]);
		}
		return signal;
	}

	// return data as a byte array
	static byte[] readByte(String filename) {
		byte[] data = null;
		AudioInputStream ais = null;
		try {
			File file = new File(filename);
			ais = AudioSystem.getAudioInputStream(file);
			AudioFormat format = ais.getFormat();
// System.out.println("format = "+format);
			data = new byte[ais.available()];
			ais.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Save the double array as a sound file
	 */
	public static void save(String filename, List<Double> input) {

		// signed, little endian
		AudioFormat format = new AudioFormat(Constants.SAMPLE_RATE, Constants.BITS_PER_SAMPLE, 1,
				true, false);
		byte[] data = new byte[2 * input.size()];
		for (int i = 0; i < input.size(); i++) {
			int temp = (short) (input.get(i).doubleValue() * Constants.MAX_VALUE);
			data[2 * i + 0] = (byte) temp;
			data[2 * i + 1] = (byte) (temp >> 8);
		}

		// save the file
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
