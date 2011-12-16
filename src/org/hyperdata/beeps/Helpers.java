/**
 * 
 */
package org.hyperdata.beeps;

/**
 * @author danny
 *
 */
public class Helpers {

	public static String getRandomASCII(){
		String input = "";
		for (char i = 0; i < 50; i++) {
			int r = (int)(70 + Math.random() * 58);
			System.out.println(r);
			input += (char)r;
		}
		System.out.println(input);
		return input;
	}
}
