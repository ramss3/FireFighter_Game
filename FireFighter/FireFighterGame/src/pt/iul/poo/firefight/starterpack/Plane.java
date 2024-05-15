package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Plane extends GameElement implements Movable, Updatable {

	public Plane(Point2D position) {
		super(position, "plane", 7);
	}

	@Override
	public void update() {
		move(KeyEvent.VK_UP);
		move(KeyEvent.VK_UP);
	}

	@Override
	public void move(int key) {

		Direction dir = Direction.directionFor(KeyEvent.VK_UP);
		Point2D newPosition = getPosition().plus(dir.asVector());

		if (canMoveTo(newPosition)) {

			setPosition(newPosition);
			
			if(GameEngine.getInstance().isFireAtPosition(newPosition)) {
				GameEngine.getInstance().removeImage(GameEngine.getInstance().getFireAtPosition(newPosition));
			}
			
		} else {
			removePlane();
		}
	}
	
	public void removePlane() {
		GameEngine.getInstance().removeImage(this);
		GameEngine.getInstance().plane = null;
	}


	public boolean canMoveTo(Point2D p) {

		if (p.getY() < 0) return false;
		return true;
	}

}
