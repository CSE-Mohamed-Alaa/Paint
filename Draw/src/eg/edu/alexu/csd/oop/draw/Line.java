package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Line extends ShapeImp {
	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(getColor());
		canvas.drawLine(getPosition().x, getPosition().y, getProperties().get("x").intValue(),
				getProperties().get("y").intValue());
	}

	@Override
	public Shape getInstance() {
		Shape x = new Line();
		return x;
	}

}
