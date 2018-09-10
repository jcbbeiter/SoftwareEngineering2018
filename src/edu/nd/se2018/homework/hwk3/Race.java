package edu.nd.se2018.homework.hwk3;
import java.util.ArrayList;

public class Race {
	private ArrayList<Horse> horses;
	private boolean finished;
	private Horse winner;
	
	// constructor
	public Race() {
		horses = new ArrayList<Horse>();
		finished = false;
	}
	
	public void clearRace() {
		finished = false;
		winner = null;
		horses.clear();
	}
	
	// add existing horse object
	public void enrollHorse(Horse h) {
		if (finished) {
			System.out.println("Error enrolling horse: race is already done!");
			return;
		}
		horses.add(h);
	}
	
	// add and construct new horse 
	public void enrollHorse(String name, int maxSpeed, Strategy strategy) {
		Horse h = new Horse(name, maxSpeed, strategy);
		enrollHorse(h);
	}
	
	// get name of winning horse (running race if necessary)
	public String getWinner() {
		if (finished == false) {
			this.run();
		}
		
		return winner.getName();
	}
	
	// run race
	public void run() {
		if (finished) {
			System.out.println("Error starting race: race is already done!");
			return;
		}
		
		// run until someone finishes
		int timestep = 1;
		while (!finished) {
			System.out.printf("\n--- Timestep %d ---\n",timestep++);
			for (Horse h : horses) {
				h.step();
				double distanceRun = h.getDistanceRun();
				System.out.printf("%s has run %.2f miles\n", h.getName(), distanceRun);
				if (distanceRun >= 10) {
					finished = true;
				}
			}
		}
		
		// find winner
		double bestDistance = 0;
		for (Horse h : horses) {
			if (h.getDistanceRun() > bestDistance) {
				winner = h;
				bestDistance = winner.getDistanceRun();
			}
		}
		
		// report winner
		System.out.printf("\n%s has won the race!\n",winner.getName());
		
	}
	
}
