package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Circle extends  ShapeImp {
	

	@Override
	public void draw(Object oCanvas) {
		Graphics canvas = (Graphics)oCanvas;
		int x = getPosition().x;
		int y = getPosition().y;
		int radius = (int) Math.sqrt(Math.pow(getPosition().x - getProperties().get("x") , 2)+Math.pow(getPosition().y - getProperties().get("y") , 2));

		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillOval(x- radius, y - radius, 2 *radius,2 * radius);
		}
		canvas.setColor(getColor());
		canvas.drawOval(x- radius, y - radius, 2 *radius, 2*radius);
	}

}
