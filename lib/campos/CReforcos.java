package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CReforcos  extends XSearchField{

   public CReforcos(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
   }

	public int getIndex() {
      return 0;
   }

   public XSelect getSelect() {
   	XSelect select = new XSelect();
   	
   	select.setTabela("Reforcos");
   	select.addCampo("Ref_Codigo 	as Codigo");
   	select.addCampo("Ref_Nome 	as Nome");
   	
      return select;
   }

   public String getCampo() {
      return "Ref_Codigo";
   }
	
}
