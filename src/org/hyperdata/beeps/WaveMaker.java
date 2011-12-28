package org.hyperdata.beeps;

import java.util.*;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.beeps.util.WavCodec;

public final class WaveMaker {

	// private static final int SAMPLE_BUFFER_SIZE = 4096;

	static byte[] buffer; // our internal buffer
	static int bufferSize = 0; // number of samples currently in
								// internal buffer

	// double hz = 440.0 * Math.pow(2, x / 12.0);

	// create a note (sine wave) of the given frequency (Hz), for the given
	// duration (seconds) scaled to the given volume (amplitude)
	// private static double[] note(double hz, double duration, double
	// amplitude) {
	// int N = (int) (WaveMaker.SAMPLE_RATE * duration);
	// double[] a = new double[N + 1];
	// for (int i = 0; i <= N; i++)
	// a[i] = amplitude
	// * Math.sin(2 * Math.PI * i * hz / WaveMaker.SAMPLE_RATE);
	// return a;
	// }

	public static Tone makeDualTone(int noteLow, int noteHigh,
			double duration) {
		return new Tone(makeDualtone(noteLow, noteHigh, duration));
	}
	
	public static Tone makeDualtone(int noteLow, int noteHigh,
			double duration) {

//		System.out.println("WaveMaker freqlow = "+Maps.LOW_FREQ[noteLow]);
//		System.out.println("WaveMaker freqhigh = "+Maps.HIGH_FREQ[noteHigh]);
		Tone dataLow = null;
		Tone dataHigh = null;

//		System.out.println();
//		System.out.println("Maps.LOW_BEATS[noteLow]="+Maps.LOW_BEATS[noteLow]);
//		System.out.println("Maps.HIGH_BEATS[noteHigh]="+Maps.HIGH_BEATS[noteHigh]);
//		System.out.println("Maps.LOW_FREQ[noteLow]="+Maps.LOW_FREQ[noteLow]);
//		System.out.println("Maps.HIGH_FREQ[noteHigh]="+Maps.HIGH_FREQ[noteHigh]);
		
		if (Maps.LOW_BEATS[noteLow] == 1) {
			dataLow = makeShapedWaveform(Maps.LOW_FREQ[noteLow], Constants.AMPLITUDE, duration*2);
		} else {
			dataLow = makeShapedWaveform(Maps.LOW_FREQ[noteLow], Constants.AMPLITUDE, duration);
			dataLow.addAll(makeSilence(duration));
		}
		if (Maps.HIGH_BEATS[noteHigh] == 1) {
			dataHigh = makeShapedWaveform(Maps.HIGH_FREQ[noteHigh], Constants.AMPLITUDE, duration*2);
		} else {
			dataHigh = makeShapedWaveform(Maps.HIGH_FREQ[noteHigh], Constants.AMPLITUDE,duration);
			dataHigh.addAll(makeSilence(duration));
		}
		for (int i = 0; i < Constants.SAMPLE_RATE * duration*2; i++) { // merge/mix
			dataLow.set(i, dataLow.get(i) + dataHigh.get(i)); // ////////////////////////////////////
		}
		return dataLow;
	}

	public static Tone makeShapedWaveform(double freq,double amplitude, double duration) {
		Tone data = makeWaveform(freq, amplitude, duration);
		EnvelopeShaper env = new EnvelopeShaper("WaveMaker.envelopeShaper");
		env.setAttackProportion(Constants.ENCODE_ATTACK_PROPORTION);
		env.setDecayProportion(Constants.ENCODE_DECAY_PROPORTION);		
		return env.process(data);
		
	}
	public static Tone makeWaveform(double freq, double amplitude, double duration) {
		Tone data = new Tone();
		for (int i = 0; i < ((double)Constants.SAMPLE_RATE) * duration; i++) {
			data.add(amplitude * Math.sin(2 * Math.PI * freq * i / (double)Constants.SAMPLE_RATE));
		}
	//	data = EnvelopeShaper.applyEnvelope(data, Constants.ENCODE_ATTACK_PROPORTION, Constants.ENCODE_DECAY_PROPORTION);
		return data;
	}
	
//	public static List<Double> makeWaveform(double freq, double duration) {
//		List<Double> data = new ArrayList<Double>();
//		for (int i = 0; i < ((double)Constants.N_SAMPLES) * duration; i++) {
//			data.add((Constants.AMPLITUDE / 2)
//					* Math.sin(2 * Math.PI * freq * i
//							/ (double)Constants.SAMPLE_RATE));
//		}
//	//	data = EnvelopeShaper.applyEnvelope(data, Constants.ENCODE_ATTACK_PROPORTION, Constants.ENCODE_DECAY_PROPORTION);
//		return data;
//	}

	public static List<Double> makeSilence(double duration) {
		List<Double> data = new ArrayList<Double>();
		for (int i = 0; i < Constants.SAMPLE_RATE * duration; i++) {
			data.add(0.0);
		}
		return data;
	}

	public static void main(String[] args) {

		String filename = "/home/danny/workspace/UTone/data/beep.wav";
		int noteLow = 3;
		int noteHigh = 6;
		List<Double> data = makeDualtone(noteLow, noteHigh, 1);
		WavCodec.save(filename, data);
	}
}