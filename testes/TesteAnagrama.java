package testes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import swing.XButton;
import swing.XFrame;
import telas.realiza.anagrama.PanelAnagrama;

public class TesteAnagrama extends XFrame{

	private final PanelAnagrama panel 		= new PanelAnagrama(4);
	private final XButton 		but 		= new XButton(2,2, 10, "Listar");
	
	public TesteAnagrama(String[] conjuntos, String[] extras){
		super(640, 480);
		
		setTitle("Anagrama");
		setLayout(new BorderLayout());
		add(panel);
		
		panel.addCubos(conjuntos);
		panel.addCubos(extras);
		
		panel.setEncaixes(3);
		
		but.addActionListener(new AcaoBotao());
		
		setResizable(true);
		
		panel.add(but);
	}
	
	private class AcaoBotao implements ActionListener{

		public void actionPerformed(ActionEvent e) {
//			System.out.println(panel.getCubos());
			panel.limparCubos();
		}
	}
	
	public static void main(String[] args) {
		new TesteAnagrama(new String[]{"CA", "BO", "AA", "PP"}, new String[]{"LA", "LO"}).setFullScreenMode();
//		new TesteAnagrama(new String[]{"CA", "BO", "AA", "PP"}, new String[]{"LA", "LO"}).setVisible(true);
	}
	
}

