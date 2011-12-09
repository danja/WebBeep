/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

/**
 * @author danny
 *
 */
public interface Processor {
	public List<Double> process(List<Double> input);
	public void setParameter(String name, Object value);
}
