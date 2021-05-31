package data;

import java.util.ArrayList;


public class Ennemy {

	private ArrayList<EnnemyDog> Dogs;

	public Ennemy() {
		Dogs = new ArrayList<EnnemyDog>();
	}

	public ArrayList<EnnemyDog> getPhantoms() {
		return Dogs;
	}
	
	 public void setPhantoms(ArrayList<EnnemyDog> ennemies) 
	 { this.Dogs=ennemies; }
	  

	public void addPhantom(Position p) {
		Dogs.add(new EnnemyDog(p));
	}

}
