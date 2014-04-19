package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CEstimulos  extends XSearchField{

   public CEstimulos(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
   }

	public int getIndex() {
      return 0;
   }

   public XSelect getSelect() {
   	XSelect select = new XSelect();
   	
   	select.setTabela("Estimulos");
   	select.addCampo("Est_Codigo as Codigo");
   	select.addCampo("Est_Sigla  as Sigla");
   	select.addCampo("Est_nome   as Nome");
   	
      return select;
   }

   public String getCampo() {
      return "Est_Codigo";
   }
	
}
