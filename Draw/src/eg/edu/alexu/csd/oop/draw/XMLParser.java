package eg.edu.alexu.csd.oop.draw;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class XMLParser {

	public static void main(String[] args) {

		saveXML("C:\\Users\\M2\\Desktop\\New folder\\test.txt", null);
		System.out.println("Done");
	}

	// TODO remove static and main
	public static void saveXML(String path, ArrayList<Shape> shapes) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		writer.println("<paint>");

		for (Shape shape : shapes) {
			writer.println("<shape id=" + shape.getClass() + ">");

			writer.println("<x>" + shape.getPosition().getX() + "</x>");
			writer.println("<y>" + shape.getPosition().getY() + "</y>");

			writer.println("<map>");
			Iterator<Map.Entry<String, Double>> it = shape.getProperties().entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Double> entry = it.next();
				writer.println("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");

			}
			writer.println("</map>");

			writer.println("<color>" + shape.getColor() + "</color>");
			writer.println("<fillcolor>" + shape.getFillColor() + "</fillcolor>");

			writer.println("</shape>");
		}

		writer.println("</paint>");

		writer.close();
	}

}
