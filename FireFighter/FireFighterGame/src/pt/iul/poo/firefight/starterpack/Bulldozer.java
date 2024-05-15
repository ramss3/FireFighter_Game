package pt.iul.poo.firefight.starterpack;
import java.awt.event.KeyEvent;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends GameElement implements Movable {

	String direction;

	public Bulldozer(Point2D position) {
		super(position, "bulldozer", 4);
		this.direction = "_up";
	}

	public void setDir(String dir) {
		this.direction = dir;
	}

	@Override
	public String getName() {
		return super.getName() + direction;
	}

	@Override
	public void move(int key) {

		GameEngine.getInstance().fireman = new Fireman(getPosition());

		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {
			Direction dir = Direction.directionFor(key);
			Point2D newPosition = getPosition().plus(dir.asVector());
			Plant plant = GameEngine.getInstance().getPlantAtPosition(newPosition);
			OpenLand openLand = new OpenLand(newPosition);
			Burnt burnt = new Burnt(newPosition, "burnt", 1);
			
			if (canMoveTo(newPosition) && !GameEngine.getInstance().isFireAtPosition(newPosition)) {
				setPosition(newPosition);
				this.setDir(direction(dir));
			}
			
			if(GameEngine.getInstance().isPlantAtPosition(newPosition) && !GameEngine.getInstance().isFireAtPosition(newPosition)) {
				
				GameEngine.getInstance().removeImage(plant);	
				GameEngine.getInstance().addImage(openLand);
				
			} else if(GameEngine.getInstance().isBurntAtPosition(newPosition)) {
				
				GameEngine.getInstance().removeImage(burnt);	
				GameEngine.getInstance().addImage(openLand);
			}
		}
	}

	public String direction(Direction dir) {
		direction="";
		switch(dir) {
		case UP: 
			return "_up";
		case DOWN:
			return "_down";
		case LEFT:
			return "_left";
		case RIGHT:
			return "_right";
		}
		return "_up";
	}


	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}

}

