/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;


/**
 * @author danny
 *
 */
public interface Pipeline {

	public void addProcessor(Processor processor);
	public List<Double> applyProcessors(List<Double> input);
	public int size();
}
