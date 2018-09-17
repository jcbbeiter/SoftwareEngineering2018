package edu.nd.se2018.homework.hwk4;

import java.awt.Point;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;

import java.util.concurrent.ThreadLocalRandom;

public class OceanMap {
	private enum gridContents {
		EMPTY, ISLAND, SHIP
	}
	private gridContents[][] oceanGrid = new gridContents[25][25];
	private PirateShip[] pirates;
	public ColumbusShip ship;
	final int dimensions = 25;
	
	public OceanMap(int numPirates, int numIslands) {
		generateMap(numPirates, numIslands);
	}
	
	private void generateMap(int numPirates, int numIslands) {
		for (int x = 0; x < dimensions; x++ ) {
			for (int y = 0; y < dimensions; y++) {
				oceanGrid[y][x] = gridContents.EMPTY;
			}
		}
		for (int i = 0; i < numIslands; i++) {
			Point p = getRandomEmptyPosition();
			oceanGrid[p.y][p.x] = gridContents.ISLAND; // island 
		}
		
		Point p = getRandomEmptyPosition();
		ship = new ColumbusShip(p);
		oceanGrid[p.y][p.x] = gridContents.SHIP; 
		
		pirates = new PirateShip[numPirates];
		for (int i = 0; i < numPirates; i++) {
			p = getRandomEmptyPosition();
			pirates[i] = new PirateShip(p, this);
			ship.addObserver(pirates[i]);
			oceanGrid[p.y][p.x]= gridContents.SHIP; 
		}
	}
	
	private Point getRandomEmptyPosition() {
		Point p = new Point(0,0);
		do {
			p.x = ThreadLocalRandom.current().nextInt(0,25);
			p.y = ThreadLocalRandom.current().nextInt(0,25);
		} while(oceanGrid[p.y][p.x] != gridContents.EMPTY);
		
		return p;
	}
	
	public Point getShipLocation() {
		return ship.getLocation();
	}
	
	public Point[] getPirateLocations() {
		Point[] result = new Point[pirates.length];
		for (int i = 0; i < pirates.length; i++) {
			result[i] = pirates[i].getLocation();
		}
		
		return result;
	}
	
	public boolean sailNorth(Ship s) {
		Point newPosition = new Point(s.location);
		newPosition.y -= 1;
		return moveTo(newPosition,s);
	}
	public boolean sailSouth(Ship s) {
		Point newPosition = new Point(s.location);
		newPosition.y += 1;
		return moveTo(newPosition, s);
	}
	public boolean sailEast(Ship s) {
		Point newPosition = new Point(s.location);
		newPosition.x += 1;
		return moveTo(newPosition,s);
	}
	public boolean sailWest(Ship s) {
		Point newPosition = new Point(s.location);
		newPosition.x -= 1;
		return moveTo(newPosition,s);
	}
	
	// moves a ship to the new position if it's valid
	public boolean moveTo(Point newPosition, Ship s) {
		// can't go out of bounds
		if (newPosition.x < 0 || newPosition.x > 24 || newPosition.y < 0 || newPosition.y > 24) {
			return false;
		}
		
		// can only move if the square is empty ocean
		if (oceanGrid[newPosition.y][newPosition.x] == gridContents.EMPTY) {
			oceanGrid[s.location.y][s.location.x] = gridContents.EMPTY;
			oceanGrid[newPosition.y][newPosition.x] = gridContents.SHIP; 
			s.setLocation(newPosition);
			
			
			return true;
		}
		
		return false;
		
	}
	
	public void drawMap(ObservableList<Node> root, int scale) {
		for (int x = 0; x < dimensions; x++ ) {
			for (int y = 0; y < dimensions; y++) {
				Rectangle rect = new Rectangle(x*scale, y*scale, scale, scale);
				rect.setStroke(Color.BLACK);
				if (oceanGrid[y][x] == gridContents.ISLAND)
					rect.setFill(Color.GREEN);
				else
					rect.setFill(Color.PALETURQUOISE);
				root.add(rect);
			}
		}
	}
}
