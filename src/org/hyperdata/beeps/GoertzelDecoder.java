/**
 * sadly I had to lose the interface PitchFinderGeneral
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.pipelines.DefaultCodec;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.pitchfinders.GoertzelPitchFinder;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Correlator;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.EnvelopeShaper;
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
public class GoertzelDecoder extends DefaultDecoder  {

	public GoertzelDecoder() {
		super();
	}
	
	public String chunksToASCII(Chunks chunks) {
		GoertzelPitchFinder pitchFinder = new GoertzelPitchFinder("Decoder.pitchFinder");
		pitchFinder.setThreshold(10000);
		
		return ASCIICodec.chunksToASCII(chunks, pitchFinder);
	}

	public static void main(String[] args) {
		Encoder encoder = new Encoder();
		Tone tones = encoder
				.encode("a"); // "http://danbri.org/foaf.rdf#danbri"
		Decoder decoder = new GoertzelDecoder();
		System.out.println("Decoded = " + decoder.decode(tones));
	}
}
