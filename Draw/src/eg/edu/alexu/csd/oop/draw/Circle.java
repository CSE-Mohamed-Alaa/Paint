package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends  ShapeImp {
	private int radius;

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(getFillColor());
		canvas.fillOval(getPosition().x, getPosition().y, radius, radius);
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, radius, radius);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Circle x = new Circle();
		x.setRadius(radius);
		x.setColor(new Color(getColor().getRGB()));
		x.setPosition(new Point(getPosition()));
		x.setFillColor(new Color(getFillColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}
}
