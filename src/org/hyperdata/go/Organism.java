/**
 * 
 */
package org.hyperdata.go;

import org.hyperdata.go.parameters.ParameterSet;


/**
 * @author danny
 * 
 */
public interface Organism {
	
	public double getFitness();

	public void setParameters(ParameterSet parameters);
	public ParameterSet getParameters();
}
