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
	
	public void setStrategy(int strategyNum) {
		switch(strategyNum) {
			case 1:
				strategy = new SlowStartStrategy();
				break;
			case 2:
				strategy = new SteadyRunStrategy();
				break;
			case 3:
				strategy = new EarlySprintStrategy();
				break;
			default:
				System.out.println("Error: Unknown strategy number");
		}
	}
	
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
