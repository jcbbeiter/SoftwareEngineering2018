package edu.nd.sarec.railwaycrossing.model.vehicles;

import java.util.Observable;
import java.util.Observer;

import edu.nd.sarec.railwaycrossing.model.infrastructure.Road;
import edu.nd.sarec.railwaycrossing.model.infrastructure.Direction;
import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
import edu.nd.sarec.railwaycrossing.view.CarImageSelector;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Represents Car object
 * @author jane
 *
 */
public class Car extends Observable implements IVehicle, Observer{
	private ImageView ivCar;
	private double currentX = 0;
	private double currentY = 0;
	private double originalY = 0;
	private boolean gateDown = false;
	private double leadCarY = -1;  // Current Y position of car directly infront of this one
	private double leadCarX = -1;
	private double speed = 0.5;
	private Road currentRoad;
	private Road crossingRoad;
	private Road endRoad;
		
	/**
	 * Constructor
	 * @param x initial x coordinate of car
	 * @param y initial y coordinate of car
	 */
	public Car(int x, int y, Road road, Road crossingRoad, Road endRoad){
		this.currentX = x;
		this.currentY = y;
		originalY = y;
		ivCar = new ImageView(CarImageSelector.getImage());
		ivCar.setX(getVehicleX());
		ivCar.setY(getVehicleY());
		this.currentRoad = road;
		this.crossingRoad = crossingRoad;
		this.endRoad = endRoad;
	}
		
	@Override
	public Node getImageView() {
		return ivCar;
	}
	
	public boolean gateIsClosed(){
		return gateDown;
	}
	
	public double getVehicleX(){
		return currentX;
	}
	public double getVehicleY(){
		return currentY;
	}
	
	public void move(){
		boolean canMove = true; 
		
		// First case.  Car is at the front of the stopping line.
		if (gateDown && getVehicleY() < 430 && getVehicleY()> 390)
			canMove = false;
		
		// Second case. Car is too close too other car.
		if ((leadCarY != -1 && leadCarX != -1)  && getDistanceToLeadCar() < 50)
			canMove = false;
		
		if (canMove){
			// if it's on the right road, check if should switch to crossing
			if (currentRoad != crossingRoad && currentRoad != endRoad) {
				if (Math.abs(currentY - crossingRoad.getStartY()) < speed/2 && Math.random() > 0.5) {
					currentRoad.removeCar(this);
					this.currentRoad = crossingRoad;
					currentRoad.addCar(this);
					this.currentY = crossingRoad.getStartY()-9;
				}
			}
			
			// if it's on the crossing road, check if it should switch to the end
			if (currentRoad == crossingRoad) {
				if (Math.abs(currentX - endRoad.getStartX()) < speed/2) {
					currentRoad.removeCar(this);
					this.currentRoad = endRoad;
					currentRoad.addCarToMiddle(this); // change this
					this.currentX = crossingRoad.getStartX()-24;
				}
			}
			
			switch(currentRoad.getDirection()) {
			case WEST: currentX -= speed;
			break;
			case EAST: currentX += speed;
			break;
			case NORTH: currentY -= speed;
			break;
			case SOUTH: currentY += speed;
			default:
				break;
			}
			ivCar.setY(currentY);
			ivCar.setX(currentX);
			setChanged();
			notifyObservers();

		}
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public void setGateDownFlag(boolean gateDown){
		this.gateDown = gateDown;
	}
	
	public boolean offScreen(){
		if (currentY > 1020)
			return true;
		else
			return false;			
	}
		
	public void reset(){
		currentY = originalY;
	}
	
	public double getDistanceToLeadCar(){
		return Math.abs(leadCarY-getVehicleY()) + Math.abs(leadCarX-getVehicleX());
	}
	
	public void removeLeadCar(){
		leadCarY = -1;
		leadCarX = -1;
	}
	

	@Override
	public void update(Observable o, Object arg1) {
		if (o instanceof Car){
			leadCarY = (((Car)o).getVehicleY());
			leadCarX = (((Car)o).getVehicleX());
			// if this car is on the eastern road in the one it's following isn't (has turned), ignore
			if (leadCarY > 1020) {
				leadCarY = -1;
				leadCarX = -1;
			}
		}
			
		if (o instanceof CrossingGate){
			CrossingGate gate = (CrossingGate)o;
			if(gate.getTrafficCommand()=="STOP")			
				gateDown = true;
			else
				gateDown = false;
					
		}				
	}	
}
