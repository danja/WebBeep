/**
 * 
 */
package org.hyperdata.beeps.filters;

import java.util.List;

/**
 * @author danny
 *
 */
public interface FIRFilter {

	/**
	 * @param fc2
	 *            the fc2 to set
	 */
	public abstract void setFc2(double fc2);

	/**
	 * @param shape
	 *            the shape to set
	 */
	public abstract void setShape(int shape);

	public abstract void setShapeName(String shapeName);

	/**
	 * @param nPoints
	 *            the nPoints to set
	 */
	public abstract void setnPoints(int nPoints);

	/**
	 * @param fc
	 *            the fc to set
	 */
	public abstract void setFc(double fc);

	public static final int LP = 0;
	public static final int HP = 1;
	public static final int BP = 2;
	public static final int BS = 3;

	public abstract void initWeights();

	public abstract List<Double> filter(List<Double> input);

}