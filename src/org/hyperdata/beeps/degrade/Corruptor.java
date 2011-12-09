/**
 * 
 */
package org.hyperdata.beeps.degrade;

import java.util.List;

import org.hyperdata.beeps.*;
import org.hyperdata.beeps.pipelines.Processor;
/**
 * @author danny
 *
 */
public interface Corruptor extends Processor {

	// public List<Double> corrupt(List<Double> input);
	public List<Double> corrupt(List<Double> input, double amount);
}
