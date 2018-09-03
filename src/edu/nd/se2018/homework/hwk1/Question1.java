package edu.nd.se2018.homework.hwk1;
import java.util.HashSet;

public class Question1 {
		
	public Question1(){}
	
	public int getSumWithoutDuplicates(int[] numbers){
		// load numbers into set
		HashSet<Integer> uniques = new HashSet<Integer>();
		for (int num : numbers) {
			uniques.add(num);
		}
		
		// sum set of unique numbers
		int sum = 0;
		for (int num : uniques) {
			sum += num;
		}
		
		return sum;	
	}
}
