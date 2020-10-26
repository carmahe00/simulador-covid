package distancing.model;

import javafx.scene.layout.Pane;


public class Position {

	private double x;
	private double y;
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Posición aleatoria 
	 * @param world panel de la pantalla
	 * @param radius radio
	 */
	public Position(Pane world) {
		this(Person.radius + Math.random()* (world.getWidth() -2 * Person.radius), 
				Person.radius +Math.random() * (world.getHeight() - 2 * Person.radius));
		System.out.println(world.getWidth()+", "+world.getHeight());
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double distance(Position other) {
		return Math.sqrt(Math.pow(this.x - other.x, 2)+
				Math.pow(this.y - other.y, 2));
	}
	
	/**
	 * método para mover Person y que no se salga de la pantalla
	 * @param heading maneja la dirección en x y y
	 * @param world pantalla que se muestra
	 */
	public void move(Heading heading, Pane world, Position origin) {
		x += heading.getDx();
		y += heading.getDy();
		if(x<Person.radius || x > world.getWidth() - Person.radius || distance(origin) > Person.distance) {
			heading.bounceX();
			x+= heading.getDx();
		}
		if(y<Person.radius || y > world.getHeight() - Person.radius || distance(origin) > Person.distance) {
			heading.bounceY();
			y+= heading.getDy();
		}
	}
	
	/**
	 * método para saber si hay coli
	 * @param other
	 * @return
	 */
	public boolean collision(Position other) {
		return distance(other) < Person.radius;
	}
}
