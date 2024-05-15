package pt.iul.poo.firefight.starterpack;
import java.util.ArrayList;
import java.util.List;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Fire extends GameElement {

	public Fire(Point2D position) {
		super(position, "fire", 3);
	}

	@Override
	public void update() {
		propagaFogo();
	}

	public void propagaFogo() {

		List<Point2D> pontosVizinhos = getPosition().getNeighbourhoodPoints();
		for( Point2D i : pontosVizinhos) {
			if(!GameEngine.getInstance().gui.isWithinBounds(i)) {
				continue;
			}
			Plant plant = GameEngine.getInstance().getPlantAtPosition(i);
			if(plant != null && !plant.isBurning() && !GameEngine.getInstance().isFiremanAtPosition(i) && !GameEngine.getInstance().isWaterAtPosition(i)) {
				plant.setFire();
			}
		}	
	}
	
}