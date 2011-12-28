/**
 * 
 */
package org.hyperdata.beeps;

import java.util.Iterator;
import java.util.List;

import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.config.Maps;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pitchfinders.PitchFinderGeneral;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Tone;

import java.util.Set;

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
			Tone rightChunk, PitchFinderGeneral finder) {
	
		Processor normalise = new Normalise("CharacterDecoder.normalise");
		try {
			leftChunk = normalise.process(leftChunk);
	
			rightChunk = normalise.process(rightChunk);
		} catch (Exception exception) {
			Debug.verbose("chunksToCharacter fail - " + exception.getMessage());
		}
	
		// Plotter.plot(leftChunk, "leftChunk");
	
		Set<Double> leftFreqs = finder.findPitches(leftChunk);
		Set<Double> rightFreqs = finder.findPitches(rightChunk);

//		System.out.println("leftFreqs = "+leftFreqs);
//		System.out.println("rightFreqs = "+rightFreqs);
		
		String c = CharacterDecoder.decodeChar(leftFreqs, rightFreqs);
		return c;
	}

	/**
	 * @param leftFreqs
	 * @param rightFreqs
	 * @return
	 */
	static String decodeChar(Set<Double> leftFreqs,
			Set<Double> rightFreqs) {
	
		double lowNote;
		double noteA = 440;
		double highNote = 880;
		Iterator<Double> iterator = leftFreqs.iterator();
		if(iterator.hasNext()){
			noteA = iterator.next();
			if(iterator.hasNext()){
			highNote = iterator.next();
			}
		} 
		if (noteA > highNote) {
			lowNote = highNote;
			highNote = noteA;
		} else {
			lowNote = noteA;
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
			Iterator<Double> iteratorR = rightFreqs.iterator();
			double rightNote = iteratorR.next();
					// Maps.findNearestNote(rightFreqs.get(0));
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
			lowValue = Maps.unmapValue(lowNote, beatLow, Maps.LOW_FREQ,
					Maps.LOW_BEATS);
			// System.out.println("got value " + lowValue);
		} catch (Exception exception) {
			Debug.inform("No character matched to lowNote=" + lowNote
					+ " beatLow=" + beatLow);
		}
		try {
			// System.out.println("unmapping high " + highNote + "   "
			// + beatHigh);
			highValue = Maps.unmapValue(highNote, beatHigh, Maps.HIGH_FREQ,
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

}
