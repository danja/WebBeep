/**
 * 
 */
package org.hyperdata.beeps.go;

import java.util.ArrayList;
import java.util.List;


/**
 * @author danny
 *
 */
public abstract class DefaultOrganism implements Organism {

	protected int age = 0;
	boolean hasRun = false;
	protected double fitness = -1;
	protected double runTime = 100;
	protected double accuracy = 0;
	private List<Double> history = new ArrayList<Double>();

	/**
	 * @return the runTime
	 */
	public double getRunTime() {
		return this.runTime;
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return this.accuracy;
	}



	/**
	 * 
	 */
	public DefaultOrganism() {
		super();
	}


	public int getAge() {
		return history.size();
	}


public void run(){
	// System.out.println("  age="+history.size());
	history.add(getFitness());
	// age++;
}
	
	public double getMeanFitness() {
		double sum = 0;
		for(int i=0;i<history.size();i++){
			sum += history.get(i);
		}
		 return sum/history.size();
	}

}