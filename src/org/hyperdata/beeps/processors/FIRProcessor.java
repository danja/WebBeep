/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.List;

import org.hyperdata.beeps.filters.FIRFilter;
import org.hyperdata.beeps.filters.FIRFilterImpl;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterized;

/**
 * @author danny
 * 
 */
public class FIRProcessor extends DefaultProcessor implements FIRFilter {

	private FIRFilter filter = new FIRFilterImpl();

	public FIRProcessor(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		return new Tone(filter.filter(input));
	}

	// delegate methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		String shape = (String)  getLocal("shape");
		setShapeName(shape);
		setFc((Double) getLocal("cutoff"));
		setnPoints((Integer)  getLocal("npoints"));
		if (shape.equals("BP") || shape.equals("BS")) {
			setFc2((Integer)  getLocal("cutoff2"));
		}
		initWeights();
	}

	/**
	 * @return
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#setFc2(double)
	 */
	@Override
	public void setFc2(double fc2) {
		filter.setFc2(fc2);
	}
	
//	public double getFc2(){
//		return filter.g
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#setShape(int)
	 */
	@Override
	public void setShape(int shape) {
		filter.setShape(shape);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#setShapeName(java.lang.String)
	 */
	@Override
	public void setShapeName(String shapeName) {
		filter.setShapeName(shapeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#setnPoints(int)
	 */
	@Override
	public void setnPoints(int nPoints) {
		filter.setnPoints(nPoints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#setFc(double)
	 */
	@Override
	public void setFc(double fc) {
		filter.setFc(fc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#initWeights()
	 */
	@Override
	public void initWeights() {
		// System.out.println("init weights in "+this.getName());
		filter.initWeights();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.beeps.filters.FIRFilter#filter(java.util.List)
	 */
	@Override
	public List<Double> filter(List<Double> input) {
		return filter.filter(input);
	}

	/**
	 * @param blackman
	 */
	public void setWindow(int windowType) {
		filter.setWindow(windowType);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getFc2()
	 */
	@Override
	public double getFc2() {
		return filter.getFc2();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getShape()
	 */
	@Override
	public int getShape() {
		return filter.getShape();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getShapeName()
	 */
	@Override
	public String getShapeName() {
		return filter.getShapeName();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getWindow()
	 */
	@Override
	public int getWindow() {
		return filter.getWindow();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getnPoints()
	 */
	@Override
	public int getnPoints() {
		return filter.getnPoints();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getFc()
	 */
	@Override
	public double getFc() {
		return filter.getFc();
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.filters.FIRFilter#getWindowName()
	 */
	@Override
	public String getWindowName() {
		return filter.getWindowName();
	}

}
