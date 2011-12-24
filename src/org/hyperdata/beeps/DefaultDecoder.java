/**
 * 
 */
package org.hyperdata.beeps;

import java.net.IDN;

import org.hyperdata.beeps.filters.FIRFilter;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pipelines.SplittingProcessor;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Checksum;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameter;
import org.hyperdata.go.parameters.SimpleParameter;

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

		if (Debug.showPlots) {
			Plotter.plot(tones, "in decoder");
		}

		Cropper cropper = new Cropper("Decoder.cropper");
		cropper.setSilenceThreshold(0.14);
		tones = cropper.process(tones);

		// Plotter.plot(tones, "Cropped");
		/*
		 * Decoder.HP_FIR.on = true
Decoder.HP_FIR.window = Blackman
Decoder.HP_FIR.cutoff = 290
Decoder.HP_FIR.npoints = 2573
		 */
		
		FIRProcessor hp = new FIRProcessor("Decoder.HP_FIR");
//		hp.setShape(FIRFilter.HP);
//		hp.setWindow(FIRFilter.BLACKMAN);
//		hp.setFc(290);
//		hp.setnPoints(2573);
		hp.setParameter(new SimpleParameter("Decoder.HP_FIR.shape","HP"));
		hp.setParameter(new SimpleParameter("Decoder.HP_FIR.window","Blackman"));
		hp.setParameter(new SimpleParameter("Decoder.HP_FIR.cutoff",new Double(290)));
		hp.setParameter(new SimpleParameter("Decoder.HP_FIR.npoints",new Integer(2573)));
		hp.initFromParameters();
		hp.initWeights();
		Plotter.plot(tones, "before filter");
		tones = hp.process(tones);
		Plotter.plot(tones, "after filter");
		System.out.println(hp.getShapeName());
		System.out.println(hp.getWindowName());
		System.out.println(hp.getFc());
		System.out.println(hp.getnPoints());
		
		Processor norm1 = new Normalise("Decoder.norm1");
		tones = norm1.process(tones);

		// preprocess
		Chunker chunker = new Chunker("Decoder.chunker");
		chunker.setCropProportion(0.38);
		Chunks chunks = chunker.process(tones);
		// postprocess
		
		
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
