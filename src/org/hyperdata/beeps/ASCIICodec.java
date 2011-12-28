/**
 * 
 */
package org.hyperdata.beeps;

import org.hyperdata.beeps.config.Constants;
import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.pitchfinders.PitchFinderGeneral;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public class ASCIICodec {

	/**
	 * @param ascii input text
	 * @return output audio tones
	 */
	public static Chunks asciiToChunks(String ascii) {
		Chunks chunks = new Chunks();
		for (int i = 0; i < ascii.length(); i++) {

			int val = (int) ascii.charAt(i);
			int lsVal = val % 16; // least significant hex digit of val is for
									// high tone
			int msVal = (val - val % 16) / 16;

			Tone chunk = WaveMaker.makeDualTone(msVal, lsVal,
					Constants.TONE_DURATION);
			chunks.add(chunk);
		}
		return chunks;
	}

	/**
	 * @param chunks input audio tones
	 * @return ASCII string derived from tones
	 */
	public static String chunksToASCII(Chunks chunks, PitchFinderGeneral finder) {
		String ascii = "";
		for (int i = 0; i < chunks.size() - 1; i = i + 2) {
			// System.out.println("CHUNK " + i + " and "+ (i+1));
			Tone leftChunk = chunks.get(i);
			Tone rightChunk = chunks.get(i + 1);
			// System.out.println("leftChunk.size()="+leftChunk.size());
			// System.out.println("rightChunk.size()="+rightChunk.size());
			ascii += CharacterDecoder.chunksToCharacter(leftChunk, rightChunk,
					finder);
		}
		return ascii;
	}

	public static String getRandomASCII() {
		return getRandomASCII(50);
	}

	/**
	 * @param i number of characters
	 * @return String of random ASCII characters
	 */
	public static String getRandomASCII(int count) {
		String input = "";
		for (char i = 0; i < count; i++) {
			int r = (int) (70 + Math.random() * 58);
			// System.out.println(r);
			input += (char) r;
		}
		// System.out.println(input);
		return input;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println("----" + getRandomWebbyASCII(5, 50));
		}
	}

	public static String getRandomWebbyASCII(int minCharacters,
			int maxCharacters) { // ugly but good enough
			String webby = "http://";
			int domain = (int) (maxCharacters * Math.random()/4);
			int path = (int) (maxCharacters * Math.random()/2);
			int anchor = (int) (maxCharacters * Math.random()/4);	
			
			while (webby.length() < domain) {
				if (Math.random() > 0.1) {
					int r = (int) (70 + Math.random() * 58); // character
					webby += (char) r;
				} else {
					webby += ".";
				}
			}
			while (webby.length() < domain+path) {
				if (Math.random() > 0.1) {
					int r = (int) (70 + Math.random() * 58); // character
					webby += (char) r;
				} else {
					webby += "/";
				}
			}
			webby +="#";
			while (webby.length() < domain+path+anchor) {
				if (Math.random() > 0.1) {
					int r = (int) (70 + Math.random() * 58); // character
					webby += (char) r;
				} else {
					webby += ".";
				}
			}
			return webby;
	}

	/**
	 * Generate a random string of random ASCII characters
	 * 
	 * @param minCharacters lowest size of string
	 * @param maxCharacters highest size of string
	 * @return random ASCII string
	 */
	public static String getRandomASCII(int minCharacters, int maxCharacters) {
		int count = minCharacters
				+ (int) ((maxCharacters - minCharacters) * Math.random());
		return getRandomASCII(count);
	}

}
