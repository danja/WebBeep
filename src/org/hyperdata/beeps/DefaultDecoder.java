/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 * everything except the pitch detection bit
 *
 */
public abstract class DefaultDecoder implements Decoder {

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.Decoder#decode(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public String decode(Tone tones) {
		Debug.inform("Decoding");
		
		Normalise norm = new Normalise("Decoder.normalise");
		tones = norm.process(tones);
		
//		EnvelopeShaper envelope = new EnvelopeShaper();
//		envelope.setAttackProportion(Constants.EDGE_WINDOW_PROPORTION);
//		envelope.setDecayProportion(Constants.EDGE_WINDOW_PROPORTION);
//		tones = envelope.process(tones);
		
		// tones = applyPreProcessors(tones);

		if (Debug.showPlots) {
			Plotter.plot(tones, "in decoder");
		}

		Processor cropper = new Cropper("Decoder.cropper");
		tones = cropper.process(tones);

		// Plotter.plot(tones, "Cropped");

		SplittingProcessor chunker = new Chunker("Decoder.chunker");
		Chunks chunks = chunker.process(tones);

		// chunks = applyPostProcessors(chunks);
		
		String ascii = chunksToASCII(chunks);

		try {
			ascii = Checksum.checksum(ascii);
			Debug.debug("ascii=" + ascii);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return IDN.toUnicode(ascii);
	}
}
