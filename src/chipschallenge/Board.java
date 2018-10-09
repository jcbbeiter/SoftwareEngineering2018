package chipschallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Board implements Observer {
	
	// level instance variables
	private int rows;
	private int cols;
	private String currentLevelName;
	private String levelTitle;
	private String nextLevelName;
	private GridObject[][] grid;
	private int chipsLeft;
	private Text chipsLeftText;
	private HashSet<Character> keys;
	
	private int gridSize;
	private int cellSize;
	
	private AnchorPane pane;
	
	private static Board instance = null;
	protected Board() {}
	
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}
	
	public void resetLevel() {
		loadLevel(currentLevelName);
	}
	
	public void loadNextLevel() {
		loadLevel(nextLevelName);
	}
	
	public int getChipsLeft() {
		return chipsLeft;
	}
	
	public void collectKey(char color) {
		keys.add(color);
	}
	
	public boolean keyCollected(char color) {
		return keys.contains(color);
	}
	
	// load level from file
	public void loadLevel(String levelName) {
		ChipsChallenge.resetPane(pane);
		File file = new File("bin\\chiplevels\\"+levelName);
		currentLevelName = levelName;
		BufferedReader reader = null;
		
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String header = reader.readLine();
		    List<String> headerArgs = Arrays.asList(header.split(","));
		    // get arguments from header of file
		    rows = Integer.parseInt(headerArgs.get(0));
		    cols = Integer.parseInt(headerArgs.get(1));
		    levelTitle = headerArgs.get(2);
		    nextLevelName = headerArgs.get(3);
		    keys = new HashSet<Character>();
		    
		    chipsLeftText = ChipsChallenge.writeText(pane,levelTitle);
		    
		    grid = new GridObject[rows][cols];
		    chipsLeft = 0;
		    
		    // read in line by line and split on commas
		    // the chars represent what the cell contains
		    // some objects have a second argument (e.g. key's color, pusher's direction)
		    for (int r = 0; r < rows; r++) {
		    	String line = reader.readLine();
		    	List<String> cells = Arrays.asList(line.split(","));
		    	for (int c = 0; c < cols; c++) {
		    		switch(cells.get(c).charAt(0)) {
		    		case 'x':
		    			grid[r][c] = new Wall(cellSize);
		    			break;
		    		case 'o':
		    		case 'O':
		    			grid[r][c] = null;
		    			break;
		    		case 'C':
		    			Chip.getInstance().setRow(r);
		    			Chip.getInstance().setCol(c);
		    			break;
		    		case 'c':
		    			chipsLeft++;
		    			grid[r][c] = new ChipItem(cellSize);
		    			break;
		    		case 'P':
		    			grid[r][c] = new Pusher(cellSize,cells.get(c).charAt(1));
		    			break;
		    		case 'W':
		    			grid[r][c] = new ChipWall(cellSize);
		    			break;
		    		case 'p':
		    			grid[r][c] = new Portal(cellSize);
		    			break;
		    		case 'k':
		    		case 'K':
		    			grid[r][c] = new Key(cellSize,cells.get(c).charAt(1));
		    			break;
		    		case 'L':
		    			grid[r][c] = new Lock(cellSize,cells.get(c).charAt(1));
		    		default:
		    		}
		    		
		    	}
		    }
		    
		    // add image views to pane
		    for (int r = 0; r < rows; r++) {
		    	for (int c = 0; c < rows; c++) {
		    		if (grid[r][c] != null) {
		    			pane.getChildren().add(grid[r][c].getImageView());
		    		}
		    	}
		    }
		    chipsLeftText.setText(String.format("Chips Left: %d",chipsLeft));
			Chip.getInstance().reset();
		    drawBoard();
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
	}
	
	// remove object from grid; if it's a chip, update count
	public void removeObject(int r, int c) {
		if (grid[r][c] instanceof ChipItem) {
			chipsLeft--;
			chipsLeftText.setText(String.format("Chips Left: %d",chipsLeft));
			
			if (chipsLeft == 0) {
				chipsLeftText.setFill(Color.GREEN);
			}
		}
		grid[r][c] = null;
	}
	
	// draw the 9x9 view of the board
	public void drawBoard() {
		int chipR = Chip.getInstance().getRow();
		int chipC = Chip.getInstance().getCol();

		
		// calculate which row/column is the top left of the visible square
		int buffer = gridSize/2;
		int topLeftRow = chipR-buffer;
		int topLeftCol = chipC-buffer;
		
		if (topLeftRow > rows-gridSize) {
			topLeftRow = rows-gridSize;
		} else if (topLeftRow < 0) {
			topLeftRow = 0;
		}
		
		if (topLeftCol > cols-gridSize) {
			topLeftCol = cols-gridSize;
		} else if (topLeftCol < 0) {
			topLeftCol = 0;
		}
		
		ImageView chipView = Chip.getInstance().getImageView();
		chipView.setY(cellSize*(chipR-topLeftRow));
		chipView.setX(cellSize*(chipC-topLeftCol));
		
		// update locations of image views
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == null) {
					continue;
				}
				ImageView view = grid[r][c].getImageView();
				// put in spot relative to top left view
				view.setX(cellSize*(c-topLeftCol));
				view.setY(cellSize*(r-topLeftRow));
				// if it's out of bounds, set it far away so it's not seen
				if (c < 0 || r < 0 || c-topLeftCol >= gridSize || r-topLeftRow >= gridSize) {
					view.setX(-1000);
					view.setY(-1000);
				}
			}
		}
		

	}
	
	// answer if chip can enter a square (asks the GridObject)
	public boolean canEnter(Object o, int r, int c) {
		GridObject obj = grid[r][c];
		if (obj == null) {
			return true;
		} else {
			return obj.canEnter();
		}
	}
	
	public void setSizes(int grid, int cell) {
		gridSize = grid;
		cellSize = cell;
	}

	@Override
	public void update(Observable o, Object arg1) {

		// make sure it's a Chip moving update
		if ((Object)o instanceof Chip) {

			// get Chip's new location
			int chipR = Chip.getInstance().getRow();
			int chipC = Chip.getInstance().getCol();
			
			// if there's a GridObject there, call its onEnter function
			if (grid[chipR][chipC] != null) {
				grid[chipR][chipC].onEnter(chipR, chipC);
			}
			
			// draw the new board
			drawBoard();
		}
	}
}
