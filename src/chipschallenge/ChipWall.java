package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChipWall extends GridObject {

	public ChipWall(int size) {
		Image chipWallImage = new Image("chiptextures\\ChipWall.png",size,size,false,false);
		view = new ImageView(chipWallImage);
	}
	
	@Override
	boolean canEnter() {
		return (Board.getInstance().getChipsLeft() == 0);
	}

	@Override
	void onEnter(int r, int c) {
		view.setImage(null);
		Board.getInstance().removeObject(r,c);
	}

}
