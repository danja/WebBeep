/**
 * 
 */
package org.hyperdata.beeps.encode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.IDN;
import java.util.zip.CRC32;

/**
 * @author danny
 * 
 */
public class Checksum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "abc";
		System.out.println(makeChecksumString(input));
	}

	public static String makeChecksumString(String input) {
		byte[] bytes = input.getBytes();
		int sum = 0;
		for(int i=0;i<bytes.length;i++){
		//	System.out.println(bytes[i]);
			sum += bytes[i];
		}
	//	System.out.println(sum+"   "+sum % 128);
		String s = new Character((char)(sum % 128)).toString();
		return s;
	}
}
