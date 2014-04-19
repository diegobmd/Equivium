package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CAnotacoes  extends XSearchField{

   public CAnotacoes(int linha, int coluna, int largura, String label) {
	   super(linha, coluna, largura, label);
   }

	public int getIndex() {
      return 0;
   }

   public XSelect getSelect() {
   	XSelect select = new XSelect();
   	
   	select.setTabela("Anotacoes");
   	
   	select.addCampo("Ano_Codigo as Código");
   	select.addCampo("Ano_Nome  	as Anotação");
   	select.addCampo("Par_Nome 	as Participante");
   	select.addCampo("Ano_Data  	as Data");
   	
   	select.addInnerJoin("Participantes", "Participantes.Par_Codigo = Anotacoes.Ano_Participante");
   	
      return select;
   }

   public String getCampo() {
      return "Ano_Codigo";
   }

	
}
