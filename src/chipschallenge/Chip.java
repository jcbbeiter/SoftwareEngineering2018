package chipschallenge;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Chip extends Observable {
	
	private Image faceUp;
	private Image faceLeft;
	private Image faceRight;
	private Image faceDown;
	private ImageView view;
	
	private int row;
	private int col;

	private static Chip instance = null;
	private static int imageSize;
	
	protected Chip() {
		faceUp = new Image("chiptextures\\chipUp.png",imageSize,imageSize,false,false);
		faceLeft = new Image("chiptextures\\chipLeft.png",imageSize,imageSize,false,false);
		faceRight = new Image("chiptextures\\chipRight.png",imageSize,imageSize,false,false);
		faceDown = new Image("chiptextures\\chipDown.png",imageSize,imageSize,false,false);
		
		view = new ImageView(faceDown);
	}
	
	public void move(KeyCode code) {
		int nextr = row;
		int nextc = col;
		switch(code) {
		case LEFT:
			view.setImage(faceLeft);
			nextc = col-1;
			break;
		case RIGHT:
			view.setImage(faceRight);
			nextc = col+1;
			break;
		case UP:
			view.setImage(faceUp);
			nextr = row-1;
			break;
		case DOWN:
			view.setImage(faceDown);
			nextr = row+1;
			break;
		default:
			break;
		}
		
		if (Board.getInstance().canEnter(this,nextr,nextc)) {
			row = nextr;
			col = nextc;
			setChanged();
			notifyObservers();
			view.toFront();
		}
		
	}
	
	public static Chip getInstance() {
		if (instance == null) {
			instance = new Chip();
		}
		return instance;
	}
	
	public ImageView getImageView() {
		return view;
	}
	
	public static void setImageSize(int size) {
		imageSize = size;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int c) {
		col = c;
	}
	
	public void reset() {
		view.setImage(faceDown);
	}
}
