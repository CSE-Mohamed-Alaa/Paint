package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Rectangle extends ShapeImp {
	
	@Override
	public void draw(Graphics canvas) {
		int x = Math.min(getPosition().x,  getProperties().get("x").intValue());
		int y = Math.min(getPosition().y,  getProperties().get("y").intValue());
		int width = Math.abs(getPosition().x -  getProperties().get("x").intValue());
		int height = Math.abs(getPosition().y -  getProperties().get("y").intValue());
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillRect(x, y, width, height);
		}
		
		canvas.setColor(getColor());
		canvas.drawRect(x, y, width, height);

	}

	@Override
	public Shape getInstance() {
		Shape x = new Rectangle();
		return x;
	}
	
}
