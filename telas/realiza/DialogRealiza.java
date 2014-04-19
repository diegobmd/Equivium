package telas.realiza;

import swing.XDialog;

public class DialogRealiza extends XDialog{

	public DialogRealiza() {
		super(800, 600);
		
	}

	public void dispose() {
		setVisible(false);
	}
	
	public void dispose2() {
		super.dispose();
	}
}
