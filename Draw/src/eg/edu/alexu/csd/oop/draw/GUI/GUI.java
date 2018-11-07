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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.DrawingEngineImp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class GUI {

	private DrawingBoard drawingBoard = new DrawingBoard();
	private JFrame frame;

	private JButton lineBtn, rectangleBtn,squareBtn, triangleBtn, circleBtn, ellipseBtn, colorBtn, fillColorBtn;
	private Point position, current;
	private Map<String, Double> properties;
	private Color color = Color.BLACK, fillColor = Color.BLACK;

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

	
		
		Box vBox = Box.createVerticalBox();
		vBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		vBox.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		JPanel eastPanel = new JPanel();
		eastPanel.add(vBox);
		
		JCheckBox solidShapes = new JCheckBox("Draw Solid Shapes");
		solidShapes.setAlignmentX(Component.CENTER_ALIGNMENT);
		solidShapes.setSelected(true);
		solidShapes.addActionListener(e -> {
			if(!solidShapes.isSelected()) {
				fillColor = null;
			}
		});
		vBox.add(solidShapes);
		
		JComboBox allShapes = new JComboBox();
		vBox.add(allShapes);
		
		JButton removeShape = new JButton("Remove Selected Shape");
		removeShape.setAlignmentX(Component.CENTER_ALIGNMENT);
		vBox.add(removeShape);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		vBox.add(horizontalBox_2);
		
		JButton btnNewButton = new JButton("UP");
		horizontalBox_2.add(btnNewButton);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		vBox.add(horizontalBox_3);
		
		JButton btnNewButton_3 = new JButton("LEFT");
		horizontalBox_3.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Down");
		horizontalBox_3.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Right");
		horizontalBox_3.add(btnNewButton_5);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		vBox.add(horizontalBox_4);
		
		JButton btnNewButton_7 = new JButton("Increase Size");
		horizontalBox_4.add(btnNewButton_7);
		
		JButton btnNewButton_6 = new JButton("Decrease Size");
		horizontalBox_4.add(btnNewButton_6);
		
		Box horizontalBox = Box.createHorizontalBox();
		vBox.add(horizontalBox);
		
		JButton undoBtn = new JButton("Undo");
		undoBtn.addActionListener(e -> drawingBoard.undo());
		horizontalBox.add(undoBtn);
		undoBtn.setAlignmentX(0.5f);
		
		JButton redoBtn = new JButton("Redo");
		redoBtn.addActionListener(e -> drawingBoard.redo());
		horizontalBox.add(redoBtn);
		redoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		vBox.add(horizontalBox_1);
		
		JButton btnSave = new JButton("Save");
		btnSave.setAlignmentX(0.5f);
		horizontalBox_1.add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setAlignmentX(0.5f);
		horizontalBox_1.add(btnLoad);
		
		JButton btnNewButton_8 = new JButton("Get Supported Shapes");
		btnNewButton_8.setAlignmentX(Component.CENTER_ALIGNMENT);
		vBox.add(btnNewButton_8);
		
		
		frame.getContentPane().add(eastPanel, BorderLayout.EAST);

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
