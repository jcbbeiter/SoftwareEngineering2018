package chipschallenge;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChipsChallenge extends Application {

	
	private final int gridSize = 9;		// how many cells are visible
	private final int cellSize = 75;	// size of each cell
	private final int sidebarWidth = 300;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane myPane = new AnchorPane();
		Scene scene = new Scene(myPane,gridSize*cellSize + sidebarWidth,gridSize*cellSize);
		
		
		// create blank tile images
		Image blankTileImage = new Image("chiptextures\\BlankTile.png",cellSize,cellSize,false,false);
		for (int r = 0; r < gridSize; r++) {
			for (int c = 0; c < gridSize; c++) {
				ImageView tileView = new ImageView(blankTileImage);
				tileView.setX(r*cellSize);
				tileView.setY(c*cellSize);
				myPane.getChildren().add(tileView);
			}
		}
		
		Board.getInstance().setSizes(gridSize,cellSize);
		Chip.setImageSize(cellSize);
		myPane.getChildren().add(Chip.getInstance().getImageView());
		Board.getInstance().loadLevel("Level1.txt",myPane);
		Board.getInstance().drawBoard();
		Chip.getInstance().addObserver(Board.getInstance());
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chip's Challenge");
		primaryStage.show();
		
		startGame(scene,primaryStage);
	}
	
	private void startGame(Scene scene, Stage primaryStage) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()){
				case RIGHT:
				case LEFT:
				case UP:
				case DOWN:
					Chip.getInstance().move(ke.getCode());
					break;
				case ESCAPE:
					primaryStage.close();
					break;
				default:
					break;
			} \]
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
