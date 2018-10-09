package chipschallenge;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChipsChallenge extends Application {

	
	private final static int gridSize = 9;		// how many cells are visible
	private final static int cellSize = 75;	// size of each cell
	private final static int sidebarWidth = 300;
	private final static int borderWidth = 4;
	private final static int keyCellSize = 50;
	private AnchorPane myPane;
	
	// clear pane and create constants (like sidebar)
	// to be called before loading any level
	public static void resetPane(AnchorPane pane) {
		pane.getChildren().clear();
		// create blank tile images
		Image blankTileImage = new Image("chiptextures\\BlankTile.png",cellSize,cellSize,false,false);
		for (int r = 0; r < gridSize; r++) {
			for (int c = 0; c < gridSize; c++) {
				ImageView tileView = new ImageView(blankTileImage);
				tileView.setX(r*cellSize);
				tileView.setY(c*cellSize);
				pane.getChildren().add(tileView);
				}
			}						
		
		// create border between play area and sidebar
		Line border = new Line(gridSize*cellSize + borderWidth/2,0,gridSize*cellSize + borderWidth/2,gridSize*cellSize);
		border.setStroke(Color.BLACK);
		border.setStrokeWidth(borderWidth);
		pane.getChildren().add(border);
		
		// create sidebar background
		Rectangle sidebar = new Rectangle(gridSize*cellSize+borderWidth,0,sidebarWidth-borderWidth,cellSize*gridSize);
		sidebar.setFill(Color.DARKGRAY);
		sidebar.setStroke(Color.LIGHTGRAY);
		sidebar.setStrokeType(StrokeType.INSIDE);
		sidebar.setStrokeWidth(4);
		pane.getChildren().add(sidebar);
		
		// create blank tiles for keys
		Image smallTileImage = new Image("chiptextures\\BlankTile.png",keyCellSize,keyCellSize,false,false);
		int keyCellStartX = gridSize*cellSize + (sidebarWidth-borderWidth - 4*keyCellSize)/2;
		for (int i = 0; i < 4; i++) {
			ImageView tileView = new ImageView(smallTileImage);
			tileView.setX(i*keyCellSize + keyCellStartX);
			tileView.setY(575);
			pane.getChildren().add(tileView);
			tileView.toFront();
		}
		
		// create keys label
		Text keysLabel = new Text(750, 560, "Keys Collected");
		keysLabel.setFill(Color.BLACK);
		keysLabel.setFont(Font.font("Arial",20));
		pane.getChildren().add(keysLabel);
		
		// make chip visible
		pane.getChildren().add(Chip.getInstance().getImageView());
	}
	
	// text positions will be hard-coded just for ease of use
	// if I were developing this more I would properly set up a nested pane and center things
	// within it
	// returns a reference to the chips left text so the Board can update it
	public static Text writeText(AnchorPane pane, String titleString) {
		int playAreaSize = gridSize*cellSize+borderWidth;
		Text title = new Text(playAreaSize+80,100,titleString);
		title.setFill(Color.BLACK);
		title.setFont(Font.font("Arial",40));
		pane.getChildren().add(title);
		
		Text resetMessage = new Text(playAreaSize+90,140,"R to restart");
		resetMessage.setFill(Color.BLACK);
		resetMessage.setFont(Font.font("Arial",20));
		pane.getChildren().add(resetMessage);
		
		Text chipsLeftText = new Text(playAreaSize+40,500,"placeholder");
		chipsLeftText.setFill(Color.RED);
		chipsLeftText.setFont(Font.font("Arial",40));
		pane.getChildren().add(chipsLeftText);
		
		return chipsLeftText;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		myPane = new AnchorPane();
		Scene scene = new Scene(myPane,gridSize*cellSize + sidebarWidth,gridSize*cellSize);
		
		Board.getInstance().setSizes(gridSize,cellSize);
		Chip.setImageSize(cellSize);
		
		Board.getInstance().setPane(myPane);
		Board.getInstance().loadLevel("Level1.txt");
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
				case R:
					resetPane(myPane);
					Board.getInstance().resetLevel();
					break;
				default:
					break;
			}
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
