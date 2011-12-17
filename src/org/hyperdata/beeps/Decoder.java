/**
 * sadly I had to lose the interface PitchFinderGeneral
 */
package org.hyperdata.beeps;

import java.net.IDN;
import java.util.List;

import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Correlator;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.EnvelopeShaper;
import org.hyperdata.beeps.processors.FFTPitchFinder;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 * Sequence of processors and the their parameters are hardcoded
 */
public class Decoder  {

	public Decoder() {
		super();
	}

	public String decode(Tone tones) {
		Debug.inform("Decoding");
		
		Normalise norm = new Normalise();
		tones = norm.process(tones);
		
//		EnvelopeShaper envelope = new EnvelopeShaper();
//		envelope.setAttackProportion(Constants.EDGE_WINDOW_PROPORTION);
//		envelope.setDecayProportion(Constants.EDGE_WINDOW_PROPORTION);
//		tones = envelope.process(tones);
		
		// tones = applyPreProcessors(tones);

		if (Debug.showPlots) {
			Plotter.plot(tones, "in decoder");
		}

		Processor cropper = new Cropper();
		tones = cropper.process(tones);

		// Plotter.plot(tones, "Cropped");

		SplittingProcessor chunker = new Chunker();
		Chunks chunks = chunker.process(tones);

		// chunks = applyPostProcessors(chunks);
		
		String ascii = ASCIICodec.chunksToASCII(chunks);

		try {
			ascii = Checksum.checksum(ascii);
			Debug.debug("ascii=" + ascii);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return IDN.toUnicode(ascii);
	}

	/**
	 * @param leftChunk
	 * @param rightChunk
	 * @return
	 */
	static String chunksToCharacter(Tone leftChunk,
			Tone rightChunk) {

		Processor normalise = new Normalise();
		try {
			leftChunk = normalise.process(leftChunk);

			rightChunk = normalise.process(rightChunk);
		} catch (Exception exception) {
			Debug.verbose("chunksToCharacter fail - " + exception.getMessage());
		}

		// ////////////////////////////////////////////////////////////////////////////////

		Processor finder = new FFTPitchFinder();

		// Plotter.plot(leftChunk, "leftChunk");

		List<Double> leftFreqs = finder.process(leftChunk);
		List<Double> rightFreqs = finder.process(rightChunk);

		String c = decodeChar(leftFreqs, rightFreqs);
		return c;
	}

	/**
	 * @param leftFreqs
	 * @param rightFreqs
	 * @return
	 */
	private static String decodeChar(List<Double> leftFreqs,
			List<Double> rightFreqs) {

		double lowNote;
		double noteA = 440;
		double highNote = 880;
		try {
			noteA = findNearestNote(leftFreqs.get(0));
			highNote = findNearestNote(leftFreqs.get(1));
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
			// System.out
			// .println("unmapping low " + lowNote + "   " + beatLow);
			lowValue = unmapValue(lowNote, beatLow, Maps.LOW_FREQ,
					Maps.LOW_BEATS);
			// System.out.println("got value " + lowValue);
		} catch (Exception exception) {
			Debug.inform("No character matched to lowNote=" + lowNote
					+ " beatLow=" + beatLow);
		}
		try {
			// System.out.println("unmapping high " + highNote + "   "
			// + beatHigh);
			highValue = unmapValue(highNote, beatHigh, Maps.HIGH_FREQ,
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
		Encoder encoder = new Encoder();
		Tone tones = encoder
				.encode("http://danbri.org/foaf.rdf#danbri"); // "http://danbri.org/foaf.rdf#danbri"
		Decoder decoder = new Decoder();
		System.out.println("Decoded = " + decoder.decode(tones));
	}
}
