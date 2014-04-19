package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CRealizaAnagrama  extends XSearchField{

	public CRealizaAnagrama(int linha, int coluna, int largura, String label) {
		super(linha, coluna, largura, label);
	}

	public int getIndex() {
		return 0;
	}

	public XSelect getSelect() {
		XSelect select = new XSelect();

		select.setTabela("Realiza_Anagrama");
		select.addCampo("RAN_Codigo as Codigo");
		select.addCampo("par_nome   as Participante");
		select.addCampo("pes_nome   as Pesquisador");
		select.addCampo("RAN_Data 	as Data");
		
		select.addInnerJoin("participantes", "par_codigo = RAN_participante");
		select.addInnerJoin("pesquisadores", "pes_codigo = RAN_pesquisador");

		return select;
	}

	public String getCampo() {
		return "RAN_Codigo";
	}

}
