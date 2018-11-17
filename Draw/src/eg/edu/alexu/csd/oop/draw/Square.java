package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Square extends ShapeImp {

	@Override
	public void draw(Object oCanvas) {
		Graphics canvas = (Graphics)oCanvas;
		int x = getPosition().x;
		int y = getPosition().y;
		int radius = (int) Math.sqrt(Math.min(Math.pow(getPosition().x - getProperties().get("x") , 2),Math.pow(getPosition().y - getProperties().get("y") , 2)));

		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillRect(x- radius, y - radius, 2 *radius,2 * radius);
		}
		canvas.setColor(getColor());
		canvas.drawRect(x- radius, y - radius, 2 *radius, 2*radius);
	}
	
	@Override
	public Shape getInstance() {
		Shape x = new Square();
		return x;
	}

}
