package telas.realiza.sessao;

import swing.XButton;
import util.XThread;

public class ThreadTexto  extends XThread{
	
	String texto     = "";
	XButton button   = null;
	double intervalo = 0;
	
	public void setTexto(XButton button, String texto, double intervalo){
		this.texto     = texto;
		this.button    = button;
		this.intervalo = intervalo;
	}
	
	public void run() {
		if(button != null){
			String[] tx = texto.split("[+]");
			button.setText("");
			for (int i = 0; i < tx.length; i++) {
				button.setText(button.getText()+tx[i]);
				
				try {
					sleep((long) (intervalo*1000));
				} catch (InterruptedException e) {}
			}
		}
	}

}
