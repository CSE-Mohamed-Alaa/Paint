package eg.edu.alexu.csd.oop.draw.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.Circle;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.DrawingEngineImp;
import eg.edu.alexu.csd.oop.draw.Ellipse;
import eg.edu.alexu.csd.oop.draw.Line;
import eg.edu.alexu.csd.oop.draw.Rectangle;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Square;
import eg.edu.alexu.csd.oop.draw.Triangle;
import eg.edu.alexu.csd.oop.draw.GUI.GUI.ShapeId;

public class DrawingBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingEngine drawingEngine = new DrawingEngineImp();
	private Shape currentShape;
	private boolean shapeFinished = false;

	public DrawingEngine getDrawingEngine() {
		return drawingEngine;
	}

	public void paintComponent(Graphics canvas) {
		super.paintComponent(canvas);
		this.setBackground(Color.WHITE);
		((Graphics2D) canvas).setStroke(new BasicStroke(2));
		if (shapeFinished && currentShape != null) {
			drawingEngine.addShape(currentShape);
			drawingEngine.refresh(canvas);
		} else if (currentShape != null) {
			drawingEngine.refresh(canvas);
			currentShape.draw(canvas);
		}
	}

	private Shape determineShape(ShapeId id) {
		Shape x = null;
		switch (id) {
		case Line:
			x = new Line();
			break;
		case Rectangle:
			x = new Rectangle();
			break;
		case Triangle:
			x = new Triangle();
			break;
		case Circle:
			x = new Circle();

			break;
		case Ellipse:
			x = new Ellipse();
			break;
		case Square:
			x = new Square();
		default:
			break;
		}
		return x;
	}

	public void passShapeInfo(ShapeId id, Point position, Map<String, Double> properties, Color color, Color fillColor,
			boolean finished) {
		Shape x = determineShape(id);
		shapeFinished = finished;
		currentShape = x;
		x.setPosition(position);
		x.setProperties(properties);
		x.setColor(color);
		x.setFillColor(fillColor);
		repaint();
	}

	public void undo() {
		drawingEngine.undo();
		repaint();
	}

	public void redo() {
		drawingEngine.redo();
		repaint();
	}

}
