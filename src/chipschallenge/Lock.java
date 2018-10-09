package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Lock extends GridObject {
	
	char color;
	
	public Lock(int size, char color) {
		Image lockImage;
		switch(color) {
		case 'R':
		case 'r':
		default:
			this.color = 'R';
			lockImage = new Image("chiptextures\\redKeyWall.png",size,size,true,true);
			break;
		case 'B':
		case 'b':
			this.color = 'B';
			lockImage = new Image("chiptextures\\blueKeyWall.png",size,size,true,true);
			break;
		case 'G':
		case 'g':
			this.color = 'G';
			lockImage = new Image("chiptextures\\greenKeyWall.png",size,size,true,true);
			break;
		case 'Y':
		case 'y':
			this.color = 'Y';
			lockImage = new Image("chiptextures\\yellowKeyWall.png",size,size,true,true);
			break;
		}
		view = new ImageView(lockImage);
	}
	
	@Override
	boolean canEnter() {
		return Board.getInstance().keyCollected(color);
	}

	@Override
	void onEnter(int r, int c) {
		view.setImage(null);
		Board.getInstance().removeObject(r,c);
	}

}
