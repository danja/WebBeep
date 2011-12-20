/**
 * 
 */
package org.hyperdata.beeps;

import org.hyperdata.beeps.pipelines.Processor;
import org.hyperdata.beeps.util.Chunks;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public class ASCIICodec {

	/**
	 * @param ascii
	 * @return
	 */
	public static Chunks asciiToChunks(String ascii) {
		Chunks chunks = new Chunks();
		for (int i = 0; i < ascii.length(); i++) {
	
			int val = (int) ascii.charAt(i);
			int lsVal = val % 16; // least significant hex digit of val is for
									// high tone
			int msVal = (val - val % 16) / 16;
			
			Tone chunk = WaveMaker.makeDualTone(msVal, lsVal, Constants.TONE_DURATION);
			chunks.add(chunk);
		}
		return chunks;
	}

	/**
	 * @param chunks
	 * @return
	 */
	public static String chunksToASCII(Chunks chunks, PitchfinderGeneral finder) {
		String ascii = "";
		for (int i = 0; i < chunks.size() - 1; i = i + 2) {
			// System.out.println("CHUNK " + i + " and "+ (i+1));
			Tone leftChunk = chunks.get(i);
			Tone rightChunk = chunks.get(i + 1);
			// System.out.println("leftChunk.size()="+leftChunk.size());
			// System.out.println("rightChunk.size()="+rightChunk.size());
			ascii += CharacterDecoder.chunksToCharacter(leftChunk, rightChunk, finder);
		}
		return ascii;
	}

	public static String getRandomASCII(){
return getRandomASCII(50);
	}

	/**
	 * @param i
	 * @return
	 */
	public static String getRandomASCII(int count) {
		String input = "";
		for (char i = 0; i < count; i++) {
			int r = (int)(70 + Math.random() * 58);
		//	System.out.println(r);
			input += (char)r;
		}
	//	System.out.println(input);
		return input;
	}
	
	public static void main(String[] args){
		for(int i=0;i<10;i++){
			System.out.println("----"+getRandomASCII(i));
		}
	}

	/**
	 * @param minCharacters
	 * @param maxCharacters
	 * @return
	 */
	public static String getRandomASCII(int minCharacters, int maxCharacters) {
		int count = minCharacters + (int)((maxCharacters-minCharacters)*Math.random());
		return getRandomASCII(count);
	}

}
