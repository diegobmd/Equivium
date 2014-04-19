package conversores;

import java.sql.SQLException;

import sql.querys.XDelete;
import sql.querys.XInsert;

public class LimpaBanco {

//	public static void main(String[] args) {
//		
//		XBanco.setShowSQL(true);
//
//		XBanco.setBanco(new XHSQLDB( "banco/hsqldb/equivium" , "sa", ""  ));
//
//		try{
//			
//			new LimpaBanco().limpar();
//			
//			XBanco.getConexao().close();
//		}
//		catch(SQLException e){
//			XTrataException.printStackTrace(e);
//		}
//		
//	}
	
	
	public void limpar() throws SQLException{

		XDelete delete = new XDelete();
		delete.setTabela("itens_realiza_sessao"); 	delete.executa();
		delete.setTabela("estimulos_sub_fase"); 	delete.executa();
		delete.setTabela("estimulos_figuras"); 		delete.executa();
		delete.setTabela("estimulos_audios"); 		delete.executa();
		delete.setTabela("estimulos"); 				delete.executa();
		delete.setTabela("sessao_fases"); 			delete.executa();
		delete.setTabela("sessoes"); 				delete.executa();
		delete.setTabela("fases_sub_fases"); 		delete.executa();
		delete.setTabela("fases"); 					delete.executa();
		delete.setTabela("sub_fases"); 				delete.executa();
		delete.setTabela("reforcos_audios"); 		delete.executa();
		delete.setTabela("reforcos"); 				delete.executa();
		delete.setTabela("realiza_sessao"); 		delete.executa();
		delete.setTabela("pesquisadores"); 			delete.executa();
		delete.setTabela("anotacoes"); 				delete.executa();
		delete.setTabela("participantes"); 			delete.executa();
		delete.setTabela("escolaridade"); 			delete.executa();
		delete.setTabela("estado"); 				delete.executa();
		XInsert insert;

		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "AC");insert.addCampo("est_descricao", "Acre");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "AL");insert.addCampo("est_descricao", "Alagoas");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "AP");insert.addCampo("est_descricao", "Amap�");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "AM");insert.addCampo("est_descricao", "Amazonas");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "BA");insert.addCampo("est_descricao", "Bahia");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "CE");insert.addCampo("est_descricao", "Cear�");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "DF");insert.addCampo("est_descricao", "Distrito Federal");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "ES");insert.addCampo("est_descricao", "Esp�rito Santo");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "GO");insert.addCampo("est_descricao", "Goi�s");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "MA");insert.addCampo("est_descricao", "Maranh�o");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "MT");insert.addCampo("est_descricao", "Mato Grosso");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "MS");insert.addCampo("est_descricao", "Mato Grosso do Sul");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "MG");insert.addCampo("est_descricao", "Minas Gerais");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "PA");insert.addCampo("est_descricao", "Par�");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "PB");insert.addCampo("est_descricao", "Para�ba");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "PR");insert.addCampo("est_descricao", "Paran�");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "PE");insert.addCampo("est_descricao", "Pernambuco");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "PI");insert.addCampo("est_descricao", "Piau�");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "RJ");insert.addCampo("est_descricao", "Rio de Janeiro");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "RN");insert.addCampo("est_descricao", "Rio Grande do Norte");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "RS");insert.addCampo("est_descricao", "Rio Grande do Sul");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "RO");insert.addCampo("est_descricao", "Rond�nia");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "RR");insert.addCampo("est_descricao", "Roraima");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "SC");insert.addCampo("est_descricao", "Santa Catarina");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "SP");insert.addCampo("est_descricao", "S�o Paulo");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "SE");insert.addCampo("est_descricao", "Sergipe");insert.executa();
		insert = new XInsert(); insert.setTabela("estado");insert.addCampo("est_sigla", "TO");insert.addCampo("est_descricao", "Tocantins");insert.executa();

		insert = new XInsert(); insert.setTabela("escolaridade");insert.addCampo("esc_descricao", "Pr�");insert.executa();
		insert = new XInsert(); insert.setTabela("escolaridade");insert.addCampo("esc_descricao", "Ensino Fundamental");insert.executa();
		insert = new XInsert(); insert.setTabela("escolaridade");insert.addCampo("esc_descricao", "Ensino M�dio");insert.executa();
		insert = new XInsert(); insert.setTabela("escolaridade");insert.addCampo("esc_descricao", "T�cnico");insert.executa();
		insert = new XInsert(); insert.setTabela("escolaridade");insert.addCampo("esc_descricao", "Superior");insert.executa();

	}
		
	
}
