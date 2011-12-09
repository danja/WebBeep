/**
 * 
 */
package org.hyperdata.beeps.degrade;

import java.util.List;

/**
 * @author danny
 *
 */
public interface Corruptor {

	public List<Double> corrupt(List<Double> input);
	public List<Double> corrupt(List<Double> input, double amount);
}
