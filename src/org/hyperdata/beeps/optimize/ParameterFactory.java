/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.pipelines.Processor;

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
	
	public static Parameter createParameter(Processor processor, String type){
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
		if(type.equals("LP_FIR") || type.equals("HP_FIR")){
			return new BooleanParameter(processor, type);
		}
		if(type.equals("npoints")){
			return new FIRNPointsParameter(processor, type);
		}
		if(type.equals("window")){
			return new FIRWindowParameter(processor, type);
		}
		if(type.equals("cutoff")){
			System.out.println("HERE processor = "+processor);
			return new FrequencyParameter(processor, type);
		}
		

		System.out.println("PARAMETER TYPE "+type+" NOT AVAILABLE");
		return null;
	}
}
