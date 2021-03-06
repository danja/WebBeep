/**
 * 
 */
package org.hyperdata.beeps.go;

import java.util.Comparator;


/**
 * @author danny
 *
 * highest first
 */
public class FitnessComparator implements Comparator<Organism> {

	// Returns a negative integer, zero, 
	// or a positive integer as the first argument is less than, equal to, or greater than the second.
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Organism o1, Organism o2) {
		double f1 = o1.getMeanFitness();
		double f2 = o2.getMeanFitness();
		if(f1==0){
			f1 = o1.getFitness()/2;
		}
		if(f2==0){
			f2 = o2.getFitness()/2;
		}
		if(f1 == f2) return 0;
		if(f1 < f2){
			return 1;
		}
		return -1;
	}
}
