/**
 * 
 */
package org.hyperdata.beeps.optimize;

import org.hyperdata.beeps.filters.Firk;

/**
 * @author danny
 *
 */
public class FIRWindowParameter extends DefaultParameter {

	public FIRWindowParameter(String name){
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.optimize.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		// W_BLACKMAN, W_HANNING, or W_HAMMING
		int r = (int) (Math.random()*3);
		// System.out.println(r);
		value = Firk.windowName(r);
	}
	
	public static void main(String[] args){
		for(int i=0;i<10;i++){
			System.out.println(new FIRWindowParameter("test"));
		}
	}

}
