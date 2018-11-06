package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Square extends ShapeImp {

	@Override
	public void draw(Graphics canvas) {
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
	public Object clone() throws CloneNotSupportedException {
		Square x = new Square();
		x.setColor(new Color(getColor().getRGB()));
		x.setPosition(new Point(getPosition()));
		x.setFillColor(new Color(getFillColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}

}
