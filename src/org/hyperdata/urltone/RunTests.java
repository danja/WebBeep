/**
 * 
 */
package org.hyperdata.urltone;

import java.util.*;
/**
 * @author danny
 *
 */
public class RunTests {
	static int LENGTH = 20;
	static Random random = new Random();
	
	
	String finalString = String.valueOf((char)random.nextInt(256));
	
	public static void main(String[] args){
		String testString = "";
		for(int i=0;i<LENGTH;i++){
			testString+= getChar();
		}
		System.out.println(testString);
	}
		public static String getChar(){
			return String.valueOf((char)random.nextInt(256));
		}
	
}
