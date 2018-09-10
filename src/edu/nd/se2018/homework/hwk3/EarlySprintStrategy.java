package edu.nd.se2018.homework.hwk3;

public class EarlySprintStrategy extends Strategy {

	@Override
	public double getPercentSpeed(double milesRun) {
		if (milesRun <= 2) {
			return 1;
		} else {
			return 0.75;
		}
	}

}
