package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Key extends GridObject {
	
	int keyIndex;
	Image smallImage;
	char color;

	public Key(int size, char color) {
		Image keyImage;
		switch(color) {
		case 'R':
		case 'r':
		default:
			this.color = 'R';
			keyIndex = 0;
			keyImage = new Image("chiptextures\\redKey.png",size,size,true,true);
			smallImage = new Image("chiptextures\\redKey.png",50,50,true,true);
			break;
		case 'B':
		case 'b':
			this.color = 'B';
			keyIndex = 1;
			keyImage = new Image("chiptextures\\blueKey.png",size,size,true,true);
			smallImage = new Image("chiptextures\\blueKey.png",50,50,true,true);
			break;
		case 'G':
		case 'g':
			this.color = 'G';
			keyIndex = 2;
			keyImage = new Image("chiptextures\\greenKey.png",size,size,true,true);
			smallImage = new Image("chiptextures\\greenKey.png",50,50,true,true);
			break;
		case 'Y':
		case 'y':
			this.color = 'Y';
			keyIndex = 3;
			keyImage = new Image("chiptextures\\yellowKey.png",size,size,true,true);
			smallImage = new Image("chiptextures\\yellowKey.png",50,50,true,true);
			break;
		}
		view = new ImageView(keyImage);
	}
	
	@Override
	boolean canEnter() {
		return true;
	}

	@Override
	void onEnter(int r, int c) {

		Board.getInstance().collectKey(color);
		Board.getInstance().removeObject(r, c);
		
		// move key image into the sidebar (inventory)
		view.setImage(smallImage);
		view.setX(723 + keyIndex*50);
		view.setY(575);
		
		
	}

}
