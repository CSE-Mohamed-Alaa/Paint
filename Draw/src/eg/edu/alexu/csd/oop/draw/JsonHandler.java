package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.Scanner;

public class JsonHandler {
	public static void Save(String path, ArrayList<Shape> shapes) {
		PrintWriter writer;

		try {
			writer = new PrintWriter("path");

			writer.print("{");
			writer.print("\"shapes\":[\n");
			for (int i = 0; i < shapes.size(); i++) {
				Shape x = shapes.get(i);
				writer.println("{");
				writer.println("\"class\":\"" + x.getClass() + "\",");
				writer.println("\"posX\":\"" + ((Point) x.getPosition()).getX() + "\",");
				writer.println("\"posY\":\"" + ((Point) x.getPosition()).getY() + "\",");
				writer.println("\"Color\":\"" + ((Color) x.getColor()).getRGB() + "\",");
				writer.println("\"properties\":{");
				Iterator<Map.Entry<String, Double>> it = x.getProperties().entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Double> entry = it.next();
					if (it.hasNext()) {
						writer.println("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
					} else {
						writer.println("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
					}

				}
				writer.println("},");
				if (x.getFillColor() != null) {
					writer.println("\"fillColor\":\"" + ((Color) x.getFillColor()).getRGB() + "\"");
				} else {
					writer.println("\"fillColor\":\"" + ((Color) x.getFillColor()).getRGB() + "\"");
				}

				if (i != shapes.size() - 1) {
					writer.println("},");
					continue;
				}
				writer.print("}");
			}

			writer.print("]}");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void load(String path, ArrayList<Shape> shapes) {
		try {
			Scanner in = new Scanner(new File(path));
			in.nextLine();
			ArrayList<Shape> loadedShapes = new ArrayList<>();
			while (in.hasNextLine()) {
				in.nextLine();
				
				Pattern regex = Pattern.compile(".class...class eg.edu.alexu.csd.oop.draw.(\\w+)..");
				Matcher regexChecker = regex.matcher(in.nextLine());
				regexChecker.matches();
				Shape x = determineShape(regexChecker.group(1));
				regex = Pattern.compile(".posX.:.(\\d+).0..");
				regexChecker = regex.matcher(in.nextLine()); 
				regexChecker.matches();
				int posx = Integer.parseInt(regexChecker.group(1));
				regex = Pattern.compile(".posY.:.(\\d+).0..");
				regexChecker = regex.matcher(in.nextLine()); 
				regexChecker.matches();
				int posy = Integer.parseInt(regexChecker.group(1));
				x.setPosition(new Point(posx, posy));
				regex = Pattern.compile(".Color.:.(-?\\d)..");
				regexChecker = regex.matcher(in.nextLine());
				regexChecker.matches();
				x.setColor(new Color(Integer.parseInt(regexChecker.group(1))));
				
				
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Shape determineShape(String className) {
		Shape x = null;
		if (className.equals("Line")) {
			x = new Line();
		} else if (className.equals("Circle")) {
			x = new Circle();
		} else if (className.equals("Ellipse")) {
			x = new Ellipse();
		} else if (className.equals("Rectangle")) {
			x = new Rectangle();
		}else if (className.equals("Square")) {
			x = new Square();
		}else {
			x = new Triangle();
		}
		return x;
	}
	public static void main(String[] args) {

	}

}
