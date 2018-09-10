package edu.nd.se2018.homework.hwk3;

public class SlowStartStrategy extends Strategy {

	@Override
	public double getPercentSpeed(double milesRun) {
		if (milesRun <= 6.0) {
			return 0.75;
		} else if (milesRun <= 9) {
			return 0.9;
		} else {
			return 1;
		}
	}

}
