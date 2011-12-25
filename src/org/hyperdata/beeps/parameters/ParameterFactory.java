/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.Debug;

/**
 * @author danny
 *
 */
public class ParameterFactory {
	
	public static Parameter createParameter(Parameterized processor, String parameterName){
		String[] split = parameterName.split("\\.");
		String type = split[2];
//		System.out.println("parameterName="+parameterName);
//		System.out.println("split.length()="+split.length);
//		System.out.println("split="+split);
//
//		System.out.println("type="+type);
//		if(type.equals("chunkNorm") || type.equals("chunkEnv") || type.equals("toNoise")) {
//			return new BooleanParameter(type);
//		}
		if(type.equals("on")) {
		//	return new BooleanParameter(processor, type, "on");
			return new BooleanParameter(processor, parameterName);
		}
		if(type.equals("attackProportion") || type.equals("decayProportion")) {
			return new EnvelopeParameter(processor, parameterName);
		} 
		if(type.equals("chunkNorm") || type.equals("LP_FIR") || type.equals("HP_FIR") || type.equals("LP_FIR1") || type.equals("LP_FIR2") ){
			return new BooleanParameter(processor, parameterName);
		}
		if(type.equals("npoints")){
			return new FIRNPointsParameter(processor, parameterName);
		}
		if(type.equals("window")){
			return new FIRWindowParameter(processor, parameterName);
		}
		if(type.equals("cutoff")){
		//	System.out.println("HERE processor = "+processor);
			return new FrequencyParameter(processor, parameterName);
		}
		
		// Decoder
		if(type.equals("silenceThreshold")){
			return new NormalParameter(processor, parameterName);
		}
		if(type.equals("cropProportion")){
			return new NormalParameter(processor, parameterName);
		}
//		if(type.equals("fftBits")){
//			return new FFTBitsParameter(processor, parameterName);
//		}
//		if(type.equals("repeatToFit")){
//			return new BooleanParameter(processor, parameterName);
//		}
//		if(type.equals("peakDelta")){
//			Parameter p = new NormalParameter(processor, parameterName);
//			p.setValue(new Double(0.25+Math.random()/2));
//			return p;
//			}
		if(type.equals("windowLength")){
			return new RunningAverageWindowLengthParameter(processor, parameterName);
		}
		if(type.equals("lowThreshold")){
			return new NormalParameter(processor, parameterName);
		}
		if(type.equals("highThreshold")){
			return new HighThresholdParameter(processor, parameterName);
		}
		// System.out.println("type="+type);
		if(type.equals("gThreshold")){ // Decoder.pitchFinder.goertzelThreshold
		//	System.out.println("gottype="+type);
			ExponentialParameter ep = new ExponentialParameter(processor, parameterName);
			ep.setRange(100, 100000);
			return ep;
		}
		
		Debug.error("Parameter "+type+" not available in ParameterFactory.");
		return null;
	}
}
