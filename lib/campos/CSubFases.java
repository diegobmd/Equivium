package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CSubFases  extends XSearchField{

	public CSubFases(int linha, int coluna, int largura, String label) {
		super(linha, coluna, largura, label);
	}

	public int getIndex() {
		return 0;
	}

	public XSelect getSelect() {
		XSelect select = new XSelect();

		select.setTabela("Sub_Fases");
		select.addCampo("Sub_Codigo as Codigo");
		select.addCampo("Sub_Sigla 	as Sigla");
		select.addCampo("Sub_Nome 	as Nome");

		return select;
	}

	public String getCampo() {
		return "Sub_Codigo";
	}

}
