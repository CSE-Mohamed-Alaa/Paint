package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends ShapeImp {
	private Point point1;
	private Point point2;

	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(getColor());
		canvas.drawLine(point1.x, point1.y, point2.x, point2.y);
	}

	public Point getPoint1() {
		return point1;
	}

	public void setPoint1(Point point1) {
		this.point1 = point1;
	}

	public Point getPoint2() {
		return point2;
	}

	public void setPoint2(Point point2) {
		this.point2 = point2;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Line x = new Line();
		x.setPoint1(new Point(point1));
		x.setPoint2(new Point(point2));
		x.setColor(new Color(getColor().getRGB()));
		x.setProperties(cloneProberties());
		return x;
	}

}
