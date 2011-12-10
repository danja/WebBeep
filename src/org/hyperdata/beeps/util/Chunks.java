/**
 * 
 */
package org.hyperdata.beeps.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author danny
 *
 */
public class Chunks extends ArrayList<Tone> {

	/**
	 * for convenience while refactoring 
	 * 
	 * @param contents
	 */
	public Chunks(List<List<Double>> contents) {
		super();
		for(int i=0;i<contents.size();i++){
			add(new Tone(contents.get(i)));
		}
	}
	
	/**
	 * 
	 */
	public Chunks() {
		super();
	}

	/**
	 * @param initialCapacity
	 */
	public Chunks(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */
	public Chunks(Collection<? extends Tone> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

}
