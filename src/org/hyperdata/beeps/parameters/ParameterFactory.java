/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.Debug;
import org.hyperdata.go.parameters.Parameter;
import org.hyperdata.go.parameters.Parameterized;

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
		if(type.equals("LP_FIR") || type.equals("HP_FIR") || type.equals("LP_FIR1") || type.equals("LP_FIR2") ){
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
			return new ExponentialParameter(processor, parameterName);
		}
		
		// Decoder
		if(type.equals("silenceThreshold")){
			return new NormalParameter(processor, parameterName);
		}
		if(type.equals("cropProportion")){
			return new NormalParameter(processor, parameterName);
		}
		if(type.equals("fftBits")){
			return new FFTBitsParameter(processor, parameterName);
		}
		if(type.equals("repeatToFit")){
			return new BooleanParameter(processor, parameterName);
		}
		if(type.equals("peakDelta")){
			Parameter p = new NormalParameter(processor, parameterName);
			p.setValue(new Double(0.25+Math.random()/2));
			return p;
			}
		Debug.error("Parameter "+type+" not available in ParameterFactory.");
		return null;
	}
}
