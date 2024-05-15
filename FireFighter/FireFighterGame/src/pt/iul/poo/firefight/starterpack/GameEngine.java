package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;


public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	public ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	public Fireman fireman;			// Referencia para o bombeiro
	public Bulldozer bulldozer;		// Referencia para o bulldozer
	private Fire fire;				// Referencia para o fogo
	public Movable movable;			// Variavel para todos os objetos que s�o moveis
	public Plane plane;				// Referencia para o avi�o

	private static GameEngine INSTANCE;

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador e' feito no construtor 

	private GameEngine() {

		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		tileList = new ArrayList<>();   
	}



	public static GameEngine getInstance() {
		if(INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}


	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado (neste caso seria a GUI)

	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();   // obtem o codigo da tecla pressionada
		boolean canMove = false;
		
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {
			movable.move(key);
			canMove = true;
		}

		if(key == KeyEvent.VK_ENTER && movable instanceof Bulldozer) {
			fireman.setPosition(bulldozer.getPosition());
			gui.addImage(fireman);
			movable = fireman;
			canMove = true;
		}

		if(key == KeyEvent.VK_P && plane == null) {
			createPlane();
			canMove = true;
		}


		if(canMove == true) {
			for(Updatable up: getUpdatables()) {
				up.update();
			}
			
			if(isGameOver()) { // se já não houver mais fogos na grelha de jogo o mesmo acaba
				gui.setMessage("Game Over!");
			}
			gui.update();    // redesenha as imagens na GUI, tendo em conta as novas posicoes

		}
	}

	//Adiciona novo objeto à grelha do jogo

	public void addImage(ImageTile image){
		tileList.add(image);
		gui.addImage(image);
	}

	//remove um objeto à grelha do jogo

	public void removeImage(ImageTile image) {
		tileList.remove(image);
		gui.removeImage(image);
	}
	
	// cria uma lista com todos os objetos do tipo updatable
	public List<Updatable> getUpdatables() {
		ArrayList<Updatable> tiles = new ArrayList<>();
		for(ImageTile i: tileList) {
			if(i instanceof Updatable) {
				tiles.add((Updatable)i);
			}
		}
		return tiles;
	}

	public Plant getPlantAtPosition(Point2D position) {
		for(ImageTile i: tileList) {
			if(position.equals(i.getPosition()) && i instanceof Plant)	
					return (Plant) i;
		}
		return null;
	}

	public Fireman getFiremanAtPosition(Point2D position) {
		for(ImageTile i: tileList) {
			if(position.equals(i.getPosition()) && i instanceof Fireman)
					return (Fireman) i;
		}
		return null;
	}

	public boolean isPlaneOnMap() {
		if(plane == null)
			return false;
		return true;
	}


	public Fire getFireAtPosition(Point2D position) {
		for(ImageTile i: tileList) {
			if(position.equals(i.getPosition())) {
				if(i instanceof Fire) {	
					return (Fire) i;
				}
			}
		}
		return null;
	}

	public boolean isFireAtPosition(Point2D position) {
		for(ImageTile objeto : tileList) 
			if(objeto.getPosition().equals(position) && objeto instanceof Fire) 
				return true;	
		return false;		
	}

	public boolean isWaterAtPosition(Point2D position) {
		for(ImageTile objeto : tileList) 
			if(objeto.getPosition().equals(position) && objeto instanceof Water) 
				return true;	
		return false;		
	}

	public boolean isFiremanAtPosition(Point2D position) {
		return position.equals(fireman.getPosition());	
	}

	public boolean isBulldozerAtPosition(Point2D position) {
		return position.equals(bulldozer.getPosition());	
	}

	//Verifica se a planta na posição dada

	public boolean isPlantAtPosition(Point2D position) {
		for(ImageTile objeto : tileList) 
			if(objeto.getPosition().equals(position) && objeto instanceof Plant)
				return true;	
		return false;		
	}
	
	//Verifica se o terreno numa dada posi��o j� est� queimado
	public boolean isBurntAtPosition(Point2D position) {
		for(ImageTile objeto : tileList) 
			if(objeto.getPosition().equals(position) && objeto instanceof Burnt) 
				return true;	
		return false;		
	}

	public boolean isGameOver() {
		for(ImageTile i: tileList) {
			if(i instanceof Fire)
				return false;
		}
		return true;
	}

	public void createPlane() {

		int nMaxFogos = 0;
		int linha = 0;
		for(int i = 0; i < GRID_WIDTH; i++) {
			int counter = 0;
			for(int j = 0; j < GRID_HEIGHT; j++) {
				if(isFireAtPosition(new Point2D(i,j))) 
					counter++;	
				if(counter > nMaxFogos) {
					nMaxFogos = counter;
					linha = i;
				}
			}
		}

		plane = new Plane(new Point2D(linha,GRID_HEIGHT+1));
		addImage(plane);
	}


	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		createTerrain("levels/example.txt");      
		createMoreObjects("levels/example.txt");  
		movable = fireman;
		sendImagesToGUI();
	}


	// l� o ficheiro e adiciona somente as plantas e o terreno sem vegetacao
	private void createTerrain(String fileName) {

		Scanner sc;
		try {
			sc = new Scanner(new File(fileName));
			int count = 0;
			while(sc.hasNextLine()) {
				String tipo = sc.nextLine();
				for (int x=0; x<GRID_HEIGHT; x++) {
					char objeto = tipo.charAt(x);
					tileList.add(GameElement.create( String.valueOf(objeto) , new Point2D(x,count)));
				}
				count++;
				if(count >= GRID_HEIGHT) {
					break;
				}
			}			
			sc.close();			
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro n�o encontrado");
		}	
	}


	// Criacao de mais objetos - um bombeiro, dois fogos e o bulldozer
	private void createMoreObjects(String fileName) {

		try {
			Scanner sc = new Scanner(new File(fileName));
			int count = 1;
			while(sc.hasNextLine()) {
				sc.nextLine();
				if(count >= GRID_HEIGHT) {
					String tipo = sc.next();
					int x = sc.nextInt();
					int y = sc.nextInt();
					if(tipo.equals("Fireman")) {
						fireman = new Fireman( new Point2D(x,y));						
						tileList.add(fireman);
					} else if(tipo.equals("Bulldozer")) {
						bulldozer = new Bulldozer( new Point2D(x,y));						
						tileList.add(bulldozer);
					} else if(tipo.equals("Fire")) {
						fire = new Fire( new Point2D(x,y));						
						tileList.add(fire);
					}
				}
				count++;
			}
			sc.close();
			gui.update();
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro n�o encontrado");
		}
	}

	//retorna todas as imagens presentes numa dada posi��o
	public ArrayList<ImageTile> GetImageTile(Point2D position) {
		ArrayList<ImageTile> list = new ArrayList<>();
		for(ImageTile g : tileList) {
			if(g.getPosition().equals(position))
				list.add(g);
		}
		return list;	
	}


	// Envio das mensagens para a GUI  
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
