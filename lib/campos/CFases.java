package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CFases  extends XSearchField{

   public CFases(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
   }

	public int getIndex() {
      return 0;
   }

   public XSelect getSelect() {
   	XSelect select = new XSelect();
   	
   	select.setTabela("Fases");
   	select.addCampo("Fas_Codigo as Codigo");
   	select.addCampo("Fas_Sigla  as Sigla");
   	select.addCampo("Fas_nome   as Nome");
   	
      return select;
   }

   public String getCampo() {
      return "Fas_Codigo";
   }
	
}
