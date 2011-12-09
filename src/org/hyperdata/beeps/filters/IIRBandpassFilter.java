/**
 * Based on material from Digital Audio with Java, Craig Lindley
 * ftp://ftp.prenhall.com/pub/ptr/professional_computer_science.w-022/digital_audio/
 */

package org.hyperdata.beeps.filters;

// Optimized IIR bandpass filter with only 4 multiplies per sample
// Used for each band of the graphic equalizer.

public class IIRBandpassFilter extends IIRFilterBase {

	// IIRBandpassFilter class constructor
	// alpha, beta and gamma are precalculated filter coefficients
	// that are passed into this filter element.
	public IIRBandpassFilter(double alpha, double beta, double gamma) {

		super(alpha, beta, gamma);
	}

	// Filter coefficients can also be extracted by passing in 
	// design object.
	public IIRBandpassFilter(IIRBandpassFilterDesign fd) {

		super(fd);
	}

	// Run the filter algorithm
	public void doFilter(short [] inBuffer, double [] outBuffer,
						 int length) {

		for (int index=0; index < length; index++) {

			// Fetch sample
			inArray[iIndex] = (double) inBuffer[index];
			
			// Do indices maintainance
			jIndex = iIndex - 2;
			if (jIndex < 0) jIndex += HISTORYSIZE;
			kIndex = iIndex - 1;
			if (kIndex < 0) kIndex += HISTORYSIZE;

			// Run the difference equation
			double out = outArray[iIndex] = 
				2 * (alpha * (inArray[iIndex] - inArray[jIndex]) + 
				gamma * outArray[kIndex] -
				beta  * outArray[jIndex]);
			
			outBuffer[index] += amplitudeAdj * out;

			iIndex = (iIndex + 1) % HISTORYSIZE;
		}
	}
}

