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

	// Window type constants
	public final static int BLACKMAN = 0;
	public final static int HANNING = 1;
	public final static int HAMMING = 2;
	public final static String[] WINDOW_TYPES = {"Blackman", "Hanning","Hamming"};

	/**
	 * @param fc
	 *            the fc to set
	 */
	public abstract void setFc(double fc);
	
	public double getFc();
	
	/**
	 * @param fc2
	 *            the fc2 to set
	 */
	public abstract void setFc2(double fc2);

	public double getFc2();
	/**
	 * @param shape
	 *            the shape to set
	 */
	public abstract void setShape(int shape);
	
	public int getShape();

	public abstract void setShapeName(String shapeName);
	
	public String getShapeName();
	
	public void setWindow(int windowType);
	
	public int getWindow();
	public String getWindowName();
	/**
	 * @param nPoints
	 *            the nPoints to set
	 */
	public abstract void setnPoints(int nPoints);
	
	public int getnPoints();



	public static final int LP = 0;
	public static final int HP = 1;
	public static final int BP = 2;
	public static final int BS = 3;
	
	public final static String[] SHAPES = {"LP", "HP","BP", "BS"};

	public abstract void initWeights();

	public abstract List<Double> filter(List<Double> input);

}