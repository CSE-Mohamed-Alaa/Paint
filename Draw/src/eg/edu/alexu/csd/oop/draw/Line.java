package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends ShapeImp {
	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(getColor());
		canvas.drawLine(getPosition().x, getPosition().y, getProperties().get("x").intValue(),  getProperties().get("y").intValue());
	}

	

	@Override
	public Object clone() throws CloneNotSupportedException {
		Line x = new Line();
		
		x.setColor(new Color(getColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}

}
