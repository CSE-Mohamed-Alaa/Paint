package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DrawingEngineImp implements DrawingEngine {

	@Override
	public void refresh(Graphics canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addShape(Shape shape) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

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
