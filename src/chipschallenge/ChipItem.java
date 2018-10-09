package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChipItem extends GridObject {
	
	public ChipItem(int size) {
		Image chipImage = new Image("chiptextures\\chipItem.png",size,size,false,false);
		view = new ImageView(chipImage);
	}
	@Override
	boolean canEnter() {
		return true;
	}

	@Override
	void onEnter(int r, int c) {
		view.setImage(null);
		Board.getInstance().removeObject(r,c);
	}

}
