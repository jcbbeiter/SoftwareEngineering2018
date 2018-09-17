package edu.nd.se2018.homework.hwk4;

import java.awt.Point;

public class ColumbusShip extends Ship {
	// I made the observable a member of Ship class because I opted for 
	// polymorphic behavior between columbus' and the pirate ships
	// and java doesn't support (direct) multiple inheritance
	private PirateNotifier notifier = new PirateNotifier();
	
	public ColumbusShip() {
		location = new Point(0,0);
	}
	public ColumbusShip(int x, int y) {
		location = new Point(x, y);
	}
	
	public ColumbusShip(Point p) {
		location = p;
	}
	
	public void addObserver(PirateShip pirate) {
		notifier.addObserver(pirate);
	}
	
	public void setLocation(Point p) {
		if (p.x != location.x || p.y != location.y) {
			notifier.change();
			notifier.location = p;
		}
		location = p;
		notifier.notifyObservers();
	}
}
