/**
 * 
 */
package org.hyperdata.common;

/**
 * @author danny
 *
 */
public class TestDescriber extends Describer {

	double d = 0.123;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestDescriber test = new TestDescriber();
		System.out.println(test.describe());
		
	//	Describer.turtleFromVariable("d", d);

	}

	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Just a test.";
	}

}
