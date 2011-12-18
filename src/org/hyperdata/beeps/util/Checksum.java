/**
 * 
 */
package org.hyperdata.beeps.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.IDN;
import java.util.zip.CRC32;

import org.hyperdata.beeps.Debug;

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

	/**
	 * @param ascii
	 * @return
	 */
	public static String checksum(String ascii) throws Exception {
		if(ascii.length()==0){
			Debug.inform("ascii zero-length in checksum");
			return "0"; 
		}
		String checkString = ascii.substring(0, 1);
		ascii = ascii.substring(1);
		String checkSum = makeChecksumString(ascii);
		if (!checkSum.equals(checkString)) {
			// throw new Exception("checksum failed");
			Debug.inform("Checksum failed!");
		}
		return ascii;
	}
}
