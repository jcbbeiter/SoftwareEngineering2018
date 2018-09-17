package edu.nd.se2018.homework.hwk4;

import java.awt.Point;
import java.util.Observable;

// I made the observable a member of Ship class because I opted for 
// polymorphic behavior between columbus' and the pirate ships
// and java doesn't support (direct) multiple inheritance
public class PirateNotifier extends Observable {
	public Point location;

	public void change() {
		setChanged();
	}

}
