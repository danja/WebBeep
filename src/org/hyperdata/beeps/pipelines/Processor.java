/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;


/**
 * @author danny
 *
 */
public interface Processor extends Parameterized {
	public List<Double> process(List<Double> input);
	public List<List<Double>> processMulti(List<List<Double>> input);
}
