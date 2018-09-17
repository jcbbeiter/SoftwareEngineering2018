package edu.nd.se2018.homework.hwk4;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class PirateShip extends Ship implements Observer {
	OceanMap ocean;

	public PirateShip(Point p, OceanMap o) {
		location = p;
		ocean = o;
	}
	
	public void update(Observable o, Object arg) {
		Point target = ((PirateNotifier)o).location;
		
		// prefer to move vertically
		if (target.y != location.y) {
			if (target.y < location.y) {
				if (ocean.sailNorth(this))
					return;
			}
			else if (ocean.sailSouth(this))
				return;
		}
		
		// if can't or shouldn't move vertically, move horizontally
		if (target.x != location.x) {
			if (target.x < location.x) {
				if (ocean.sailWest(this))
					return;
			}
			else if(ocean.sailEast(this))
				return;
		}
		
	}
}
