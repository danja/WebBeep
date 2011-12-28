/**
 * sadly I had to lose the interface PitchFinderGeneral
 */
package org.hyperdata.beeps.old;

import java.net.IDN;

import org.hyperdata.beeps.ASCIICodec;
import org.hyperdata.beeps.DefaultCodec;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.pitchfinders.FFTPitchFinder;
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
public class FFTDecoder  extends FixedDecoder {

	public FFTDecoder() {
		super();
	}
	
	/**
	 * @param chunks
	 * @return
	 */
	public String chunksToASCII(Chunks chunks) {
		return ASCIICodec.chunksToASCII(chunks, new FFTPitchFinder("Decoder.pitchFinder"));
	}

	public static void main(String[] args) {
		FixedEncoder encoder = new FixedEncoder();
		Tone tones = encoder
				.encode("http://danbri.org/foaf.rdf#danbri"); // "http://danbri.org/foaf.rdf#danbri"
		FFTDecoder decoder = new FFTDecoder();
		System.out.println("Decoded = " + decoder.decode(tones));
	}
}
