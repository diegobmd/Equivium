package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CSessoesAnagrama  extends XSearchField{

	public CSessoesAnagrama(int linha, int coluna, int largura, String label) {
		super(linha, coluna, largura, label);
	}

	public int getIndex() {
		return 0;
	}

	public XSelect getSelect() {
		XSelect select = new XSelect();

		select.setTabela("SESSOES_ANAGRAMA");
		select.addCampo("SAN_CODIGO as Codigo");
		select.addCampo("SAN_SIGLA 	as Sigla");
		select.addCampo("SAN_NOME 	as Nome");

		return select;
	}

	public String getCampo() {
		return "SAN_CODIGO";
	}

}
