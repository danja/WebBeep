/**
 * 
 */
package org.hyperdata.beeps.go;

import org.hyperdata.beeps.system.ParameterList;


/**
 * @author danny
 * 
 */
public interface Organism {
	
	public double getFitness();

	public void setParameters(ParameterList parameters);
	public ParameterList getParameters();
	
	public int getAge();
	
	
	public double getMeanFitness();
	/**
	 * 
	 */
	public void run();
}
