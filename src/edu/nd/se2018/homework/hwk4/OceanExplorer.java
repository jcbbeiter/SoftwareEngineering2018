package edu.nd.se2018.homework.hwk4;

import java.awt.Point;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class OceanExplorer extends Application {
	private ImageView shipImageView;
	private ImageView[] pirateImageViews;
	OceanMap map;
	private int dimensions = 750;
	private int scale = 30;
	private final int numPirates = 2;
	private final int numIslands = 25;
	
	@Override
	public void start(Stage oceanStage) throws Exception {
		AnchorPane myPane = new AnchorPane();
		Scene scene = new Scene(myPane,dimensions,dimensions);
		int scale = 30;

		map = new OceanMap(numPirates, numIslands);
		map.drawMap(myPane.getChildren(),scale);
		
		// create ship image vieiw
		Image shipImage = new Image("images\\ColumbusShip.png",scale,scale,true,true);
		shipImageView = new ImageView(shipImage);
		shipImageView.setX(map.getShipLocation().x * scale);
		shipImageView.setY(map.getShipLocation().y * scale);
		myPane.getChildren().add(shipImageView);
		
		// create pirate image views
		pirateImageViews = new ImageView[numPirates];
		Image pirateImage = new Image("images\\pirateship.gif",scale,scale,true,true);
		Point[] pirateLocations = map.getPirateLocations();
		for (int i = 0; i < numPirates; i++) {
			Point p = pirateLocations[i];
			pirateImageViews[i] = new ImageView(pirateImage);
			pirateImageViews[i].setX(p.x * scale);
			pirateImageViews[i].setY(p.y * scale);
			myPane.getChildren().add(pirateImageViews[i]);
		}
		
		// set parameters and show
		oceanStage.setScene(scene);
		oceanStage.setTitle("Columbus Game");
		oceanStage.show();
		
		startSailing(scene);
	}
	
	private void startSailing(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()){
				case RIGHT:
					map.sailEast(map.ship);
					break;
				case LEFT:
					map.sailWest(map.ship);
					break;
				case UP:
					map.sailNorth(map.ship);
					break;
				case DOWN:
					map.sailSouth(map.ship);
					break;
				default:
					break;
			} 
			shipImageView.setX(map.getShipLocation().x*scale);
			shipImageView.setY(map.getShipLocation().y*scale);
			refreshPirates();
			}
			
		});
	}
	
	public void refreshPirates() {
		Point[] pirateLocations = map.getPirateLocations();
		for (int i = 0; i < numPirates; i++) {
			Point p = pirateLocations[i];
			pirateImageViews[i].setX(p.x * scale);
			pirateImageViews[i].setY(p.y * scale);
		}
	}

	public static void main(String[] args) {
		launch(args);
		
		

	}

}
