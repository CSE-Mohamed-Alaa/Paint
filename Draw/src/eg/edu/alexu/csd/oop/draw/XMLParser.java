package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {

	public void saveXML(String path, ArrayList<Shape> shapes) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		writer.println("<paint>");

		for (Shape shape : shapes) {
			writer.println("<shape id=" + shape.getClass().getName() + ">");

			writer.println("<x>" + shape.getPosition().x + "</x>");
			writer.println("<y>" + shape.getPosition().y + "</y>");

			writer.println("<map>");
			Iterator<Map.Entry<String, Double>> it = shape.getProperties().entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Double> entry = it.next();
				writer.println("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");

			}
			writer.println("</map>");

			writer.println("<color>" + shape.getColor().getRGB() + "</color>");
			writer.println("<fillcolor>" + shape.getFillColor().getRGB() + "</fillcolor>");

			writer.println("</shape>");
		}

		writer.println("</paint>");

		writer.close();
	}

	public ArrayList<Shape> loadXML(String path) {

		ArrayList<Shape> allShapes = new ArrayList<>();
		Shape shape = null;
		BufferedReader reader = null;
		String thisLine = null;

		Pattern idPattern = Pattern.compile("<shape id=(\\S+)>");
		Pattern valuePattern = Pattern.compile("<(\\S+)>(\\S+)</(\\S+)>");
		Matcher matcher = null;

		try {
			reader = new BufferedReader(new FileReader(path));
			//escape <paint>
			reader.readLine();
			while (!(thisLine = reader.readLine()).equals("</paint>")) {
				//read shape ID
				matcher = idPattern.matcher(thisLine);
				matcher.matches();
				@SuppressWarnings("unchecked")
				Class<? extends Shape> tempShape = (Class<? extends Shape>) Class.forName(matcher.group(1));
				shape = tempShape.newInstance();
				
				//read position
				matcher = valuePattern.matcher(reader.readLine());
				matcher.matches();
				String x = matcher.group(2);
				matcher = valuePattern.matcher(reader.readLine());
				matcher.matches();
				String y = matcher.group(2);
				shape.setPosition(new Point(Integer.parseInt(x), Integer.parseInt(y)));
				
				//read Map
				reader.readLine();
				Map<String, Double> properties = new HashMap<>();
				while(!(thisLine = reader.readLine()).equals("</map>")) {
					matcher = valuePattern.matcher(thisLine);
					matcher.matches();
					properties.put(matcher.group(1),
							Double.parseDouble(matcher.group(2)));
				}
				shape.setProperties(properties);
				
				//read Color
				matcher = valuePattern.matcher(reader.readLine());
				matcher.matches();
				shape.setColor(new Color(Integer.parseInt(matcher.group(2))));
			
				//read fillColor
				matcher = valuePattern.matcher(reader.readLine());
				matcher.matches();
				shape.setFillColor(new Color(Integer.parseInt(matcher.group(2))));
				
				//escape </shape>
				reader.readLine();
				
				allShapes.add(shape);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allShapes;
	}
}
