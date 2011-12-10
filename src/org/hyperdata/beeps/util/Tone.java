/**
 * 
 */
package org.hyperdata.beeps.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author danny
 *
 */
public class Tone extends ArrayList<Double> {

	/**
	 * 
	 */
	public Tone() {
		super();
	}

	/**
	 * for convenience while refactoring
	 * 
	 * @param contents
	 */
	public Tone(ArrayList<Double> contents) {
		super();
		addAll(contents);
	}
	
	/**
	 * @param initialCapacity
	 */
	public Tone(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public Tone(Collection<? extends Double> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

}
