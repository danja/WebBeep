/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.processors.FFTPitchFinder;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class CharacterDecoder {

	/**
	 * @param leftChunk
	 * @param rightChunk
	 * @return
	 */
	static String chunksToCharacter(Tone leftChunk,
			Tone rightChunk, Processor finder) {
	
		Processor normalise = new Normalise();
		try {
			leftChunk = normalise.process(leftChunk);
	
			rightChunk = normalise.process(rightChunk);
		} catch (Exception exception) {
			Debug.verbose("chunksToCharacter fail - " + exception.getMessage());
		}
	
		// Plotter.plot(leftChunk, "leftChunk");
	
		List<Double> leftFreqs = finder.process(leftChunk);
		List<Double> rightFreqs = finder.process(rightChunk);
	
		String c = CharacterDecoder.decodeChar(leftFreqs, rightFreqs);
		return c;
	}

	/**
	 * @param leftFreqs
	 * @param rightFreqs
	 * @return
	 */
	static String decodeChar(List<Double> leftFreqs,
			List<Double> rightFreqs) {
	
		double lowNote;
		double noteA = 440;
		double highNote = 880;
		try {
			noteA = CharacterDecoder.findNearestNote(leftFreqs.get(0));
			highNote = CharacterDecoder.findNearestNote(leftFreqs.get(1));
		} catch (Exception exception) {
			Debug.error("Index out of range in Decoder.decodeChar");
			Debug.error("leftFreqs.size() = " + leftFreqs.size());
			Debug.error("rightFreqs.size() = " + rightFreqs.size());
		}
		if (noteA > highNote) {
			lowNote = highNote;
			highNote = noteA;
		} else {
			lowNote = noteA;
		}
	
		// System.out.println("rightFreqs = " + rightFreqs);
		for (int j = 0; j < rightFreqs.size(); j++) {
			rightFreqs.set(j, CharacterDecoder.findNearestNote(rightFreqs.get(j)));
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
			double rightNote = CharacterDecoder.findNearestNote(rightFreqs.get(0));
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
			// System.out
			// .println("unmapping low " + lowNote + "   " + beatLow);
			lowValue = CharacterDecoder.unmapValue(lowNote, beatLow, Maps.LOW_FREQ,
					Maps.LOW_BEATS);
			// System.out.println("got value " + lowValue);
		} catch (Exception exception) {
			Debug.inform("No character matched to lowNote=" + lowNote
					+ " beatLow=" + beatLow);
		}
		try {
			// System.out.println("unmapping high " + highNote + "   "
			// + beatHigh);
			highValue = CharacterDecoder.unmapValue(highNote, beatHigh, Maps.HIGH_FREQ,
					Maps.HIGH_BEATS);
			// System.out.println("got value " + highValue);
		} catch (Exception exception) {
			Debug.inform("No character matched to highNote=" + highNote
					+ " beatHigh=" + beatHigh);
		}
		String c = (new Character((char) (lowValue * 16 + highValue)))
				.toString();
		return c;
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

	public static int unmapValue(double note, int beat, double[] freqs,
			int[] beats) throws Exception {
		// System.out.println(beat);
		for (int i = 0; i < freqs.length; i++) {
			if (note == freqs[i] && beat == beats[i]) {
				return i;
			}
		}
		throw new Exception("Not Found");
	}

}
