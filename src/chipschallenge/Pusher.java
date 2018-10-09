package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Pusher extends GridObject {
	
	KeyCode pushCode;
	
	public Pusher(int size, char direction) {
		Image pusherImage;
		switch (direction) {
		case 'U':
		case 'u':
			pushCode = KeyCode.UP;
			pusherImage = new Image("chiptextures\\pusherUp.png",size,size,false,false);
			break;
		case 'D':
		case 'd':
			pushCode = KeyCode.DOWN;
			pusherImage = new Image("chiptextures\\pusherDown.png",size,size,false,false);
			break;
		case 'L':
		case 'l':
			pushCode = KeyCode.LEFT;
			pusherImage = new Image("chiptextures\\pusherLeft.png",size,size,false,false);
			break;
		case 'R':
		case 'r':
		default:
			pushCode = KeyCode.RIGHT;
			pusherImage = new Image("chiptextures\\pusherRight.png",size,size,false,false);
			break;
		}
		
		view = new ImageView(pusherImage);
	}
	@Override
	boolean canEnter() {
		return true;
	}

	@Override
	void onEnter(int r, int c) {		
		Chip.getInstance().move(pushCode);

	}

}
