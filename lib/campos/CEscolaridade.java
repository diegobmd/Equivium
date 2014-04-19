package lib.campos;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.querys.XSelect;
import swing.XComboBox;
import util.XTrataException;

public class CEscolaridade extends XComboBox<Integer>{

	public CEscolaridade(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
	   
	   XSelect select = new XSelect();
	   
	   select.setTabela("Escolaridade");
	   select.addCampo("*");
	   
	   try {
	      ResultSet rs = select.executa();
	      
	      while(rs.next()){
	      	addItemCodigo(rs.getString("Esc_Descricao"), rs.getInt("Esc_Codigo"));
	      }
	      
      } catch (SQLException e) {
	      XTrataException.printStackTrace(e);
      }
	   
   }
	
	public Integer getSelectedCodigo() {
		if ( getSelectedIndex() >= 0 ){
			return super.getSelectedCodigo();
		}
	   return 0;
	}

}
