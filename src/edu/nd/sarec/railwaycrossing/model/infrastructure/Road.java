package edu.nd.sarec.railwaycrossing.model.infrastructure;

import java.awt.Point;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
import edu.nd.sarec.railwaycrossing.model.vehicles.Car;
import edu.nd.sarec.railwaycrossing.model.vehicles.CarFactory;

/**
 * Represents a single road
 * @author jane
 *
 */
public class Road {
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private CarFactory carFactory;
	Direction direction;
	Collection<CrossingGate> gates;
	boolean clearEnds = false;
	int roadSize;
	private ArrayBlockingQueue<Car> cars;
	
	public Road(){}
	
	public Road(Point start, Point end, Direction direction, boolean buildCarFactory, boolean clearEnds){
		startX = start.x;
		startY = start.y;
		endX = end.x;
		endY = end.y;
		roadSize = 18;
		
		this.direction = direction;
		gates = new Vector<CrossingGate>();
		this.clearEnds = clearEnds;
		
		cars = new ArrayBlockingQueue<Car>(100);
		
	}
	
	// Adds a gate to a road
	// In case a new gate is added after the factory is assigned, we reassign factory
	// The factory needs to know all gates on the road in order to register each car as an observer.
	public void assignGate(CrossingGate gate){
		gates.add(gate);
		//if (carFactory == null)
		//	carFactory = new CarFactory(direction, new Point(startX-roadSize/2,startY), gates, this);  // allows additional gates.  Needs fixing
	}
	
	public void addCarFactory(Road crossingRoad, Road endRoad){
		if (carFactory == null) // We only allow one
			carFactory = new CarFactory(direction, new Point(startX-roadSize/2,startY), gates, this,crossingRoad,endRoad);
	}
	
	public void addCar(Car c) {
		cars.add(c);
		updateObserving();
	}
	
	public void addCarToMiddle(Car newCar) {
		ArrayList<Car> temp = new ArrayList<Car>();
		cars.drainTo(temp);
		
		for (Car c : temp) {
			if (newCar != null && newCar.getVehicleY() > c.getVehicleY()) {
				cars.add(newCar);
				newCar = null;
			}
			cars.add(c);
		}
		updateObserving();
	}
	
	public void removeCar(Car c) {
		cars.remove(c);
		updateObserving();
	}
	
	public void updateObserving() {
		// removes from the front any cars who have gone off-screen
		while (cars.size() > 0 && cars.peek().getVehicleY() > 1020) {
			cars.poll();
			cars.peek().removeLeadCar();
		}
		// clear observing links
		for (Car c : cars) {
			c.deleteObservers();	

		}
		// go from front to back, assigning observer relationships
		Car frontCar = null;
		for (Car c : cars) {
			if (frontCar != null) {
				frontCar.addObserver(c);
			}
			frontCar = c;
		}
		if (cars.size() > 0) {
			cars.peek().removeLeadCar();
		}
	}
	
	public CarFactory getCarFactory(){
		return carFactory;
	}
	
	public int getStartX(){
		return startX;
	}
	
	public int getEndX(){
		return endX;
	}
	
	public int getStartY(){
		return startY;
	}
	
	public int getEndY(){
		return endY;
	} 
	
	public Direction getDirection(){
		return direction;
	}
	
	public boolean getClearEnds(){
		return clearEnds;
	}
	
	public int getRoadWidth(){
		return roadSize;
	}
}