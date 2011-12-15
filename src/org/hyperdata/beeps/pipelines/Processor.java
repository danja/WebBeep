/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;
import java.util.Set;

/**
 * @author danny
 *
 */
public interface Processor {
	public void setName(String name);
	public String getName();
	public List<Double> process(List<Double> input);
	public List<List<Double>> processMulti(List<List<Double>> input);
	public void setParameter(String name, Object value);
	public Object getParameter(String name);
	public void initFromParameters();
	// public void createParameters();
	/**
	 * @return
	 */
	public Set<String> parameterNames();
}
