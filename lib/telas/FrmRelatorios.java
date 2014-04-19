package lib.telas;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * 
 * <B> Tela para formularios de cadastro </B>
 * 
 * @author Diego
 *
 */
public abstract class FrmRelatorios extends FrmMenuBar{
	
	protected final int INC = 0; 
	protected       int OPE = -1;
	
	private final int operacao = 1000; 
	private final int cancelar = 2000;
	
	public FrmRelatorios(String title) {
		super(title);
		
		addButton("Iniciar" 	, actionIniciar	, "./imagens/sis/b_incluir.gif" , operacao);
		addButton("Ok" 		, actionOk			, "./imagens/sis/b_ok.gif"      , cancelar);
		addButton("Cancelar" , actionCancelar	, "./imagens/sis/b_cancelar.gif", cancelar);

		addMenu("Arquivo");
		
		addSubMenu("Arquivo", "Iniciar" 	, actionIniciar	, "./imagens/sis/bp_incluir.gif" , operacao);
		addSubMenu("Arquivo", "Ok" 		, actionOk			, "./imagens/sis/bp_ok.gif"      , cancelar);
		addSubMenu("Arquivo", "Cancelar" , actionCancelar	, "./imagens/sis/bp_cancelar.gif", cancelar);

	}
	
	public void setVisible(boolean b) {
	   super.setVisible(b);
	   
	   if(b){
	   	cancelarClick();
	   }
	}

	public abstract void initComponentes();

	Action actionOk = new AbstractAction(){
		public void actionPerformed(ActionEvent e) {
			okClick();
		}
	};
	

	Action actionCancelar = new AbstractAction(){
		public void actionPerformed(ActionEvent e) {
			getButton("Ok").setVerifyInputWhenFocusTarget(true);
			cancelarClick();
		}
	};
	
	Action actionIniciar = new AbstractAction(){
		public void actionPerformed(ActionEvent e) {
			IniciarClick();
		}
	};
	
	public abstract void doIniciar();
	
	Action actionSair = new AbstractAction(){
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
   public void cancelarClick() {
   	controlView.setControl(operacao, true , false);
   	controlView.setControl(cancelar, false, false);
   	controlView.setControl(CAMPO_CHAVE , false, true);
   	controlView.setControl(CAMPO_NORMAL, false, true);
   	tabbedPane.setSelectedIndex(0);
   }

   public void IniciarClick() {
   	controlView.setControl(operacao, false, false);
   	controlView.setControl(cancelar, true , false);
   	controlView.setControl(CAMPO_CHAVE , false, true);
   	controlView.setControl(CAMPO_NORMAL, true , true);
   	controlView.requestFocus(CAMPO_NORMAL);
   	OPE = INC;
   }

	public void okClick(){

		switch (OPE) {
			case INC:
				if (!controlView.validaCampos(CAMPO_NORMAL)){
					return;
				}
				doIniciar();
				IniciarClick();
				
				break;
			default:
				break;
		}
	
	}
}


