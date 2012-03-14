/**
 * 
 */
package org.hyperdata.beeps.server;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author danny
 *
 */
public class TextSplitter {

	public static List<String> splitText(String text){

		String[] rawWords = text.split("//s+//g");
		Deque<String> wordQueue = new ArrayDeque<String>();
		
		for(int i=0;i<rawWords.length;i++){ // put words into an queue
			while(rawWords[i].length() > 62){ // chopping up if necessary
				wordQueue.addLast(rawWords[i].substring(0, 62));
				rawWords[i] = rawWords[i].substring(62);
			}
			wordQueue.addLast(rawWords[i]);
		}
		List<String> blocks = new ArrayList<String>();
		
		String block = "";

		while(wordQueue.size() > 0){ // compose, like word wrap
			
			String next = wordQueue.removeFirst();
			if(block.length() + next.length() > 62){
				blocks.add(block);
				block = next;
			} else {
				block += next;
			}
		}
		blocks.add(block);
		return blocks;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "Any letter except an uppercase letter (subtraction) ";
		String n = "0123456789_";
				n = n+n+n+n+n+n+n+n+n+n;
		s = s+s+s;
		s = s + n +s;
		List<String> blocks = splitText(s);
		String glued = "";
		for(int i = 0;i<blocks.size();i++){
			System.out.println(blocks.get(i));
			glued += blocks.get(i);
		}
		System.out.println(s.equals(glued));
	}

}
