package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CParticipantes extends XSearchField{

   public CParticipantes(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
   }

	public int getIndex() {
      return 0;
   }

   public XSelect getSelect() {
   	XSelect select = new XSelect();
   	
   	select.setTabela("Participantes");
   	select.addCampo("Par_Codigo as CODIGO");
   	select.addCampo("Par_Nome   as NOME");
   	
      return select;
   }

   public String getCampo() {
      return "Par_Codigo";
   }
	
}
