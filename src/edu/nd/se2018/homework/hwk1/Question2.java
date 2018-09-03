package edu.nd.se2018.homework.hwk1;
import java.util.*;

public class Question2 {

	public Question2(){}
	
	public String getMostFrequentWord(String input, String stopwords){
	
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		for (String word : input.split(" ")) {
			// ignore stop words
			if (stopwords.contains(word)) {
				continue;
			}
			// increment count for word
			if (counts.containsKey(word)) {
				counts.put(word, counts.get(word)+1);
			} else {
				counts.put(word, 1);
			}
		}	
		
		boolean unique = true;
		int maxCount = 0;
		String maxWord = null;
		// find most common word
		for (String word : counts.keySet()) {
			int count = counts.get(word);
			
			// if new max, record it
			if (count > maxCount) {
				maxCount = count;
				maxWord = word;
				unique = true;
			}
			// if it's a repeat count, mark false
			else if (count == maxCount) {
				unique = false;
			}
		}
		
		// return the max only if it was a unique count
		if (unique) {
			return maxWord;
		} else {
			return null;
		}
	}
}
