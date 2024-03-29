/**
 * 
 */
package org.hyperdata.beeps.system;

import java.util.List;

import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;


/**
 * @author danny
 *
 */
public interface Processor extends Component {
//	public List<Double> process(List<Double> input);
//	public List<List<Double>> processMulti(List<List<Double>> input);
	
	public Tone process(Tone input);
	public Chunks process(Chunks input);
	public void setEnabled(boolean enabled);
	public boolean isEnabled();
	/**
	 * @param parameter
	 */
	public void setParameter(Parameter parameter);
	/**
	 * @param string
	 * @param string2
	 */


	/**
	 * @param string
	 * @param string2
	 */
	// public void setParameter(String string, String string2);
}
