/**
 * Based on material from Digital Audio with Java, Craig Lindley
 * ftp://ftp.prenhall.com/pub/ptr/professional_computer_science.w-022/digital_audio/
 */
package org.hyperdata.urltone.fft;


public class ComputeFrequencyWithFFT {

	// Do a 4096 point fft for the finest possible granularity
	private static final int LOG2_FFTSIZE = 12;
	private static final int FFT_SIZE	  = 1 << LOG2_FFTSIZE;

	/**
	 * Class constructor
	 *
	 * @param int samplingRate is the rate at which the data to be
	 * examined was sampled at.
	 */
	public ComputeFrequencyWithFFT(int samplingRate) {

		// Instantiate an FFT object to run the data through
		fft = new FFT(LOG2_FFTSIZE);

		// Rate at which each bucket is greater than the next in frequency
		rate = ((double) samplingRate) / FFT_SIZE;

		// Arrays need to hold the data when processing is performed
		realArray = new double[FFT_SIZE];
		imagArray = new double[FFT_SIZE];
	}
	
	/**
	 * Compute the dominant frequency of the data in the buffer
	 * Consider only frequencies less than or equal to maxFreq
	 *
	 * @param double maxFreq is the upper limit of the frequencies
	 * of interest.
	 * @param double [] buffer is the buffer of sampled data
	 */
	public double computeFrequency(double maxFreq, double [] buffer) {

		// Is there enough data to process?
		if (buffer.length < FFT_SIZE)
			return -1;

		// Move the sample data into the arrays for processing. The
		// data consists of real data only.
		for(int i=0; i < FFT_SIZE; i++) {
			realArray[i] = buffer[i];
			imagArray[i] = 0.0;
		}

		// Do the forward fft
		fft.doFFT(realArray, imagArray, false);

		// See which bucket has the pitch with the most power
		double maxPower = 0.0;
		double freqAtMax = -1;

		// Because the input data consisted of only real components,
		// only the first half of the data is "independent". Therefore
		// we only need to look at the first half.
		for(int i=0; i < (FFT_SIZE / 2); i++) {
			
			// What frequency bucket does this sample represent?
			double freq = i * rate;
			
			// If this frequency below our maximum frequency of interest?
			if (freq <= maxFreq) {
				// Power is the sum of the components squared
				double power = Math.pow(realArray[i], 2) + 
							   Math.pow(imagArray[i], 2);
				
				// Do we have a new max power?
				if (power > maxPower) {
					// Yes, save it and the frequency it represents
					maxPower = power;
					freqAtMax = freq;
				}
			}	else	{
				// We've passed the frequency we are interested in so
				// terminate our search
				break;
			}
		}
		
		// Return the frequency which had the most power
		return freqAtMax;
	}
	// Private class data
	private FFT fft;
	private double rate;
	private double [] realArray;
	private double [] imagArray;
}
