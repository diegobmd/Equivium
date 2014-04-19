package testes;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class HiglightAndForeground {
	public static void main(String[] args) throws Exception {
		
		
		JFrame windo;
		JTextPane textpane;
		SimpleAttributeSet attributes;
		DefaultHighlighter.DefaultHighlightPainter light;

		windo = new JFrame();
		windo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textpane = new JTextPane();
		windo.getContentPane().add(textpane);
		attributes = new SimpleAttributeSet();
		
		StyleConstants.setForeground(attributes, Color.MAGENTA);
		textpane.setCharacterAttributes(attributes, false);
		
		textpane.setText("A l'heure vague où les fantômes en grand nombre");
		
		light = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		textpane.getHighlighter().addHighlight(4, 15, light);
		windo.pack();
		windo.setVisible(true);
	}
}