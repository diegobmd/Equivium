package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CRealizaSessao extends XSearchField {

   public CRealizaSessao(int linha, int coluna, int largura, String label) {
      super(linha, coluna, largura, label);
   }

   public int getIndex() {
      return 0;
   }

   public XSelect getSelect() {
      XSelect select = new XSelect();

      select.setTabela("Realiza_Sessao");
      select.addCampo("RSe_Codigo as Codigo");
      select.addCampo("RSe_Data 	 as Data");
      select.addCampo("RSE_SIGLA  as Sigla");
      select.addCampo("RSE_NOME   As Nome");
      select.addCampo("par_nome   as Participante");
      select.addCampo("pes_nome   as Pesquisador");

      select.addInnerJoin("participantes", "par_codigo = RSe_participante");
      select.addInnerJoin("pesquisadores", "pes_codigo = RSe_pesquisador");

      return select;
   }

   public String getCampo() {
      return "RSe_Codigo";
   }

}
