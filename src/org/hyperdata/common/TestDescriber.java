/**
 * 
 */
package org.hyperdata.common;

/**
 * @author danny
 *
 */
public class TestDescriber extends Describer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestDescriber test = new TestDescriber();
		System.out.println(test.describe());

	}

	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Just a test.";
	}

}
