package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Pine extends Plant {

	public Pine(Point2D position) {
		super(position, "pine", 1, 0.15);
	}
	
	@Override
	public int jogadasOnFire() {
		return 10;
	}
	
}
