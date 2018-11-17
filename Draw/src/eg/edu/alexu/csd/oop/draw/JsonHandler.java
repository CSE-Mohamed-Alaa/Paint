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
import java.util.HashMap;
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
			writer = new PrintWriter(path);

			writer.print("{");
			writer.print("\"shapes\":[\n");
			for (int i = 0; i < shapes.size(); i++) {
				Shape x = shapes.get(i);
				writer.println("{");
				writer.println("\"class\":\"" + x.getClass() + "\",");
				if (x.getPosition() == null) {
					writer.println("\"posX\":\"" + "null" + "\",");
					writer.println("\"posY\":\"" + "null" + "\",");
				} else {
					writer.println("\"posX\":\"" + ((Point) x.getPosition()).getX() + "\",");
					writer.println("\"posY\":\"" + ((Point) x.getPosition()).getY() + "\",");
				}
				if (x.getColor() != null) {
					writer.println("\"color\":\"" + ((Color) x.getColor()).getRGB() + "\",");
				} else {
					writer.println("\"color\":\"" + "null" + "\",");
				}
				if (x.getProperties() == null) {
					writer.println("\"properties\":\"null\",");
				} else {
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
				}
				if (x.getFillColor() != null) {
					writer.println("\"fillColor\":\"" + ((Color) x.getFillColor()).getRGB() + "\"");
				} else {
					writer.println("\"fillColor\":\"" + "null" + "\"");
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

	public static ArrayList<Shape> load(String path) {
		ArrayList<Shape> loadedShapes = new ArrayList<>();
		try {
			Scanner in = new Scanner(new File(path));
			in.nextLine();
			while (in.hasNextLine()) {
				in.nextLine();
				Pattern regex = Pattern.compile(".class...class (\\S+)\",");
				Matcher regexChecker = regex.matcher(in.nextLine());
				regexChecker.matches();
				@SuppressWarnings("unchecked")
				Class<? extends Shape> tempShape = (Class<? extends Shape>) Class.forName(regexChecker.group(1));
				Shape x = tempShape.newInstance();
				regex = Pattern.compile(".posX.:.(\\d+).0..");
				regexChecker = regex.matcher(in.nextLine());
				if (regexChecker.matches()) {
					int posx = Integer.parseInt(regexChecker.group(1));
					regex = Pattern.compile(".posY.:.(\\d+).0..");
					regexChecker = regex.matcher(in.nextLine());
					regexChecker.matches();
					int posy = Integer.parseInt(regexChecker.group(1));
					x.setPosition(new Point(posx, posy));
				}else {
					in.nextLine();
				}
				regex = Pattern.compile(".color.:.(-?\\d+)..");
				regexChecker = regex.matcher(in.nextLine());
				if(regexChecker.matches()) {
				x.setColor(new Color(Integer.parseInt(regexChecker.group(1))));
				}
				regex = Pattern.compile("\"properties\":.");
				regexChecker = regex.matcher(in.nextLine());
				if(regexChecker.matches()) {
					regex = Pattern.compile("\"(\\S+)\":\"(\\S+)\",?");
					regexChecker = regex.matcher(in.nextLine());
					HashMap<String, Double> prob = new HashMap<>();
					while (regexChecker.matches()) {
						prob.put(regexChecker.group(1), Double.valueOf(regexChecker.group(2)));
						regex = Pattern.compile("\"(\\S+)\":\"(\\S+)\",?");
						regexChecker = regex.matcher(in.nextLine());
					}
					x.setProperties(prob);
				}
				regex = Pattern.compile(".fillColor.:.(-?\\d+)..");
				regexChecker = regex.matcher(in.nextLine());
				if(regexChecker.matches()) {
				x.setFillColor(new Color(Integer.parseInt(regexChecker.group(1))));
				}
				loadedShapes.add(x);
				in.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadedShapes;

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
		} else if (className.equals("Square")) {
			x = new Square();
		} else {
			x = new Triangle();
		}
		return x;
	}

	public static void main(String[] args) {

	}

}
