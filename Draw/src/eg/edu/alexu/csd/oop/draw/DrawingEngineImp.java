package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DrawingEngineImp implements DrawingEngine {
	private ArrayList<Shape> shapes = new ArrayList<>();
	Stack<ArrayList<Shape>> undoStack = new Stack<>();
	Stack<ArrayList<Shape>> redoStack = new Stack<>();
	@Override
	public void refresh(Graphics canvas) {
		
		for (Shape x : shapes) {
			x.draw(canvas);
		}

	}

	@Override
	public void addShape(Shape shape) {
		undoStack.push((ArrayList<Shape>) shapes.clone());
		shapes.add(shape); 
		
	}

	@Override
	public void removeShape(Shape shape) {
		undoStack.push((ArrayList<Shape>) shapes.clone());
		for (int i = 0; i < shapes.size(); i++) {
			if (shape == shapes.get(i)) {
				shapes.remove(i);
			}
			
		}

	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape[] getShapes() {
		Shape [] x = new Shape[shapes.size()];
		return  x = shapes.toArray(x);
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> ans = new ArrayList<Class<? extends Shape>>();
		JarFile jarFile;
		try {
			jarFile = new JarFile("RoundRectangle.jar");
		
		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + "RoundRectangle.jar"+"!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (e.hasMoreElements()) {
		    JarEntry je = e.nextElement();
		    if(je.isDirectory() || !je.getName().endsWith(".class")){
		        continue;
		    }
		    // -6 because of .class
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    Class<? extends Shape> c = (Class<? extends Shape>) cl.loadClass(className);
		    Object x = c.newInstance();
		    if (x instanceof Shape) {
				ans.add(c);
			}

		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ans;
	}

	@Override
	public void undo() { 
		if (undoStack.size() <= 20 && !undoStack.isEmpty()) {
			redoStack.push(shapes);
			shapes = undoStack.pop();
	}
		
	}

	@Override
	public void redo() {
		undoStack.push(shapes);
		shapes = redoStack.pop();
		

	}

	@Override
	public void save(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub

	}
}
