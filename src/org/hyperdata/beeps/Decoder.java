/**
 * sadly I had to lose the interface PitchFinderGeneral
 */
package org.hyperdata.beeps;

import java.net.IDN;

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
		
		String ascii = ASCIICodec.chunksToASCII(chunks, new FFTPitchFinder());

		try {
			ascii = Checksum.checksum(ascii);
			Debug.debug("ascii=" + ascii);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return IDN.toUnicode(ascii);
	}

	public static void main(String[] args) {
		Encoder encoder = new Encoder();
		Tone tones = encoder
				.encode("http://danbri.org/foaf.rdf#danbri"); // "http://danbri.org/foaf.rdf#danbri"
		Decoder decoder = new Decoder();
		System.out.println("Decoded = " + decoder.decode(tones));
	}
}
