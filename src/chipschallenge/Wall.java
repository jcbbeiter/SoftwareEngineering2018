package chipschallenge;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends GridObject {

	public Wall(int size) {
		Image wallImage = new Image("chiptextures\\Wall.png",size,size,false,false);
		view = new ImageView(wallImage);
	}
	@Override
	boolean canEnter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	ImageView getImageView() {
		return view;
	}

}
