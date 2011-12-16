/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

/**
 * @author danny
 *
 */
public interface SplittingProcessor extends Parameterized {
	public List<List<Double>> process(List<Double> input);
}
