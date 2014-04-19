package lib.campos;

import sql.querys.XSelect;
import swing.XSearchField;

public class CPesquisadores  extends XSearchField{

	public CPesquisadores(int linha, int coluna, int largura, String label) {
		super(linha, coluna, largura, label);
	}

	public int getIndex() {
		return 0;
	}

	public XSelect getSelect() {
		XSelect select = new XSelect();

		select.setTabela("Pesquisadores");
		select.addCampo("Pes_Codigo 	as Codigo");
		select.addCampo("Pes_Nome 	as Nome");

		return select;
	}

	public String getCampo() {
		return "Pes_Codigo";
	}
	
}
