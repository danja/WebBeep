/**
 * 
 */
package org.hyperdata.beeps;

/**
 * @author danny
 * 
 */
public class Debug {


	public static final char QUIET = 0; 
	public static final char ERROR = 1;
	public static final char INFORM = 2;
	public static final char VERBOSE = 3;
	public static final char DEBUG = 4;
	
	public static char level = 0;
	public static boolean showPlots = false;

	public static void log(String message) {
		//	System.out.println(message);
	}
	
	// Apache logger etc uses DEBUG, INFO, WARN, ERROR and FATAL
	
	public static void inform(String message) {
		if (level >= INFORM) {
			System.out.println(message);
		}
	}
	
	public static void error(String message) {
		if (level >=  ERROR) {
			System.err.println(message);
		}
	}

	public static void verbose(String message) {
		if (level >=  VERBOSE) {
			System.out.println(message);
		}
	}
	
	public static void debug(String message) {
		if (level >=  DEBUG) {
			System.out.println(message);
		}
	}
	
	public static void error(Object object) {
		error(object.toString());
	}
	
	public static void verbose(Object object) {
		verbose(object.toString());
	}

	public static void inform(Object object) {
		inform(object.toString());
	}

	public static void debug(Object object) {
		debug(object.toString());
	}

	/**
	 * @param envelopeShaper
	 */
	public static void halt(Object object) {
		System.out.println("Exit forced in " + object);
		System.exit(1);
	}
}
