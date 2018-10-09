package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Portal extends GridObject {

	public Portal(int size) {
		Image portalImage = new Image("chiptextures\\portal.png",size,size,false,false);
		view = new ImageView(portalImage);
	}
	
	@Override
	boolean canEnter() {
		return true;
	}

	@Override
	void onEnter(int r, int c) {
		Board.getInstance().loadNextLevel();
	}

}
