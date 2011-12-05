/**
 * 
 */
package org.hyperdata.urltone.decode.correlate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hyperdata.urltone.WaveMaker;
import org.hyperdata.urltone.common.Constants;
import org.hyperdata.urltone.common.Maps;


/**
 * @author danny
 *
 */
public class ReferenceTones {
	
	public static Map<Double,List<Double>> tones = makeRefs();
	/**
	 * key is the frequency, value is a List contain reference tone
	 * 
	 * @return
	 */
	private static Map<Double,List<Double>> makeRefs(){
		// if(refs != null) return refs; // lazy
		Map<Double,List<Double>> refs = new HashMap<Double,List<Double>>();
		
		for(int i=0;i<Maps.ALL_FREQS.length;i++){
			Set keys = refs.keySet(); // there are duplicates in ALL_FREQS
			if(!keys.contains(Maps.ALL_FREQS[i])) {
				double duration = 1.0 / Maps.ALL_FREQS[i];
				// System.out.println("duration="+duration);
				List<Double> ref = WaveMaker.makeWaveform(Maps.ALL_FREQS[i], 0.99, duration);
				refs.put(Maps.ALL_FREQS[i], ref);
			}
		}
		return refs;
	}
}
