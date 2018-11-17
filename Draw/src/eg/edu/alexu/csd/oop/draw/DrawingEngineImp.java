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
	public void refresh(Object oCanvas) {
		Graphics canvas = (Graphics)oCanvas;
		for (Shape x : shapes) {
			x.draw(canvas);
		}

	}

	@Override
	public void addShape(Shape shape) {
		updateStacks();
		shapes.add(shape); 
		
	}

	@Override
	public void removeShape(Shape shape) {
		updateStacks();
		for (int i = 0; i < shapes.size(); i++) {
			if (shape == shapes.get(i)) {
				shapes.remove(i);
			}
			
		}

	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		updateStacks();
		for (int i = 0; i < shapes.size(); i++) {
			if (oldShape == shapes.get(i)) {
				shapes.set(i, newShape);
			}

		}

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
		if (!undoStack.isEmpty()) {
			redoStack.push(shapes);
			shapes = undoStack.pop();
		}
		
	}

	@Override
	public void redo() {
		
		if (!redoStack.isEmpty()) {
			undoStack.push(shapes);
			shapes = redoStack.pop();
		}
		

	}
	
	void updateStacks() {
		redoStack.removeAllElements();
		if (undoStack.size() == 20) {
			undoStack.remove(0);
		}
		undoStack.push((ArrayList<Shape>) shapes.clone());
	}
	
	@Override
	public void save(String path) {
		String ext = getExtension(path);
		if(ext.equalsIgnoreCase("xml")) {
			XMLParser.saveXML(path, shapes);
		}else if(ext.equalsIgnoreCase("json")){
			JsonHandler.Save(path, shapes);
		}
	}

	@Override
	public void load(String path) {
		String ext = getExtension(path);
		if(ext.equalsIgnoreCase("xml")) {
			shapes = XMLParser.loadXML(path);
			undoStack.removeAllElements();
			redoStack.removeAllElements();
		}else if(ext.equalsIgnoreCase("json")){
			shapes = JsonHandler.load(path);
			undoStack.removeAllElements();
			redoStack.removeAllElements();
		}

	}
	private String getExtension(String path) {
		String tempExt = path.substring(path.length()-4, path.length());
		if(tempExt.equalsIgnoreCase(".xml")) {
			return "xml";
		}else if (tempExt.equalsIgnoreCase("json")){
			return "json";
		}else {
			return null;
		}
	}
}
