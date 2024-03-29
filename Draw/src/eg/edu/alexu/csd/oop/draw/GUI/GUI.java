package eg.edu.alexu.csd.oop.draw.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class GUI {

	private DrawingBoard drawingBoard = new DrawingBoard();
	private DrawingEngine drawingEngine = new DrawingEngineImp();
	private JFrame frame;

	private final DefaultComboBoxModel<Integer> shapesModel = new DefaultComboBoxModel<Integer>();
	private final DefaultComboBoxModel<String> supShapesModel = new DefaultComboBoxModel<>();

	int CHANGE_CONST = 0;

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

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (frame.getWidth() / 2);
		int yPos = (dim.height / 2) - (frame.getHeight() / 2);
		frame.setLocation(xPos, yPos);

		String path = "/eg/edu/alexu/csd/oop/draw/GUI/";
		lineBtn = creatButton(ShapeId.Line, path + "Line.png");
		rectangleBtn = creatButton(ShapeId.Rectangle, path + "Rectangle.png");
		triangleBtn = creatButton(ShapeId.Triangle, path + "Triangle.png");
		circleBtn = creatButton(ShapeId.Circle, path + "Circle.png");
		ellipseBtn = creatButton(ShapeId.Ellipse, path + "Ellipse.png");
		squareBtn = creatButton(ShapeId.Square, path + "Square.png");
		colorBtn = creatColorButton(ShapeId.COLOR, path + "Stroke.png");
		fillColorBtn = creatColorButton(ShapeId.FILLCOLOR, path + "Fill.png");

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
					shapesModel.addElement((int) (drawingEngine.getShapes().length + 1));
				

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
			if (solidShapes.isSelected()) {
				fillColor = prevFillColor;
			} else {
				fillColor = null;
			}
		});
		vBox.add(solidShapes);

		Box horizontalBox_5 = Box.createHorizontalBox();
		vBox.add(horizontalBox_5);

		JLabel lblNewLabel = new JLabel("Shape Number");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		horizontalBox_5.add(lblNewLabel);

		JComboBox<Integer> allShapesComboBox = new JComboBox<Integer>(shapesModel);
		horizontalBox_5.add(allShapesComboBox);

		JButton removeShape = new JButton("Remove Selected Shape");
		removeShape.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				drawingEngine.removeShape(drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1]);
				drawingBoard.repaint();
				updateShapesModel();
			}
		});
		removeShape.setAlignmentX(Component.CENTER_ALIGNMENT);
		vBox.add(removeShape);

		Box horizontalBox_2 = Box.createHorizontalBox();
		vBox.add(horizontalBox_2);

		JButton upBtn = new JButton("Up");
		upBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				changeValue("Move");
				Shape shape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
				if (ourShape(shape)) {
					editShape(0, -CHANGE_CONST, 0, -CHANGE_CONST);
				}else {
					moveSup(0,-CHANGE_CONST);
				}
			}
		});

		horizontalBox_2.add(upBtn);

		Box horizontalBox_3 = Box.createHorizontalBox();
		vBox.add(horizontalBox_3);

		JButton leftBtn = new JButton("Left");
		leftBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				changeValue("Move");
				Shape shape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
				if (ourShape(shape)) {
					editShape(-CHANGE_CONST, 0, -CHANGE_CONST, 0);
				}else {
					moveSup(-CHANGE_CONST, 0);
				}
			}
		});
		horizontalBox_3.add(leftBtn);

		JButton downBtn = new JButton("Down");
		downBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() != null) {
				changeValue("Move");
				Shape shape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
				if (ourShape(shape)) {
					editShape(0, CHANGE_CONST, 0, CHANGE_CONST);
				}else {
					moveSup(0,CHANGE_CONST);	
				}
			}
		});
		horizontalBox_3.add(downBtn);

		JButton rightBtn = new JButton("Right");
		rightBtn.addActionListener(e -> {
			
			if (shapesModel.getSelectedItem() != null) {
				changeValue("Move");
				Shape shape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
				if (ourShape(shape)) {
					editShape(CHANGE_CONST, 0, CHANGE_CONST, 0);
				}else {
					moveSup(CHANGE_CONST, 0);
				}
			}
		});
		horizontalBox_3.add(rightBtn);

		Box horizontalBox_4 = Box.createHorizontalBox();
		vBox.add(horizontalBox_4);

		JButton incBtn = new JButton("Increase Size");
		incBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() == null) {
				return;
			}
			changeValue("Increase");
			Shape currentShape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
			if (ourShape(currentShape)) {
				
				int dX = (int) -(((Point) currentShape.getPosition()).x
						- Math.round(currentShape.getProperties().get("x")));
				int dY = (int) -(((Point) currentShape.getPosition()).y
						- Math.round(currentShape.getProperties().get("y")));
	
				editShape(0, 0, xChange(dX, dY), yChange(dX, dY));
			}else {
				resizeSup(CHANGE_CONST);
			}

		});
		horizontalBox_4.add(incBtn);

		JButton decBtn = new JButton("Decrease Size");
		decBtn.addActionListener(e -> {
			if (shapesModel.getSelectedItem() == null) {
				return;
			}
			
			changeValue("Decrease");
			Shape currentShape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
			if (ourShape(currentShape)) {
				
				int dX = (int) -(((Point) currentShape.getPosition()).x
						- Math.round(currentShape.getProperties().get("x")));
				int dY = (int) -(((Point) currentShape.getPosition()).y
						- Math.round(currentShape.getProperties().get("y")));
				if (currentShape.getClass().getSimpleName().equals("Circle")) {
					if (Math.abs(dX) > CHANGE_CONST || Math.abs(dY) > CHANGE_CONST) {
						editShape(0, 0, -xChange(dX, dY), -yChange(dX, dY));
					}
				} else {
					if (Math.abs(dX) > CHANGE_CONST && Math.abs(dY) > CHANGE_CONST) {
						editShape(0, 0, -xChange(dX, dY), -yChange(dX, dY));
					}
				}
				
			}else {
				resizeSup(-CHANGE_CONST);
			}
		});
		horizontalBox_4.add(decBtn);

		Box horizontalBox = Box.createHorizontalBox();
		vBox.add(horizontalBox);

		JButton undoBtn = new JButton("Undo");
		undoBtn.addActionListener(e -> {
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
		btnSave.addActionListener(e -> {
			JFileChooser f = new JFileChooser(System.getProperty("user.home") + "/Desktop");
			f.setDialogTitle("Choose file to save");
			int returnValue = f.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				drawingEngine.save(f.getSelectedFile().getAbsolutePath());
			}

		});
		btnSave.setAlignmentX(0.5f);
		horizontalBox_1.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(e -> {
			JFileChooser f = new JFileChooser(System.getProperty("user.home") + "/Desktop");
			f.setDialogTitle("Choose file to load");
			int returnValue = f.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				drawingEngine.load(f.getSelectedFile().getAbsolutePath());
				drawingBoard.repaint();
				updateShapesModel();
			}

		});
		btnLoad.setAlignmentX(0.5f);
		horizontalBox_1.add(btnLoad);

		JButton getSupportedShapesBtn = new JButton("Get Supported Shapes");
		getSupportedShapesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		getSupportedShapesBtn.addActionListener(e -> {

			List<Class<? extends Shape>> supShapes = drawingEngine.getSupportedShapes();
			supShapesModel.removeAllElements();
			for (Class<? extends Shape> supShape : supShapes) {
				supShapesModel.addElement(supShape.getSimpleName());
			}

			/*
			 * JFileChooser f = new JFileChooser(System.getProperty("user.home") +
			 * "/Desktop"); f.setDialogTitle("Choose the JAR file"); int returnValue =
			 * f.showOpenDialog(null);
			 * 
			 * if (returnValue == JFileChooser.APPROVE_OPTION) { PrintWriter writer = null;
			 * try { writer = new PrintWriter("supportedShapePath.txt", "UTF-8"); } catch
			 * (FileNotFoundException | UnsupportedEncodingException e2) {
			 * e2.printStackTrace(); } writer.println(f.getSelectedFile().getPath());
			 * writer.close();  }
			 * 
			 */

		});
		vBox.add(getSupportedShapesBtn);
		JComboBox<String> subShapesComboBox = new JComboBox<>(supShapesModel);

		JButton drawSupShapeBtn = new JButton("Draw Supported Shape");
		drawSupShapeBtn.addActionListener(e -> {
			String x = (String) supShapesModel.getSelectedItem();
			List<Class<? extends Shape>> supShapes = drawingEngine.getSupportedShapes();
			for (Class<? extends Shape> supShape : supShapes) {
				if (supShape.getSimpleName().equals(x)) {
					try {
						Shape shape = supShape.newInstance();
						JComponent[] comp = createPropFieldsArray(shape);
						int result = JOptionPane.showConfirmDialog(null, comp, x + " Properties",
								JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.OK_OPTION) {
							try {
								Point p1 = new Point(Integer.parseInt(((JTextField) comp[1]).getText()),
										Integer.parseInt(((JTextField) comp[3]).getText()));
								properties = new HashMap<>();
								//After position fields +2 for label (even) and textField (odd)
								for (int i = 4; i < comp.length; i+=2) {
									properties.put(((JLabel)comp[i]).getText(), Double.parseDouble(((JTextField) comp[i+1]).getText()));
								}

								drawingBoard.passShapeInfo(shape, p1, properties, color, fillColor, true);
								shapesModel.addElement((int) (drawingEngine.getShapes().length + 1));
							} catch (NumberFormatException e2) {
								JOptionPane.showMessageDialog(null, "Error While Entering The Properties !!");
							}
						}

					} catch (InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		// drawSupShapeBtn.setAlignmentX(0.5f);

		Box supportedShapesBox = Box.createHorizontalBox();
		supportedShapesBox.add(drawSupShapeBtn);
		supportedShapesBox.add(subShapesComboBox);
		vBox.add(supportedShapesBox);

		frame.getContentPane().add(eastPanel, BorderLayout.EAST);

		frame.setVisible(true);
	}

	private void updateShapesModel() {
		shapesModel.removeAllElements();
		for (int i = 0; i < drawingEngine.getShapes().length; i++) {
			shapesModel.addElement(i + 1);
		}
	}

	private Shape cloneShape(Shape oldShape) {
		Shape newShape = null;
		try {
			newShape = (Shape) oldShape.clone();
		} catch (CloneNotSupportedException e1) {
		}
		return newShape;

	}

	private Shape updateShapeProp(Shape shape, int x1, int y1, int x2, int y2) {
		Point p1 = new Point(((Point) shape.getPosition()).x + x1, ((Point) shape.getPosition()).y + y1);
		shape.setPosition(p1);
		Map<String, Double> p2 = new HashMap<>();
		p2.put("x", shape.getProperties().get("x") + x2);
		p2.put("y", shape.getProperties().get("y") + y2);
		shape.setProperties(p2);
		return shape;

	}

	private void editShape(int x1, int y1, int x2, int y2) {
		Shape oldShape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
		Shape newShape = cloneShape(oldShape);
		newShape = updateShapeProp(newShape, x1, y1, x2, y2);
		drawingEngine.updateShape(oldShape, newShape);
		drawingBoard.repaint();
	}

	private JButton creatButton(ShapeId name, String icon) {
		JButton btn = new JButton(/* name.toString() */);
		Icon x = new ImageIcon(getClass().getResource(icon));
		btn.setIcon(x);
		btn.addActionListener(e -> currentButton = name);
		return btn;

	}

	private JButton creatColorButton(ShapeId name, String icon) {
		JButton btn = new JButton(/* name.toString() */);
		Icon x = new ImageIcon(getClass().getResource(icon));
		btn.setIcon(x);
		btn.addActionListener(e -> {
			if (name == ShapeId.COLOR) {
				color = JColorChooser.showDialog(null, "Pick a Stroke", Color.BLACK);
				if (color == null) {
					color = Color.BLACK;
				}
			} else {
				fillColor = JColorChooser.showDialog(null, "Pick a Fill", Color.BLACK);
				if (fillColor != null) {
					prevFillColor = fillColor;
					solidShapes.setSelected(true);
				} else {
					solidShapes.setSelected(false);
				}
			}

		});
		return btn;
	}

	private int xChange(int xDifference, int yDifference) {
		double length = Math.sqrt(xDifference * xDifference + yDifference * yDifference);
		return (int) Math.round((xDifference * CHANGE_CONST) / length);
	}

	private int yChange(int xDifference, int yDifference) {
		double length = Math.sqrt(xDifference * xDifference + yDifference * yDifference);
		return (int) Math.round((yDifference * CHANGE_CONST) / length);
	}

	// inputs in odd indexes
	private JComponent[] createPropFieldsArray(Shape shape) {
		ArrayList<JComponent> propFields = new ArrayList<>();
		propFields.add(new JLabel("Position of X"));
		propFields.add(new JTextField());
		propFields.add(new JLabel("Position of Y"));
		propFields.add(new JTextField());
		Iterator<Map.Entry<String, Double>> it = shape.getProperties().entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Double> entry = it.next();
			propFields.add(new JLabel(entry.getKey()));
			propFields.add(new JTextField());
		}
		JComponent[] arr = new JComponent[propFields.size()];
		arr = propFields.toArray(arr);
		return arr;
	}

	private void changeValue(String x) {
		try {
			String value = JOptionPane.showInputDialog("Enter " + x + " Value in Pixels");
			CHANGE_CONST = Integer.parseInt(value);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error While Entering The Input !!");
			CHANGE_CONST = 0;
		}
	}
	
	private boolean ourShape(Shape x) {
		String name = x.getClass().getSimpleName();
		boolean flag = false;
		final int numOfShapes = 6;
		for (int i = 0; i < numOfShapes; i++) {
			if(name.equals(ShapeId.values()[i].name())) {
				flag = true;
			}
		}
		return flag;
	}
	
	private void moveSup(int x ,int y) {
		Shape oldShape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
		Shape newShape = cloneShape(oldShape);
		
		Point p1 = new Point(((Point) newShape.getPosition()).x + x, ((Point) newShape.getPosition()).y + y);
		newShape.setPosition(p1);		
		
		drawingEngine.updateShape(oldShape, newShape);
		drawingBoard.repaint();
	}
	
	private void resizeSup(int change) {
		Shape oldShape = drawingEngine.getShapes()[(int) (shapesModel.getSelectedItem()) - 1];
		Shape newShape = cloneShape(oldShape);
		Iterator<Map.Entry<String, Double>> it = newShape.getProperties().entrySet().iterator();
		Entry<String, Double> entry;
		double ratio = 1 ;
		if(it.hasNext()) {
			entry = it.next();
			ratio = 1 + (change/entry.getValue());
			entry.setValue(entry.getValue() * ratio);
		}
		while (it.hasNext()) {
			entry = it.next();
			entry.setValue(entry.getValue() * ratio);
		}
	
		drawingEngine.updateShape(oldShape, newShape);
		drawingBoard.repaint();
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
			} else {
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

		public void passShapeInfo(ShapeId id, Point position, Map<String, Double> properties, Color color,
				Color fillColor, boolean finished) {
			Shape x = determineShape(id);
			shapeFinished = finished;
			currentShape = x;
			x.setPosition(position);
			x.setProperties(properties);
			x.setColor(color);
			x.setFillColor(fillColor);
			repaint();
		}

		public void passShapeInfo(Shape x, Point position, Map<String, Double> properties, Color color, Color fillColor,
				boolean finished) {
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
