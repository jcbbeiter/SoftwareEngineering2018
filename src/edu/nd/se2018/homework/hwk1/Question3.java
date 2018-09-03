package edu.nd.se2018.homework.hwk1;
import java.util.*;

public class Question3 {
	
	public Question3(){}
	
    public int getMirrorCount(int[] numbers){
    	// load numbers into ArrayList
    	ArrayList<Integer> numbersList = new ArrayList<Integer>();
    	for (int num : numbers) {
    		numbersList.add(num);
    	}
    	
    	// checks for a subsequence of each size, decreasing -- return first
    	for (int length = numbers.length; length > 0; length--) {
    		// check all possible starting locations for sublist
    		for (int i = 0; i <= numbers.length - length; i++) {
    			// extract and reverse sublist
    			ArrayList<Integer> sublist = new ArrayList<Integer>(numbersList.subList(i, i+length));
    			Collections.reverse(sublist);
    			// check if reversed sublist in original list
    			if (Collections.indexOfSubList(numbersList, sublist) != -1) {
    				return length;
    			}
    		}
    	}

		return 0;	
	}
}
