/**
 * 
 */
package org.hyperdata.beeps.optimize;

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
	
	public static Parameter createParameter(String type){
//		if(type.equals("chunkNorm") || type.equals("chunkEnv") || type.equals("toNoise")) {
//			return new BooleanParameter(type);
//		}
		if(type.equals("chunkNorm") || type.equals("chunkEnv") || type.equals("toNoise")) {
			return new BooleanParameter(type, "on");
		}
		if(type.equals("attackProportion") || type.equals("decayProportion")) {
			return new EnvelopeParameter(type);
		} 
		if(type.equals("LP_FIR") || type.equals("HP_FIR")){
			return new BooleanParameter(type);
		}
		if(type.equals("LP_points") || type.equals("HP_points")){
			return new FIRNPointsParameter(type);
		}
		if(type.equals("LP_window") || type.equals("HP_window")){
			return new FIRWindowParameter(type);
		}
		if(type.equals("LP_Fc") || type.equals("HP_Fc")){
			return new FrequencyParameter(type);
		}
		

		System.out.println("PARAMETER TYPE "+type+" NOT AVAILABLE");
		return null;
	}
}
