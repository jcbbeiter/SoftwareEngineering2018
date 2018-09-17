package edu.nd.se2018.homework.hwk4;

import java.awt.Point;

public class Ship {
	protected Point location;
	
	public Ship() {
		location = new Point(0,0);
	}
	public Ship(int x, int y) {
		location = new Point(x, y);
	}
	
	public Ship(Point p) {
		location = p;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setLocation(Point p) {
		location = p;
	}
}
