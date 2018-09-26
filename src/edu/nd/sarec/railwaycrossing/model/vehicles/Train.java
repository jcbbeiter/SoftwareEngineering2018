package edu.nd.sarec.railwaycrossing.model.vehicles;

import java.util.Observable;

import edu.nd.sarec.railwaycrossing.model.infrastructure.Direction;

import java.util.HashSet;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the train entity object
 * @author jane
 *
 */
public class Train extends Observable implements IVehicle{
	private double currentX = 0;
	private double currentY = 0;
	private double originalX = 0;
	private Image img;
	private ImageView imgView;
	private int trainLength = 35;
	
	private HashSet<String> stations;
	private Direction direction;
	private int speed;
	
	public Train(int x, int y, Direction direction){
		this.currentX = x;
		this.currentY = y;
		originalX = x;
		String filename;
		if (direction == Direction.WEST) {
			filename = "images\\Train.PNG";
		} else {
			filename = "images\\Train_Reversed.PNG";
		}
		img = new Image(filename,120,trainLength,false,false);
		imgView = new ImageView(img);
		imgView.setX(currentX);
		imgView.setY(currentY);
		stations = new HashSet<String>();
		this.direction = direction;
		this.speed = ThreadLocalRandom.current().nextInt(1,3);
	}
	
	public double getVehicleX(){
		return currentX;
	}
	
	public double getVehicleY(){
		return currentY;
	}
	
	public void move(){
		if (this.direction == Direction.WEST) {
			currentX-= speed;
		} else if (this.direction == Direction.EAST) {
			this.currentX+= speed;
		}
		imgView.setX(currentX);
		setChanged();
		notifyObservers();
	}
	
	public boolean offScreen(){
		if (currentX < -200 || currentX > 1400)
			return true;
		else
			return false;				
	}
	
	public boolean inStation(String station) {
		return stations.contains(station);
	}
	
	public void setInStation(String station) {
		stations.add(station);
	}
	
	public void setNotInStation(String station) {
		stations.remove(station);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void reset(){
		currentX = originalX;
	}

	//@Override
	public Node getImageView() {
		return imgView;
	}
}