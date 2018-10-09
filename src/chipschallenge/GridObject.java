package chipschallenge;

import javafx.scene.image.ImageView;

public abstract class GridObject {

	protected ImageView view;
	
	abstract boolean canEnter();
	abstract void onEnter(int r, int c);
	
	public ImageView getImageView() { return view; }
}
