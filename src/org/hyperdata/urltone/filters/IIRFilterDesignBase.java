/**
 * Based on material from Digital Audio with Java, Craig Lindley
 * ftp://ftp.prenhall.com/pub/ptr/professional_computer_science.w-022/digital_audio/
 */

package org.hyperdata.urltone.filters;

// Base class for all IIR filters 

public abstract class IIRFilterDesignBase {

	public IIRFilterDesignBase(int frequency, 
							   int sampleRate,
							   double parameter) {
		// Save incoming
		this.frequency = frequency;
		this.sampleRate = sampleRate;
		
		// Damping factor for highpass and lowpass, q for bandpass
		this.parameter = parameter;
	}

	// Given a frequency of interest, calculate radians/sample
	protected double calcRadiansPerSample(double freq) {

		return (2.0 * Math.PI * freq) / sampleRate;
	}

	// Return the radians per sample at the frequency of interest
	protected double getThetaZero() {
		
		return calcRadiansPerSample(frequency);
	}

	// Do the design of the filter. Filter response controlled by
	// just three coefficients: alpha, beta and gamma.
	public abstract void doFilterDesign();
	
	// Print all three IIR coefficients
	public void printCoefficients() {

		System.out.println("Filter Specifications:");
		System.out.println("\tSample Rate: " + sampleRate +
						   ", Frequency: " + frequency + 
						   ", d/q: " + parameter);

		System.out.println("\tAlpha: " + alpha);
		System.out.println("\tBeta: " + beta);
		System.out.println("\tGamma: " + gamma);
	}

	// Return alpha coefficient
	public double getAlpha() {

		return alpha;
	}

	// Return beta coefficient
	public double getBeta() {

		return beta;
	}

	// Return gamma coefficient
	public double getGamma() {

		return gamma;
	}
	
	// Private class data
	protected int frequency;
	protected int sampleRate;
	protected double parameter;
	protected double alpha;
	protected double beta;
	protected double gamma;
}

