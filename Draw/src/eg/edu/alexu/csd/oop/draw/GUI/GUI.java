package eg.edu.alexu.csd.oop.draw.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import eg.edu.alexu.csd.oop.draw.Circle;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.DrawingEngineImp;
import eg.edu.alexu.csd.oop.draw.Ellipse;
import eg.edu.alexu.csd.oop.draw.Line;
import eg.edu.alexu.csd.oop.draw.Rectangle;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Square;
import eg.edu.alexu.csd.oop.draw.Triangle;

import java.awt.Component;


public class GUI {

	private DrawingBoard drawingBoard = new DrawingBoard();
	private DrawingEngine drawingEngine = new DrawingEngineImp();
	private JFrame frame;

    private final DefaultComboBoxModel<Integer> shapesModel = new DefaultComboBoxModel<Integer>();
        
	private JButton lineBtn, rectangleBtn, squareBtn, triangleBtn, circleBtn, ellipseBtn, colorBtn, fillColorBtn;
	private Point position, current;
	private Map<String, Double> properties;
	private Color color = Color.BLACK, fillColor = Color.BLACK, prevFillColor = Color.BLACK;
	JCheckBox solidShapes;
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
				

				shapesModel.addElement((int)(drawingEngine.getShapes().length + 1));
				
				
			}
		});
		
		frame.getContentPane().add(drawingBoard, BorderLayout.CENTER);

	
		
		Box vBox = Box.createVerticalBox();
		vBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		vBox.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		JPanel eastPanel = new JPanel();
		eastPanel.add(vBox);
		
		solidShapes = new JCheckBox("Draw Solid Shapes");
		solidShapes.setAlignmentX(Component.CENTER_ALIGNMENT);
		solidShapes.setSelected(true);
		solidShapes.addActionListener(e -> {
			if(solidShapes.isSelected()) {
				fillColor = prevFillColor;
			}else {
				fillColor = null;
			}
		});
		vBox.add(solidShapes);
		
		
		JComboBox<Integer> allShapesComboBox = new JComboBox<Integer>(shapesModel);	
		vBox.add(allShapesComboBox);
		
		JButton removeShape = new JButton("Remove Selected Shape");
		removeShape.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				drawingEngine.removeShape(drawingEngine.getShapes()[(int)(shapesModel.getSelectedItem())-1]);
				drawingBoard.repaint();
				updateShapesModel();
			}
		});
		removeShape.setAlignmentX(Component.CENTER_ALIGNMENT);
		vBox.add(removeShape);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		vBox.add(horizontalBox_2);
		
		final int CHANGE_CONST = 10;
		
		JButton upBtn = new JButton("UP");
		upBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				editShape(0, -CHANGE_CONST, 0, -CHANGE_CONST);
			}
		});

		horizontalBox_2.add(upBtn);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		vBox.add(horizontalBox_3);
		
		JButton leftBtn = new JButton("Left");
		leftBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				editShape(-CHANGE_CONST, 0, -CHANGE_CONST, 0);
			}
		});
		horizontalBox_3.add(leftBtn);
		
		JButton downBtn = new JButton("Down");
		downBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				editShape(0, CHANGE_CONST, 0, CHANGE_CONST);			}
		});
		horizontalBox_3.add(downBtn);
		
		JButton rightBtn = new JButton("Right");
		rightBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				editShape(CHANGE_CONST, 0, CHANGE_CONST, 0);
			}
		});
		horizontalBox_3.add(rightBtn);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		vBox.add(horizontalBox_4);
		
		//TODO Bug Resize Line
		JButton incBtn = new JButton("Increase Size");
		incBtn.addActionListener(e -> {
			editShape(0, 0, CHANGE_CONST, CHANGE_CONST);
		});
		horizontalBox_4.add(incBtn);
		
		JButton decBtn = new JButton("Decrease Size");
		decBtn.addActionListener(e -> {
			editShape(0, 0, -CHANGE_CONST, -CHANGE_CONST);
		});
		horizontalBox_4.add(decBtn);
		
		Box horizontalBox = Box.createHorizontalBox();
		vBox.add(horizontalBox);
		
		JButton undoBtn = new JButton("Undo");
		undoBtn.addActionListener(e ->{
			drawingEngine.undo();
			drawingBoard.repaint();
			updateShapesModel();
		});
		horizontalBox.add(undoBtn);
		undoBtn.setAlignmentX(0.5f);
		
		JButton redoBtn = new JButton("Redo");
		redoBtn.addActionListener(e -> {
			drawingEngine.redo();
			drawingBoard.repaint();
			updateShapesModel();
		});
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

		
		JButton getSupportedShapesBtn = new JButton("Get Supported Shapes");
		getSupportedShapesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		getSupportedShapesBtn.addActionListener(e -> {
			JFileChooser f = new JFileChooser(); 
			f.setDialogTitle("Choose the JAR file");
			f.showOpenDialog(null);
		});
		vBox.add(getSupportedShapesBtn);
		
		
		frame.getContentPane().add(eastPanel, BorderLayout.EAST);

		frame.setVisible(true);
	}

	private void updateShapesModel() {
		shapesModel.removeAllElements();
		for (int i = 0; i < drawingEngine.getShapes().length; i++) {
			shapesModel.addElement(i+1);
		}	
	}

	private Shape cloneShape(Shape oldShape) {
		Shape newShape = null;
		try {
			newShape = (Shape)oldShape.clone();
		} catch (CloneNotSupportedException e1) {
		} 
		return newShape;
		
	}

	private Shape updateShapeProp(Shape shape, int x1, int y1, int x2, int y2) {
		Point p1 = new Point(shape.getPosition().x + x1, shape.getPosition().y + y1);
		shape.setPosition(p1);
		Map<String, Double> p2 = new HashMap<>();
		p2.put("x", shape.getProperties().get("x") + x2);
		p2.put("y", shape.getProperties().get("y") + y2);
		shape.setProperties(p2);
		return shape;
		
	}
	
	private void editShape(int x1, int y1, int x2, int y2) {
		Shape oldShape = drawingEngine.getShapes()[(int)(shapesModel.getSelectedItem())-1];
		Shape newShape = cloneShape(oldShape);
		newShape = updateShapeProp(newShape, x1, y1, x2, y2);
		drawingEngine.updateShape(oldShape, newShape);
		drawingBoard.repaint();
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
				if(fillColor != null) {
					prevFillColor = fillColor;
					solidShapes.setSelected(true);
				}else {
					solidShapes.setSelected(false);
				}
			}

		});
		return btn;
	}
	
	
	
	
	public class DrawingBoard extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Shape currentShape;
		private boolean shapeFinished = false;

		

		public void paintComponent(Graphics canvas) {
			super.paintComponent(canvas);
			this.setBackground(Color.WHITE);
			((Graphics2D) canvas).setStroke(new BasicStroke(2));
			if (shapeFinished && currentShape != null) {
				drawingEngine.addShape(currentShape);
				drawingEngine.refresh(canvas);
				currentShape = null;
			} else if (currentShape != null) {
				drawingEngine.refresh(canvas);
				currentShape.draw(canvas);
			}else {
				drawingEngine.refresh(canvas);
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


		
	}

}
