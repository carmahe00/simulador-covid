package distancing.model;

public class Heading {

	/**
	 * @param SPEED velovidad de la persona
	 */
	public static final double SPEED = 5;
	private double dx;
	private double dy;
	public Heading(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public Heading() {
		//Dirección en radio
		double dir = Math.random() * 2 * Math.PI;
		dx = Math.sin(dir);
		dy = Math.cos(dir);
	}
	public static double getSpeed() {
		return SPEED;
	}
	public double getDx() {
		return dx * SPEED;
	}
	public double getDy() {
		return dy * SPEED;
	}
	
	public void bounceX() {
		dx *= -1;
	}
	
	public void bounceY() {
		dy *= -1;
	}
}
