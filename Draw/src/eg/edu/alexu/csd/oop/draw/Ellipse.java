package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ellipse extends ShapeImp {
	
	@Override
	public void draw(Graphics canvas) {
		int x = Math.min(getPosition().x,  getProperties().get("x").intValue());
		int y = Math.min(getPosition().y,  getProperties().get("y").intValue());
		int width = Math.abs(getPosition().x-  getProperties().get("x").intValue());
		int height = Math.abs(getPosition().y-  getProperties().get("y").intValue());
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillOval(x, y, width, height);
		}
		
		canvas.setColor(getColor());
		canvas.drawOval(x, y, width, height);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Ellipse x = new Ellipse();
		x.setColor(new Color(getColor().getRGB()));
		x.setPosition(new Point(getPosition()));
		x.setFillColor(new Color(getFillColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}

}
