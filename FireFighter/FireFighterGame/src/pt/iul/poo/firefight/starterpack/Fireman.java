package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.awt.event.KeyEvent;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends GameElement implements Movable {
	

	public Fireman(Point2D position) {
		super(position, "fireman", 5);
	}

	// Move o bombeiro 
	@Override
	public void move(int key) {
			
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {
			Direction dir = Direction.directionFor(key);
			Point2D newPosition = getPosition().plus(dir.asVector());
			Fire fire = GameEngine.getInstance().getFireAtPosition(newPosition);
			Water water = null;

			if (canMoveTo(newPosition) && !GameEngine.getInstance().isFireAtPosition(newPosition) ) {
				setPosition(newPosition);
			} else {
				if(fire != null) {
					water = new Water(newPosition, direction(dir));
					if(water != null) {
						GameEngine.getInstance().addImage(water);
						water.updateAgua();	
						GameEngine.getInstance().removeImage(fire);
					}
				}
			}
			
			if(GameEngine.getInstance().isBulldozerAtPosition(newPosition)) {
				GameEngine.getInstance().movable = GameEngine.getInstance().bulldozer;
				ImageMatrixGUI.getInstance().removeImage(this);
			}
		}
	}

	public String direction(Direction dir) {
		switch(dir) {
		case UP:
			return "_up";
		case DOWN:
			return "_down";
		case LEFT:
			return "_left";
		case RIGHT:
			return "_right";
		default: return "";
		}
	}
	

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}


}
