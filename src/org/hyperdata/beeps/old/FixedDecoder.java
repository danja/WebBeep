/**
 * 
 */
package org.hyperdata.beeps.old;

import java.net.IDN;

import org.hyperdata.beeps.Decoder;
import org.hyperdata.beeps.config.Debug;
import org.hyperdata.beeps.filters.FIRFilter;
import org.hyperdata.beeps.parameters.SimpleParameter;
import org.hyperdata.beeps.processors.Chunker;
import org.hyperdata.beeps.processors.Cropper;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.system.DefaultParameter;
import org.hyperdata.beeps.system.Processor;
import org.hyperdata.beeps.system.SplittingProcessor;
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
public abstract class FixedDecoder implements Decoder {

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
		cropper.setSilenceThreshold(0.65);
		tones = cropper.process(tones);
		
		tones = norm.process(tones);

	//	 Plotter.plot(tones, "Cropped");
		
		// high pass
		FIRProcessor hp = new FIRProcessor("Decoder.HP_FIR");
		hp.setShape(FIRFilter.HP);
		hp.setWindow(FIRFilter.BLACKMAN);
		hp.setFc(188);
		hp.setnPoints(219);
		hp.initWeights();
	//	Plotter.plot(tones, "before filter");
		tones = hp.process(tones);
	//	Plotter.plot(tones, "after filter");
	
		tones = norm.process(tones);

		// low pass 1
		FIRProcessor lp1 = new FIRProcessor("Decoder.LP_FIR1");
		lp1.setShape(FIRFilter.LP);
		lp1.setWindow(FIRFilter.BLACKMAN);
		lp1.setFc(5493);
		lp1.setnPoints(529);
		lp1.initWeights();
//		Plotter.plot(tones, "before filter");
		tones = lp1.process(tones);
//		Plotter.plot(tones, "after filter");
	
		tones = norm.process(tones);
		
		// low pass 2
		FIRProcessor lp2 = new FIRProcessor("Decoder.LP_FIR2");
		lp2.setShape(FIRFilter.LP);
		lp2.setWindow(FIRFilter.HAMMING);
		lp2.setFc(10723);
		lp2.setnPoints(192);
		lp2.initWeights();
	//	Plotter.plot(tones, "before filter");
		tones = lp2.process(tones);
	//	Plotter.plot(tones, "after filter");
		tones = norm.process(tones);
		
		// preprocess ^^^
		Chunker chunker = new Chunker("Decoder.chunker");
		chunker.setCropProportion(0.58);
		Chunks chunks = chunker.process(tones);
		// postprocess vvv
//		for(int i=0;i<chunks.size();i++){
//			Plotter.plot(chunks.get(i), "chunk");
//		}
		
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
