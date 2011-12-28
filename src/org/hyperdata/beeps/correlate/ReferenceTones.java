package org.hyperdata.beeps.correlate;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hyperdata.beeps.Maps;
import org.hyperdata.beeps.WaveMaker;
import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public class ReferenceTones {
	
	public static Map<Double,Tone> tones = makeRefs();
	/**
	 * key is the frequency, value is a List contain reference tone
	 * 
	 * @return
	 */
	private static Map<Double,Tone> makeRefs(){
		// if(refs != null) return refs; // lazy
		Map<Double,Tone> refs = new HashMap<Double,Tone>();
		
		for(int i=0;i<Maps.ALL_FREQS.length;i++){
			Set keys = refs.keySet(); // there are duplicates in ALL_FREQS
			if(!keys.contains(Maps.ALL_FREQS[i])) {
				double duration = 1.0 / Maps.ALL_FREQS[i];
				// System.out.println("duration="+duration);
				Tone ref = new Tone(WaveMaker.makeWaveform(Maps.ALL_FREQS[i], 0.99, duration));
				refs.put(Maps.ALL_FREQS[i], ref);
			}
		}
		return refs;
	}
}
