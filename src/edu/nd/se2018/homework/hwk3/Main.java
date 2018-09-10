package edu.nd.se2018.homework.hwk3;

public class Main {
	public static void main(String[] args) {
		Race myRace = new Race();
		
		myRace.enrollHorse("Sparky",5,new EarlySprintStrategy());
		myRace.enrollHorse("Tortoise",3,new SlowStartStrategy());
		myRace.enrollHorse("Biff",4,new SteadyRunStrategy());
		myRace.enrollHorse("Mr. Rock",0,new SlowStartStrategy());

		myRace.run();
	}
}
