/**
 * 
 */
package org.hyperdata.beeps.go;

import org.hyperdata.beeps.parameters.ParameterList;


/**
 * @author danny
 * 
 */
public interface Organism {
	
	public double getFitness();

	public void setParameters(ParameterList parameters);
	public ParameterList getParameters();
	
	public int getAge();
	public void setAge(int age);

	/**
	 * 
	 */
	public void run();
}
