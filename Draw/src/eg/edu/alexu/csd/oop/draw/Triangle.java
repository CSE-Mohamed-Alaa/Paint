package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.awt.Polygon;

public class Triangle extends ShapeImp {

	@Override
	public void draw(Graphics canvas) {
		int y = getPosition().y;
		int x = (int) (getPosition().x + 2 * (getProperties().get("x") - getPosition().x));
		Polygon p = new Polygon(new int[]{x,getProperties().get("x").intValue(),(int) getPosition().getX(),x}, new int[]{y,(int)
				getProperties().get("y").intValue(),(int) getPosition().getY(),y}, 3); 
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillPolygon(p);
		}
		canvas.setColor(getColor());
		canvas.drawPolygon(p);

	}
	
	@Override
	public Shape getInstance() {
		Shape x = new Triangle();
		return x;
	}

}
