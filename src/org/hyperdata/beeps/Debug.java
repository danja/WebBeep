/**
 * 
 */
package org.hyperdata.beeps;

/**
 * @author danny
 *
 */
public class Debug {
	public static char level = 2;
	
	public static final char QUIET = 0;
	public static final char INFORM = 1;
	public static final char DEBUG = 2;
	
	public static void inform(String message){
		if(level > 0) {
			System.out.println(message);
		}
	}	
	
	public static void debug(String message){
		if(level > 1) {
			System.out.println(message);
		}
	}

	/**
	 * @param envelopeShaper
	 */
	public static void halt(Object object) {
		System.out.println("Exit forced in "+object);
		System.exit(1);
	}
}
