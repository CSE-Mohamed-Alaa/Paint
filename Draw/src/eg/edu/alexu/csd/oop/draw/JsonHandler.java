package eg.edu.alexu.csd.oop.draw;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.swing.Box.Filler;

import java.util.Map.Entry;

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
				writer.println("\"posX\":\"" + x.getPosition().getX() + "\",");
				writer.println("\"posY\":\"" + x.getPosition().getY() + "\",");
				writer.println("\"Color\":\"" + x.getColor().getRGB() + "\",");
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
					writer.println("\"fillColor\":\"" + x.getFillColor().getRGB() + "\"");
				}else {
					writer.println("\"fillColor\":\"" + x.getFillColor().getRGB() + "\"");
				}
				
				if (i != shapes.size() - 1) {
					writer.println("},");
					continue;
				}
				writer.print("}");
			}

			writer.print("}");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

	}
}
