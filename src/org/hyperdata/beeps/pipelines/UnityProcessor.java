/**
 * 
 */
package org.hyperdata.beeps.pipelines;

import java.util.List;

/**
 * @author danny
 *
 * dummy, do-nothing process
 */
public class UnityProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
	@Override
	public List<Double> process(List<Double> input) {
		return input;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setParameter(String name, Object value) {
	}

}
