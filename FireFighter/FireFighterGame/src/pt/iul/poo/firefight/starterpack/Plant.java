package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Plant extends GameElement implements Burnable {

	private double probabilidadeDeArder;
	private int nJogadas = 0;
	private Fire fire;

	public Plant(Point2D position, String name, int layer, double probabilidadeDeArder) {
		super(position, name , layer);
		this.probabilidadeDeArder = probabilidadeDeArder;
	}

	@Override
	public void update() {
		if(isBurning()) {
			burn();
		}
	}

	public void setFire() {
		if(isBurning()) {
			return;
		}

		double probabilidade = Math.random();
		boolean propaga = false;

		if(this instanceof Pine) {
			if(probabilidade < this.probabilidadeDeArder) {
				propaga = true;
			}
		}

		if(this instanceof Eucaliptus) {
			if(probabilidade < this.probabilidadeDeArder) {
				propaga = true;
			}	
		}

		if(this instanceof Grass) {
			if(probabilidade < this.probabilidadeDeArder) {
				propaga = true;
			}
		}

		if(propaga == true ) {
			fire = new Fire(getPosition());
			GameEngine.getInstance().addImage(fire);
		}
		
	}


	public void burn() {
		if(jogadasOnFire() > nJogadas+1 || GameEngine.getInstance().isWaterAtPosition(getPosition())) {
			nJogadas++;
		} else {
			ArrayList<ImageTile> list = GameEngine.getInstance().GetImageTile(getPosition());
			for(ImageTile i : list) {
				if(i instanceof Burnable || i instanceof Fire) {					
					GameEngine.getInstance().removeImage(i);
				}
			}
			Burnt burnt = new Burnt(getPosition(), getName(), getLayer());
			GameEngine.getInstance().addImage(burnt);
		}
	}

	public boolean isBurning() {
		ArrayList<ImageTile> tileList = GameEngine.getInstance().GetImageTile(getPosition());
		for(int i = 0; i < tileList.size(); i++){
			if(tileList.get(i) instanceof Fire)
				return true;
		}
		return false;
	}

	public String getTipo() {
		return getName();
	}

	public double getProbabilidade() {
		return this.probabilidadeDeArder;
	}

	public int getJogadasOnFire() {
		return this.nJogadas;
	}

}
