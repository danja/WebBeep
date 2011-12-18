/**
 * 
 */
package org.hyperdata.go;

import org.hyperdata.go.parameters.ParameterList;


/**
 * @author danny
 * 
 */
public interface Organism {
	
	public double getFitness();

	public void setParameters(ParameterList parameters);
	public ParameterList getParameters();

	/**
	 * 
	 */
	public void run();
}
