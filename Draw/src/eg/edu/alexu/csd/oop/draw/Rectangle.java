package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rectangle extends ShapeImp {

	private int width;
	private int height;

	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(getFillColor());
		canvas.fillRect(getPosition().x, getPosition().y, width, height);
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, width, height);

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Rectangle x = new Rectangle();
		x.setHeight(height);
		x.setWidth(width);
		x.setColor(new Color(getColor().getRGB()));
		x.setPosition(new Point(getPosition()));
		x.setFillColor(new Color(getFillColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}

}
