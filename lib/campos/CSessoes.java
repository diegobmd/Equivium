package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CSessoes  extends XSearchField{

	public CSessoes(int linha, int coluna, int largura, String label) {
		super(linha, coluna, largura, label);
	}

	public int getIndex() {
		return 0;
	}

	public XSelect getSelect() {
		XSelect select = new XSelect();

		select.setTabela("Sessoes");
		select.addCampo("Ses_Codigo as Codigo");
		select.addCampo("Ses_Nome 	as Nome");

		return select;
	}

	public String getCampo() {
		return "Ses_Codigo";
	}

}
