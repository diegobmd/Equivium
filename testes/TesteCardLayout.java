package testes;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import swing.XButton;
import swing.XFrame;
import swing.XLabel;
import swing.XPanel;

public class TesteCardLayout extends XFrame{

	XPanel panel = new XPanel();
	XPanel panel2 = new XPanel(1, 1, 30, 10);
	XPanel panel3 = new XPanel(0, 0, 30, 10);
	XPanel panel4 = new XPanel(0, 0, 30, 10);
	
	XLabel label0 = new XLabel(1,1,"Panel 0 ");
	XLabel label1 = new XLabel(1,1,"Panel 1 ");
	
	XButton button = new XButton(10,5, 30, "trocar");
	
	final CardLayout card = new CardLayout();
	
	public TesteCardLayout() {
		super(800, 600);
		
		setLayout(new BorderLayout());
		
		add(panel);
		
		panel2.setLayout( card );
		
		panel2.setBackground(Color.yellow);
		panel3.setBackground(Color.blue);
		panel4.setBackground(Color.red);
		
		panel.add(panel2);
		
		panel2.add(panel3, "1");
		panel2.add(panel4, "2");
		
		//card.layoutContainer(panel2);
		
		panel3.add(label0);
		panel4.add(label1);
	
		panel.add(button);
		
		button.addActionListener(new ActionListener(){

			boolean a = false;
			public void actionPerformed(ActionEvent e) {
				
				if(a){
					card.show(panel2, "1");
					System.out.println("1");
				}else{
					card.show(panel2, "2");
					System.out.println("2");
				}
				
				a = !a;
			}
			
		});
	}
	
	
	public static void main(String[] args) {
		new TesteCardLayout().setVisible(true);
	}

	
}
