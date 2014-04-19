package testes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TesteArrastar extends JFrame{

	JPanel panel = new JPanel(null);
	JLabel label = new JLabel("Me arraste");
	JLabel label2 = new JLabel("AUX");
	int somaX = 0;
	int somaY = 0;
	
	public TesteArrastar(){
		super("TEste");
		
		setLayout(new BorderLayout());
		
		panel.add(label);
		panel.add(label2);
		
		label.addMouseMotionListener(new Mover());
		label.addMouseListener(new Click());
		label.setSize(70, 20);
		label.setLocation(50, 50);
		label.setOpaque(true);
		label.setBackground(Color.ORANGE);
		
		label2.setSize(200, 200);
		label2.setLocation(100, 100);
		label2.setOpaque(true);
		label2.setBackground(Color.WHITE);
		
		add(panel);
		
		setSize(400,400);
	}
	
	private class Click implements MouseListener{

		public void mousePressed(MouseEvent e) {
			somaX = e.getX()*-1;
			somaY = e.getY()*-1;
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

	}
	
	private class Mover implements MouseMotionListener{

		public void mouseDragged(MouseEvent e) {
			Object c = e.getSource();
			
			int x = e.getX() + label.getBounds().x;
			int y = e.getY() + label.getBounds().y;
			
			((JComponent) c).setLocation( x+somaX, y+somaY );
			
		}
		public void mouseMoved(MouseEvent e) {}
	}
	
	public static void main(String[] args) {
		new TesteArrastar().setVisible(true);
	}
}
