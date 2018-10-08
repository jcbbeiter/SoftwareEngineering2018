package chipschallenge;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Board implements Observer {
	
	// level instance variables
	private int rows;
	private int cols;
	private String nextLevelName;
	private GridObject[][] grid;
	
	private int gridSize;
	private int cellSize;
	
	private static Board instance = null;
	protected Board() {}
	
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	public void loadLevel(String levelName, AnchorPane pane) {
		File file = new File("bin\\chiplevels\\"+levelName);
		BufferedReader reader = null;
		
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String header = reader.readLine();
		    List<String> headerArgs = Arrays.asList(header.split(","));
		    rows = Integer.parseInt(headerArgs.get(0));
		    cols = Integer.parseInt(headerArgs.get(1));
		    nextLevelName = headerArgs.get(2);
		    
		    grid = new GridObject[rows][cols];
		    
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
		    		default:
		    		}
		    		
		    	}
		    }
		    
		    for (int r = 0; r < rows; r++) {
		    	for (int c = 0; c < rows; c++) {
		    		if (grid[r][c] != null) {
		    			pane.getChildren().add(grid[r][c].getImageView());
		    		}
		    	}
		    }
		    
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
	
	public void drawBoard() {
		int chipR = Chip.getInstance().getRow();
		int chipC = Chip.getInstance().getCol();

		
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
		
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == null) {
					continue;
				}
				ImageView view = grid[r][c].getImageView();
				view.setX(cellSize*(c-topLeftCol));
				view.setY(cellSize*(r-topLeftRow));
				if (c < 0 || r < 0 || c-topLeftCol >= gridSize || r-topLeftRow >= gridSize) {
					view.setX(-1000);
					view.setY(-1000);
				}
			}
		}
		

	}
	
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
		if ((Object)o instanceof Chip) {
			drawBoard();
		}
	}
}
