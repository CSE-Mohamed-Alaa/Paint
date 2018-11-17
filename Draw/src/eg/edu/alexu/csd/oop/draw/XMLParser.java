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
			if(shape.getPosition() != null) {
				writer.println("<x>" + shape.getPosition().x + "</x>");
				writer.println("<y>" + shape.getPosition().y + "</y>");
			}
			writer.println("<map>");
			if(shape.getPosition() != null) {
				Iterator<Map.Entry<String, Double>> it = shape.getProperties().entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Double> entry = it.next();
					writer.println("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
	
				}
			}
			writer.println("</map>");
			
			if(shape.getColor() != null) {
				writer.println("<color>" + shape.getColor().getRGB() + "</color>");
			}
			if(shape.getFillColor() != null) {
				writer.println("<fillcolor>" + shape.getFillColor().getRGB() + "</fillcolor>");
			}
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
		Pattern colorPattern = Pattern.compile("<color>(\\S+)</color>");
		Pattern fillColorPattern = Pattern.compile("<fillcolor>(\\S+)</fillcolor>");

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
				thisLine = reader.readLine();
				matcher = valuePattern.matcher(thisLine);
				String x = null;
				if(matcher.matches()) {
					x = matcher.group(2);
					thisLine = reader.readLine();
				}
				matcher = valuePattern.matcher(thisLine);
				String y = null;
				if(matcher.matches()) {
					y = matcher.group(2);
					thisLine = reader.readLine();
				}
				if(x != null && y!= null) {
					shape.setPosition(new Point(Integer.parseInt(x), Integer.parseInt(y)));
				}
				
				//read Map
				if(thisLine.equals("<map>")){
					Map<String, Double> properties = new HashMap<>();
					while(!(thisLine = reader.readLine()).equals("</map>")) {
						matcher = valuePattern.matcher(thisLine);
						matcher.matches();
						properties.put(matcher.group(1),Double.parseDouble(matcher.group(2)));
					}
					if(!properties.isEmpty()) {
						shape.setProperties(properties);
					}
					thisLine = reader.readLine();
				}
				
				//read Color
				matcher = colorPattern.matcher(thisLine);
				if(matcher.matches()) {
					shape.setColor(new Color(Integer.parseInt(matcher.group(1))));
					thisLine = reader.readLine();
				}
				//read fillColor
				matcher = fillColorPattern.matcher(thisLine);
				if(matcher.matches()) {
					shape.setFillColor(new Color(Integer.parseInt(matcher.group(1))));
					thisLine = reader.readLine();
				}
				
				if(thisLine.equals("</shape>")) {
					allShapes.add(shape);	
				}
				
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allShapes;
	}
}
