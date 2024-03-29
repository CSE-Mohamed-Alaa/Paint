package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ShapeImp implements Shape {
	private Point position;
	private Map<String, Double> properties;
	private Color color;
	private Color fillColor;
	@Override
	public void setPosition(Object position) {
		this.position = (Point)position;
	}

	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		this.properties = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		return this.properties;
	}

	@Override
	public void setColor(Object color) {
		this.color = (Color)color;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setFillColor(Object color) {
		this.fillColor = (Color)color;
	}

	@Override
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
		Shape x = null;
		try {
			x = getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated s block
			e.printStackTrace();
		}
		x.setColor(new Color(getColor().getRGB()));
		x.setPosition(new Point(getPosition()));
		if (getFillColor() != null) {
			x.setFillColor(new Color(getFillColor().getRGB()));
		}
		x.setProperties(cloneProberties());
		return x;
	};
		
	public Map<String, Double> cloneProberties(){
		Map <String, Double> prop = new HashMap<>();
		for (Entry<String, Double> entry : properties.entrySet()) {
		   prop.put(entry.getKey()+"", new Double(entry.getValue().doubleValue())) ;
		}
		return prop;
		
	}

}
