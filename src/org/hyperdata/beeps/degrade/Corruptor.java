/**
 * 
 */
package org.hyperdata.beeps.degrade;

import java.util.List;
import java.util.Set;

import org.hyperdata.beeps.*;
import org.hyperdata.beeps.pipelines.Processor;
/**
 * @author danny
 *
 */
public class Corruptor implements Processor {

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(java.util.List)
	 */
	@Override
	public List<Double> process(List<Double> input) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#processMulti(java.util.List)
	 */
	@Override
	public List<List<Double>> processMulti(List<List<Double>> input) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setParameter(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#getParameter(java.lang.String)
	 */
	@Override
	public Object getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#parameterNames()
	 */
	@Override
	public Set<String> parameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	// public List<Double> corrupt(List<Double> input);
	//public List<Double> corrupt(List<Double> input, double amount);
}
