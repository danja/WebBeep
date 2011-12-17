/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.Decoder;
import org.hyperdata.beeps.Encoder;
import org.hyperdata.beeps.ParameterizedDecoder;
import org.hyperdata.beeps.ParameterizedEncoder;
import org.hyperdata.beeps.filters.FIRFilter;

/**
 * @author danny
 * 
 * Fitness - function of accuracy and time
 *
 */
public class Organism {

	private ParameterizedEncoder encoder = new ParameterizedEncoder();
//	private FIRFilter encoderLP = new ParameterizedFilter();
//	private FIRFilter encoderHP = new ParameterizedFilter();
	
	
	private ParameterizedDecoder decoder = new ParameterizedDecoder();
//	private FIRFilter decoderLP1 = new ParameterizedFilter();
//	private FIRFilter decoderLP2 = new ParameterizedFilter();
//	private FIRFilter decoderHP = new ParameterizedFilter();
	
	/*
	 Encoder

Bit    Function       Value Range

0      gen Fs           44, 22, 11, 6 kHz
1      chunknorm        on/off
2      env attack       tone length/2...tone length/8
3      env decay           "
4      LP FIR           on/off
5      LP FIR window    Blackman/Hanning/Hamming
6      LP FIR Fc        1kHz...12kHz
7      LP stages        64...4096
8      LP FIR           on/off
9      HP FIR window    Blackman/Hanning/Hamming
A      HP FIR Fc        30Hz...250Hz
B      HP stages        64...4096

Decoder

10 start threshold     0.1...0.9
11 start delay         0...0.5
12 snip duration       0.1...0.9
13 HP FIR              on/off
14 HP FIR window       Blackman/Hanning/Hamming
15 HP FIR Fc           30Hz...250Hz
16 HP stages           64...4096
17 LP1 FIR             on/off
18 LP1 FIR window      Blackman/Hanning/Hamming
19 LP1 FIR Fc          1kHz...12kHz
1A LP1 stages          64...4096
1B LP2 FIR window      Blackman/Hanning/Hamming
1C LP2 FIR Fc          1kHz...12kHz
1D LP2 stages          64...4096
1E FFT bits            8...16
1F peak detector       0.05...0.9
	 */
	public double getFitness(){
	// run the thing, produce a measure
		double time = 1 ;
		double accuracy = 0.5;
		
		
		double fitness = (accuracy * accuracy)/(time + 1); // accuracy is most important, but time also a consideration
		return 0.0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
