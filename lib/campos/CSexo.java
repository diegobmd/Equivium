package lib.campos;

import swing.XComboBox;

public class CSexo extends XComboBox<Character>{

	public CSexo(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
	   
	   addItemCodigo("Masculino", 'M');
	   addItemCodigo("Feminino" , 'F');
	   
   }
	
	
	public Character getSelectedCodigo() {
		if ( getSelectedIndex() >= 0 ){
			return super.getSelectedCodigo();
		}
	   return ' ';
	}

}
