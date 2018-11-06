package eg.edu.alexu.csd.oop.draw.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

public class GUI {

	private JFrame frame;

	private JButton lineBtn, rectangleBtn,squareBtn, triangleBtn, circleBtn, ellipseBtn, colorBtn, fillColorBtn;
	private Point position, current;
	private Map<String, Double> properties;
	private Color color = Color.BLACK, fillColor = null;

	enum ShapeId {
		Line, Rectangle, Square, Triangle, Circle, Ellipse, COLOR, FILLCOLOR
	}

	ShapeId currentButton = ShapeId.Line;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Paint");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);

		String pass = "./src/eg/edu/alexu/csd/oop/draw/GUI/";
		lineBtn = creatButton(ShapeId.Line, pass + "Line.png");
		rectangleBtn = creatButton(ShapeId.Rectangle, pass + "Rectangle.png");
		triangleBtn = creatButton(ShapeId.Triangle, "Triangle.png");
		circleBtn = creatButton(ShapeId.Circle, "Circle.png");
		ellipseBtn = creatButton(ShapeId.Ellipse, pass + "Ellipse.png");
		squareBtn = creatButton(ShapeId.Square, pass + "Square.png");
		colorBtn = creatColorButton(ShapeId.COLOR, pass + "Stroke.png");
		fillColorBtn = creatColorButton(ShapeId.FILLCOLOR, pass + "Fill.png");

		Box hBox = Box.createHorizontalBox();

		hBox.add(lineBtn);
		hBox.add(triangleBtn);
		hBox.add(squareBtn);
		hBox.add(rectangleBtn);
		hBox.add(circleBtn);
		hBox.add(ellipseBtn);
		hBox.add(colorBtn);
		hBox.add(fillColorBtn);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(hBox);
		frame.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		
		
		
		Box vBox = Box.createVerticalBox();
		
		
		
		

		DrawingBoard drawingBoard = new DrawingBoard();
		drawingBoard.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				current = new Point(e.getX(), e.getY());
				properties = new HashMap<>();
				properties.put("x", current.getX());
				properties.put("y", current.getY());
				drawingBoard.passShapeInfo(currentButton, position, properties, color, fillColor, false);

			}
		});
		drawingBoard.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				position = new Point(e.getX(), e.getY());

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				current = new Point(e.getX(), e.getY());
				properties = new HashMap<>();
				properties.put("x", current.getX());
				properties.put("y", current.getY());
				drawingBoard.passShapeInfo(currentButton, position, properties, color, fillColor, true);
			}
		});
		frame.getContentPane().add(drawingBoard, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	private JButton creatButton(ShapeId name, String icon) {
		JButton btn = new JButton(/*name.toString()*/);
		Icon x = new ImageIcon(icon);
		btn.setIcon(x);
		btn.addActionListener(e -> currentButton = name);
		return btn;

	}

	private JButton creatColorButton(ShapeId name, String icon) {
		JButton btn = new JButton(/* name.toString() */);
		Icon x = new ImageIcon(icon);
		btn.setIcon(x);
		btn.addActionListener(e -> {
			if (name == ShapeId.COLOR) {
				color = JColorChooser.showDialog(null, "Pick a Stroke", Color.BLACK);
			} else {
				fillColor = JColorChooser.showDialog(null, "Pick a Fill", Color.BLACK);
			}

		});
		return btn;
	}
}
