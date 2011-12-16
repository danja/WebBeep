/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

/**
 * @author danny
 *
 */
public interface MergingProcessor extends Parameterized {
	public List<Double> process(List<List<Double>> input);
}
