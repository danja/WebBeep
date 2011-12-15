/**
 * 
 */
package org.hyperdata.beeps.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hyperdata.beeps.Constants;

/**
 * @author danny
 *
 */
public class Noise {

	  private static  Random random = new Random();
	  
	  public static List<Double> white(double duration){
		  List<Double> white = new ArrayList<Double>();
		  int n = (int) (duration * Constants.SAMPLE_RATE);
		  for(int i=0;i<n;i++){
			  white.add(random.nextDouble()*2-1);
		  }
		  return white;
	  }
	  
	  public double getGaussian(double mean, double variance){
	      return mean + random.nextGaussian() * variance;
	  }
	  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Double> white = white(1.0);
		Plotter.plot(white);
	}
	    
	


}
