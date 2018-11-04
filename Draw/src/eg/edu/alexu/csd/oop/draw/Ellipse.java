package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ellipse extends ShapeImp {
	private int height;
	private int width;
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(getFillColor());
		canvas.fillOval(getPosition().x, getPosition().y, width, height);
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, width, height);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Ellipse x = new Ellipse();
		x.setHeight(height);
		x.setWidth(width);
		x.setColor(new Color(getColor().getRGB()));
		x.setPosition(new Point(getPosition()));
		x.setFillColor(new Color(getFillColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}

}
