/**
 * 
 */
package org.hyperdata.beeps.parameters;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.pipelines.Parameterized;

/**
 * @author danny
 *
 */
public class ParameterFactory {

	/*
	p("chunkEnv");
	p("attackProportion");
p("decayProportion");
p("LP_FIR");
p("LP_window");
p("LP_Fc");
p("LP_points");
p("HP_FIR");
p("HP_window");
p("HP_Fc");
p("HP_points");
*/
	
	public static Parameter createParameter(Parameterized processor, String type){
//		if(type.equals("chunkNorm") || type.equals("chunkEnv") || type.equals("toNoise")) {
//			return new BooleanParameter(type);
//		}
		if(type.equals("chunkNorm") || type.equals("chunkEnv") || type.equals("toNoise")) {
		//	return new BooleanParameter(processor, type, "on");
			return new BooleanParameter(processor, type);
		}
		if(type.equals("attackProportion") || type.equals("decayProportion")) {
			return new EnvelopeParameter(processor, type);
		} 
		if(type.equals("LP_FIR") || type.equals("HP_FIR") || type.equals("LP_FIR1") || type.equals("LP_FIR2") ){
			return new BooleanParameter(processor, type);
		}
		if(type.equals("npoints")){
			return new FIRNPointsParameter(processor, type);
		}
		if(type.equals("window")){
			return new FIRWindowParameter(processor, type);
		}
		if(type.equals("cutoff")){
		//	System.out.println("HERE processor = "+processor);
			return new FrequencyParameter(processor, type);
		}
		
		if(type.equals("silenceThreshold") || type.equals("cropProportion") || type.equals("cropProportion") || type.equals("peakDelta")){
			return new NormalParameter(processor, type);
		}
		if(type.equals("fftBits")){
			return new FFTBitsParameter(processor, type);
		}
		
		Debug.error("Parameter "+type+" not available in ParameterFactory.");
		return null;
	}
}
