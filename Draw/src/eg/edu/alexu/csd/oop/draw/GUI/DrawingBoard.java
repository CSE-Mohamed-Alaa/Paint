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
		
		/*
		//line build in working 
		canvas.setColor(Color.GREEN);
		
		+canvas.drawLine(0,0,50,50);
		*/
		/*
		//it is the right implementation i think
		Shape[] Shape = drawingEngine.getShapes();
		for (Shape x : Shape) {
			x.draw(canvas);
		}
		*/
		
		//our line (Doesn't Work)
		if(x != null) {
		x.setColor(Color.GREEN);
		x.draw(canvas);
		}
	}

	public void addLine(Point start, Point end) {
		//ta2rebn hanb3at al properties be Map 3a4an manb3at4 parameters kteer
		//3a4an kda kda hatet7at fe al map , fa badal ma afdl a pass parameters kteer 
		//a3ml pass le al Map we 5las
		//wn3ml function wa7dah addShape bta5od al properties (Map) we 5las
		//TODO delete this comments
		Line line = new Line();
		line.setPoint1(start);
		line.setPoint2(end);
		x=line;
		//TODO set colors we 7agat tanyah m4 wa2taha now
		drawingEngine.addShape(line);
		repaint();
	}
	Line x;
	
	
}
