/**
 * Based on material from Digital Audio with Java, Craig Lindley
 * ftp://ftp.prenhall.com/pub/ptr/professional_computer_science.w-022/digital_audio/
 * in turn derived from libfft.c by by Jef Poskanzer (public domain)
 */

package org.hyperdata.urltone.fft;

import java.util.ArrayList;
import java.util.List;

public class FFT {

	private static final double TWOPI = 2.0 * Math.PI;

	// Limits on the number of bits this algorithm can utilize
	private static final int LOG2_MAXFFTSIZE = 18; // was 15
	private static final int MAXFFTSIZE = 1 << LOG2_MAXFFTSIZE;

	/**
	 * FFT class constructor Initializes code for doing a fast Fourier transform
	 * 
	 * @param int bits is a power of two such that 2^b is the number of samples.
	 */
	public FFT(int bits) {

		this.bits = bits;

		if (bits > LOG2_MAXFFTSIZE) {
			System.out.println("" + bits + " is too big");
			System.exit(1);
		}
		for (int i = (1 << bits) - 1; i >= 0; --i) {
			int k = 0;
			for (int j = 0; j < bits; ++j) {
				k *= 2;
				if ((i & (1 << j)) != 0)
					k++;
			}
			bitreverse[i] = k;
		}
	}

	public List<Double> doPowerFFT(List<Double> xrL, boolean invFlag) {
		// int size = xrL.size();
		int size = (int) Math.pow(2, bits);
		System.out.println("size="+size);
		System.out.println("xrl size="+xrL.size());
		double[] xr = new double[size];
		double[] xi = new double[size];
		for (int i = 0; i < xrL.size(); i++) {
			xr[i] = xrL.get(i).doubleValue();
			xi[i] = 0;
		}
		for (int i = xrL.size(); i < size; i++) {
			xr[i] = 0;
			xi[i] = 0;
		}
		System.out.println("xr length="+xr.length);
		doFFT(xr, xi, invFlag);

		List<Double> powers = new ArrayList<Double>();
		for (int i = 0; i < size; i++) {
			double power = xr[i] * xr[i] + xi[i] * xi[i];
			powers.add(new Double(power));
		}
		return powers;
	}

	/**
	 * A fast Fourier transform routine
	 * 
	 * @param double [] xr is the real part of the data to be transformed
	 * @param double [] xi is the imaginary part of the data to be transformed
	 *        (normally zero unless inverse transofrm is effect).
	 * @param boolean invFlag which is true if inverse transform is being
	 *        applied. false for a forward transform.
	 */
	public void doFFT(double[] xr, double[] xi, boolean invFlag) {
		int n, n2, i, k, kn2, l, p;
		double ang, s, c, tr, ti;

		n2 = (n = (1 << bits)) / 2;

		for (l = 0; l < bits; ++l) {
			for (k = 0; k < n; k += n2) {
				for (i = 0; i < n2; ++i, ++k) {
					p = bitreverse[k / n2];
					ang = TWOPI * p / n;
					c = Math.cos(ang);
					s = Math.sin(ang);
					kn2 = k + n2;

					if (invFlag)
						s = -s;

					tr = xr[kn2] * c + xi[kn2] * s;
					ti = xi[kn2] * c - xr[kn2] * s;

					xr[kn2] = xr[k] - tr;
					xi[kn2] = xi[k] - ti;
					xr[k] += tr;
					xi[k] += ti;
				}
			}
			n2 /= 2;
		}

		for (k = 0; k < n; k++) {
			if ((i = bitreverse[k]) <= k)
				continue;

			tr = xr[k];
			ti = xi[k];
			xr[k] = xr[i];
			xi[k] = xi[i];
			xr[i] = tr;
			xi[i] = ti;
		}

		// Finally, multiply each value by 1/n, if this is the forward
		// transform.
		if (!invFlag) {
			double f = 1.0 / n;

			for (i = 0; i < n; i++) {
				xr[i] *= f;
				xi[i] *= f;
			}
		}
	}

	// Private class data
	private int bits;
	private int[] bitreverse = new int[MAXFFTSIZE];
}