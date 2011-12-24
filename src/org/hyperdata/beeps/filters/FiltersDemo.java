/**
 * 
 */
package org.hyperdata.beeps.filters;

import org.hyperdata.beeps.WaveMaker;
import org.hyperdata.beeps.pitchfinders.FFT;
import org.hyperdata.beeps.processors.FIRProcessor;
import org.hyperdata.beeps.processors.Normalise;
import org.hyperdata.beeps.util.Noise;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.SimpleParameter;

/**
 * @author danny
 * 
 */
public class FiltersDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double cutoff = 2900;
		double upperCutoff = 5000;
		
		int nPoints = 2573;

		double duration = 1;
		int step = 100; // 10
		int max = 10000; // 1000
		Tone markers = WaveMaker.makeWaveform(step, 0.9 * (double) step
				/ (double) max, duration);
		for (int i = 0; i < max; i += step) {
			// double val = (double)(i+100)/100.0;

			// double expVal = scaledExp(val, 10, 20000);
			// System.out.println(val+"   "+expVal);
			Tone tone = WaveMaker.makeWaveform(i + step, 0.9 * (double) step
					/ (double) max, duration);
			sumWith(markers, tone);
		}

		Normalise norm0 = new Normalise("norm0");
		markers = norm0.process(markers);

		markers = Noise.whiteTone(duration);
		
		Plotter.plot(markers, "Markers");

		
		
		FFT fft = new FFT(16);
		Tone markersSpectrum = new Tone(fft.doPowerFFT(markers, false));
		markersSpectrum = trim(markersSpectrum);

		Normalise norm1 = new Normalise("norm1");
		markersSpectrum = norm1.process(markersSpectrum);
		Plotter.plot(markersSpectrum, "Markers Spectrum", 4, true);

		// Tone noise = Noise.whiteTone(1.0);

		// Tone noiseSpectrum = new Tone(fft.doPowerFFT(noise, false));
		// Plotter.plot(noiseSpectrum , "Noise");


		FIRProcessor[] filters = new FIRProcessor[4];

		// set up filters
		 filters[0] = new FIRProcessor("Demo.LP_FIR");
		 filters[0].setShape(FIRFilter.LP); 
		 
		 filters[1] = new FIRProcessor("Demo.HP_FIR");
		 filters[1].setShape(FIRFilter.HP); 
		 
		 filters[2] = new FIRProcessor("Demo.BP_FIR");
		 filters[2].setShape(FIRFilter.BP); 
		 
		 filters[3] = new FIRProcessor("Demo.BS_FIR");
		 filters[3].setShape(FIRFilter.BS); 
		 
		 
		 for(int i = 0; i<filters.length;i++){
			 filters[i].setWindow(FIRFilter.BLACKMAN); // Blackman
			 filters[i].setFc(cutoff); // 290
			 filters[i].setnPoints(nPoints); // 2573
			 filters[i].initWeights();
		 }
		 
		 filters[2].setFc2(upperCutoff); 
		 filters[3].setFc2(upperCutoff); 
		 
		 Tone[] tones = new Tone[4];
		 Tone[] spectrums = new Tone[4];
		 
		// run filters
		 for(int i = 0; i<filters.length;i++){
		tones[i] = filters[i].process(markers);
		spectrums[i] = new Tone(fft.doPowerFFT(tones[i], false));
		 }
		 
		 Normalise norm2 = new Normalise("norm2");
		 
		// display
		 for(int i = 0; i<filters.length;i++){
		
			 spectrums[i] = norm2.process(spectrums[i]); // normalise to -1...1
			 spectrums[i] = trim(spectrums[i]); // zoom in on FFT results
		Plotter plotter = Plotter.plot(spectrums[i], "Spectrum after "+filters[i].getShapeName()+" filter",
				4, true);
		 }
	}

	/**
	 * @param markersSpectrum
	 * @return
	 */
	private static Tone trim(Tone tone) {

		return new Tone(tone.subList(50, tone.size() / 2));
	}

	public static Tone sumWith(Tone toneA, Tone toneB) {
		for (int i = 0; i < toneA.size(); i++) {
			toneA.set(i, toneA.get(i) + toneB.get(i));
		}
		return toneA;
	}

	public static double scaledExp(double value, double low, double high) {
		double top = Math.log(high);
		double bottom = Math.log(low);
		double diff = top - bottom;
		double r = value * diff;
		double exponent = bottom + r;
		return (double) Math.exp(exponent);
	}

}
