package edu.nd.se2018.homework.hwk3;

public class Horse {
	// horse traits
	private String name;
	private int maxSpeed;
	private Strategy strategy;
	
	// state variables
	private double distanceRun;
	
	// constructor
	public Horse(String name, int maxSpeed, Strategy strategy) {
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.strategy = strategy;
		this.distanceRun = 0;
	}
	
	// access functions
	public String getName() { return this.name; }
	public double getDistanceRun(){ return this.distanceRun; }
	
	// racing functions
	public void reset() {
		this.distanceRun = 0;
	}
	
	public void step() {
		// the scaling factor between the horses' speed and miles
		double scalingFactor = 0.1;
		
		distanceRun += scalingFactor * maxSpeed * this.strategy.getPercentSpeed(distanceRun);
	}
}
