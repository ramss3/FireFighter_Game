package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Water extends GameElement {
	
	private int nJogadasAgua = 0;
	
	public Water(Point2D position, String dir) {
		super(position, "water" + dir, 3);
	}
	
	@Override 
	public void update() {
		updateAgua();
	}
	
	public void updateAgua() {
		if(nJogadasAgua > 1 ) {
			GameEngine.getInstance().removeImage(this);
		}
		nJogadasAgua++;
	}
	
}
