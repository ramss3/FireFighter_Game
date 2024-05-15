package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

public class Eucaliptus extends Plant {

		public Eucaliptus(Point2D position) {
			super(position, "eucaliptus", 1, 0.3);
		}
		
		@Override
		public int jogadasOnFire() {
			return 5;
		}
		
	}
