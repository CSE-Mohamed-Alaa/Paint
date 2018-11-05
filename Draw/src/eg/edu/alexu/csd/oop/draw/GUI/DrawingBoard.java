package eg.edu.alexu.csd.oop.draw.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.DrawingEngineImp;
import eg.edu.alexu.csd.oop.draw.Line;
import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawingBoard extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DrawingEngine drawingEngine = new DrawingEngineImp();
	
	public void paintComponent(Graphics canvas) {
		super.paintComponent(canvas);
		this.setBackground(Color.WHITE);
		Shape[] Shape = drawingEngine.getShapes();
		for (Shape x : Shape) {
			x.draw(canvas);
		}
		
	}

	public void addLine(Point start, Point end,boolean finished) {
		//ta2rebn hanb3at al properties be Map 3a4an manb3at4 parameters kteer
		//3a4an kda kda hatet7at fe al map , fa badal ma afdl a pass parameters kteer 
		//a3ml pass le al Map we 5las
		//wn3ml function wa7dah addShape bta5od al properties (Map) we 5las
		//TODO delete this comments
		Line line = new Line();
		line.setPoint1(start);
		line.setPoint2(end);
		line.setColor(Color.GRAY);
		if (finished)
			drawingEngine.addShape(line);
		getGraphics().drawLine(start.x, start.y, end.x, end.y);
		repaint();
	}

	
	
}
