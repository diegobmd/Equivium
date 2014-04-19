package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CAnagramas  extends XSearchField{

	public CAnagramas(int linha, int coluna, int largura, String label) {
		super(linha, coluna, largura, label);
	}

	public int getIndex() {
		return 0;
	}

	public XSelect getSelect() {
		XSelect select = new XSelect();

		select.setTabela("ANAGRAMAS");
		select.addCampo("ANA_CODIGO as Codigo");
		select.addCampo("ANA_SIGLA 	as Sigla");
		select.addCampo("ANA_NOME 	as Nome");

		return select;
	}

	public String getCampo() {
		return "Sub_Codigo";
	}

}
