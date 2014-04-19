package lib.campos;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.querys.XSelect;
import swing.XComboBox;
import util.XTrataException;

public class CEstado extends XComboBox<String>{

	public CEstado(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
	   
	   XSelect select = new XSelect();
	   
	   select.setTabela("Estado");
	   select.addCampo("*");
	   
	   try {
	      ResultSet rs = select.executa();
	      
	      while(rs.next()){
	      	addItemCodigo(rs.getString("Est_Sigla") + " - " + rs.getString("Est_Descricao"), rs.getString("Est_Sigla"));
	      }
	      
      } catch (SQLException e) {
	      XTrataException.printStackTrace(e);
      }
	   
   }
	
	public String getSelectedCodigo() {
		if ( getSelectedIndex() >= 0 ){
			return super.getSelectedCodigo();
		}
	   return null;
	}

}
