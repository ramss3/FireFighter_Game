package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

public class Grass extends Plant {

		public Grass(Point2D position) {
			super(position, "grass", 1, 0.45);

		}

		@Override
		public int jogadasOnFire() {
			return 3;
		}
		
	}

