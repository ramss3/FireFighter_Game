package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile, Updatable {

	private Point2D position;
	private String name;
	private int layer;

	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;
	}
	
	@Override
	public void update() {
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int getLayer() {
		return this.layer;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public static GameElement create(String tipo, Point2D info) {
		switch(tipo) {
		case "p": return new Pine(info);
		case "e": return new Eucaliptus(info);
		case "m": return new Grass(info);
		case "_": return new OpenLand(info);
		default: throw new IllegalArgumentException();
		}
	}
}


