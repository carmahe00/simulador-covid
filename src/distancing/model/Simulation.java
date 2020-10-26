package distancing.model;

import java.util.ArrayList;


import javafx.scene.layout.Pane;

public class Simulation {

	private ArrayList<Person> people;
	
	public Simulation(Pane world, int  popSize) {
		people = new ArrayList<Person>();
		for (int i = 0; i < popSize; i++) {
			people.add(new Person(State.SUSCEPTIBLE, world));
		}
		people.add(new Person(State.INFECTED, world));
		draw();
	}

	public ArrayList<Person> getPeople() {
		return people;
	}

	public void move() {
		for(Person p: people) {
			p.move();
		}
	}
	
	public void draw() {
		for(Person p : people) {
			p.draw();
		}
	}
	
	public void resolveCollisions() {
		for(Person p : people) {
			for(Person q: people) {
				if(p != q ) {
					p.collisionCheck(q);
				}
			}
		}
	}
	
	public void feelBetter() {
		for(Person p:people) {
			p.feelBetter();
		}
	}
}