package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Line extends ShapeImp {
	@Override
	public void draw(Object oCanvas) {
		Graphics canvas = (Graphics)oCanvas;
		canvas.setColor(getColor());
		canvas.drawLine(getPosition().x, getPosition().y, getProperties().get("x").intValue(),
				getProperties().get("y").intValue());
	}

}
