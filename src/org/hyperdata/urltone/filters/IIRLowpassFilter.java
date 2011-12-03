/**
 * Based on material from Digital Audio with Java, Craig Lindley
 * ftp://ftp.prenhall.com/pub/ptr/professional_computer_science.w-022/digital_audio/
 */

package org.hyperdata.urltone.filters;

// Optimized IIR lowpass filter used as shelving EQ

public class IIRLowpassFilter extends IIRFilterBase {

	// IIRLowpassFilter class constructor
	// alpha, beta and gamma are precalculated filter coefficients
	// that are passed into this filter element.
	public IIRLowpassFilter(double alpha, double beta, double gamma) {

		super(alpha, beta, gamma);
	}

	// Filter coefficients can also be extracted by passing in 
	// design object.
	public IIRLowpassFilter(IIRLowpassFilterDesign fd) {

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

			// Run the lowpass difference equation
			double out = outArray[iIndex] = 
				2.0 * 
				(alpha * (inArray[iIndex] + (2 * inArray[kIndex]) + inArray[jIndex]) +
				 gamma * outArray[kIndex] -
				 beta  * outArray[jIndex]);
			
			outBuffer[index] += amplitudeAdj * out;

			iIndex = (iIndex + 1) % HISTORYSIZE;
		}
	}
	
	public void doFilterNoSum(short [] inBuffer, double [] outBuffer,
						 int length) {

		for (int index=0; index < length; index++) {

			// Fetch sample
			inArray[iIndex] = (double) inBuffer[index];
			
			// Do indices maintainance
			jIndex = iIndex - 2;
			if (jIndex < 0) jIndex += HISTORYSIZE;
			kIndex = iIndex - 1;
			if (kIndex < 0) kIndex += HISTORYSIZE;

			// Run the lowpass difference equation
			double out = outArray[iIndex] = 
				2.0 * 
				(alpha * (inArray[iIndex] + (2 * inArray[kIndex]) + inArray[jIndex]) +
				 gamma * outArray[kIndex] -
				 beta  * outArray[jIndex]);
			
			outBuffer[index] = out;

			iIndex = (iIndex + 1) % HISTORYSIZE;
		}
	}
	
	public void doFilterNoSum(double [] inBuffer, double [] outBuffer,
						 int length) {

		for (int index=0; index < length; index++) {

			// Fetch sample
			inArray[iIndex] = inBuffer[index];
			
			// Do indices maintainance
			jIndex = iIndex - 2;
			if (jIndex < 0) jIndex += HISTORYSIZE;
			kIndex = iIndex - 1;
			if (kIndex < 0) kIndex += HISTORYSIZE;

			// Run the lowpass difference equation
			double out = outArray[iIndex] = 
				2.0 * 
				(alpha * (inArray[iIndex] + (2 * inArray[kIndex]) + inArray[jIndex]) +
				 gamma * outArray[kIndex] -
				 beta  * outArray[jIndex]);
			
			outBuffer[index] = out;

			iIndex = (iIndex + 1) % HISTORYSIZE;
		}
	}
}

