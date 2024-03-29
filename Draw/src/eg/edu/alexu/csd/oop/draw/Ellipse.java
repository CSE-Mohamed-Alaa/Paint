package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Ellipse extends ShapeImp {
	
	@Override
	public void draw(Object oCanvas) {
		Graphics canvas = (Graphics)oCanvas;
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
	
}
