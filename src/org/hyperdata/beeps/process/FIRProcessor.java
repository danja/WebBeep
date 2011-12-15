/**
 * 
 */
package org.hyperdata.beeps.process;

import java.util.List;

import org.hyperdata.beeps.filters.FIRFilter;
import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.pipelines.Processor;

/**
 * @author danny
 *
 */
public class FIRProcessor extends DefaultProcessor implements FIRFilter {

	private FIRFilter filter = new FIRFilterImpl();
	
	public FIRProcessor(String name){
		super(name);
	}
	
	public List<Double> process(List<Double> input) {
		return filter.filter(input);
	}
	
	// delegate methods
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		setFc((Double)parameters.get("cutoff"));
		setFc2((Double)parameters.get("cutoff2"));
		setnPoints((Integer)parameters.get("npoints"));
		setShapeName((String)parameters.get("shape"));
	}

	/**
	 * @return
	 */

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setFc2(double)
	 */
	@Override
	public void setFc2(double fc2) {
		filter.setFc2(fc2);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setShape(int)
	 */
	@Override
	public void setShape(int shape) {
		filter.setShape(shape);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setShapeName(java.lang.String)
	 */
	@Override
	public void setShapeName(String shapeName) {
		filter.setShapeName(shapeName);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setnPoints(int)
	 */
	@Override
	public void setnPoints(int nPoints) {
		filter.setnPoints(nPoints);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#setFc(double)
	 */
	@Override
	public void setFc(double fc) {
		filter.setFc(fc);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#initWeights()
	 */
	@Override
	public void initWeights() {
		filter.initWeights();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#filter(java.util.List)
	 */
	@Override
	public List<Double> filter(List<Double> input) {
		return filter.filter(input);
	}

	
}
