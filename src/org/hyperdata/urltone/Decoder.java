/**
 * 
 */
package org.hyperdata.urltone;

import java.net.IDN;
import java.util.List;

import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Maps;
import org.hyperdata.urltone.decode.ChunkDetector;
import org.hyperdata.urltone.decode.FFTPitchFinder;
import org.hyperdata.urltone.decode.PitchFinderGeneral;
import org.hyperdata.urltone.decode.PreProcess;
import org.hyperdata.urltone.encode.EnvelopeShaper;

/**
 * @author danny
 * 
 */
public class Decoder {

	public static String decode(List<Double> tones) {
		// bandpass

	//	System.out.println("decoding");

		// tones = PreProcess.normalise(tones, true, true);
		// tones = ChunkDetector.rectify(tones);
		// tones = RunningAverage.filter(tones, 100);

		// Plotter.plot(tones);

		int start = ChunkDetector.findStartThreshold(tones,
				Constants.SILENCE_THRESHOLD);
		int end = ChunkDetector.findEndThreshold(tones,
				Constants.SILENCE_THRESHOLD);

		// plotter.addPoint(start, tones.get(start));
		// plotter.addPoint(end, tones.get(end));
	//	System.out.println("cropping");
		tones = tones.subList(start, end);

	//	Plotter.plot(tones, "Cropped");

	//	System.out.println("chunking");
		int cropLength = (int) (Constants.CROP_PROPORTION
				* Constants.TONE_DURATION * Constants.SAMPLE_RATE / 2);
		List<List<Double>> chunks = ChunkDetector.chunk(tones, 0, cropLength);

	//	System.out.println("windowing chunks");
		
		// slope intro/outro of chunk, does it help?
		for (int i = 0; i < chunks.size(); i++) {
			chunks.set(i, EnvelopeShaper.applyEnvelope(chunks.get(i),
					Constants.EDGE_WINDOW_PROPORTION,
					Constants.EDGE_WINDOW_PROPORTION));
		//	Plotter.plot(chunks.get(i), "Chunk " + i);
		}

	//	System.out.println("finding pitches");
	//	System.out.println("chunks.size() = " + chunks.size());
		String ascii = "";
		for (int i = 0; i < chunks.size() - 1; i=i+2) {
		//	System.out.println("CHUNK " + i + " and "+ (i+1));
			List<Double> leftChunk = chunks.get(i);
			leftChunk = PreProcess.normalise(leftChunk, true, true);

			List<Double> rightChunk = chunks.get(i + 1);
			rightChunk = PreProcess.normalise(rightChunk, true, true);

			PitchFinderGeneral finder = new FFTPitchFinder();
		//	Plotter.plot(leftChunk, "leftChunk");
			List<Double> leftFreqs = finder.findPitches(leftChunk);
		//	System.out.println("leftFreqs = " + leftFreqs);

		//	System.out.println("ASCII=" + ascii);
			double lowNote;
			double noteA = findNearestNote(leftFreqs.get(0));
			double highNote = findNearestNote(leftFreqs.get(1));
			if (noteA > highNote) {
				lowNote = highNote;
				highNote = noteA;
			} else {
				lowNote = noteA;
			}

			// List<Beep> leftBeeps = new ArrayList<Beep>();
			// for(int j=0;j<leftFreqs.size();j++){
			// System.out.println("nearestL="+findNearestNote(leftFreqs.get(j)));
			// }

			List<Double> rightFreqs = finder.findPitches(rightChunk);
			// // List<Integer> rightFreqIndices = new ArrayList<Integer>();
		//	System.out.println("rightFreqs = " + rightFreqs);
			for (int j = 0; j < rightFreqs.size(); j++) {
				rightFreqs.set(j, findNearestNote(rightFreqs.get(j)));
			}

			int beatLow = 0;
			int beatHigh = 0;
			if (rightFreqs.size() == 2) { // two long notes
				beatLow = 1;
				beatHigh = 1;
			}
			if (rightFreqs.size() == 0) { // two short notes
				beatLow = 2;
				beatHigh = 2;
			}
			if (rightFreqs.size() == 1) { // one of each
				double rightNote = findNearestNote(rightFreqs.get(0));
				if (rightNote == lowNote) {
					beatLow = 1;
					beatHigh = 2;
				}
				if (rightNote == highNote) {
					beatLow = 2;
					beatHigh = 1;
				}
			}
			int lowValue = -1;
			int highValue = -1;
			try {
//				System.out
//						.println("unmapping low " + lowNote + "   " + beatLow);
				lowValue = unmapValue(lowNote, beatLow, Maps.LOW_FREQ,
						Maps.LOW_BEATS);
	//			System.out.println("got value " + lowValue);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			try {
//				System.out.println("unmapping high " + highNote + "   "
//						+ beatHigh);
				highValue = unmapValue(highNote, beatHigh, Maps.HIGH_FREQ,
						Maps.HIGH_BEATS);
	//			System.out.println("got value " + highValue);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			String c = (new Character((char) (lowValue * 16 + highValue)))
					.toString();
			// System.out.println(c);
			ascii += c;
	//		System.out.println("ascii=" + ascii);
		}

		return IDN.toUnicode(ascii);
	}

	public static int unmapValue(double note, int beat, double[] freqs,
			int[] beats) throws Exception {

		for (int i = 0; i < freqs.length; i++) {
			if (note == freqs[i] && beat == beats[i]) {
				return i;
			}
		}
		throw new Exception("Not Found");
	}

	public static double findNearestNote(double freq) {

		double nearestNote = 0;
		double bestDiff = Math.abs(nearestNote - freq);

		for (int i = 1; i < Maps.ALL_FREQS.length; i++) {
			double current = Maps.ALL_FREQS[i];
			double diff = Math.abs(current - freq);
			if (diff < bestDiff) {
				bestDiff = diff;
				nearestNote = current;
			}
		}
		return nearestNote;
	}

	public static void main(String[] args) {
		List<Double> tones = Encoder.encode("http://danbri.org/foaf.rdf#danbri"); // "http://danbri.org/foaf.rdf#danbri"
		System.out.println(decode(tones));
	}
}
