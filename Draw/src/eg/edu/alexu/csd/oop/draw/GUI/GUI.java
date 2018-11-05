package eg.edu.alexu.csd.oop.draw.GUI;

import javax.swing.JFrame;

import java.awt.EventQueue;
import javax.swing.JButton;

public class GUI {
	
	/*
	 * Catch Shape Properties :-
	 * Catch name from combo or choice box with all shapes
	 * Catch color and fill color from combo or choice box
	 * Catch the properties with listeners or enter the properties in textField (Note: listeners Easier)
	 * catch Start, End points and a name of shape like this :
	 * https://youtu.be/OOb1eil4PCo
	 * redraw every second of drag using updateShape
	 * calculate the setPosition (different with every shape)
	 * Call addShape
	 */

	private JFrame frame;

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
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);

		DrawingBoard drawingBoard = new DrawingBoard();
		drawingBoard.setBounds(10, 45, 574, 316);
		frame.getContentPane().add(drawingBoard);

		JButton drawLinebtn = new JButton("Draw Line");
		drawLinebtn.addActionListener(e -> drawingBoard.addLine(null, null));
		drawLinebtn.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(drawLinebtn);

		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(109, 11, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
}
