package util;

import java.sql.SQLException;

import sql.querys.XDelete;
import sql.querys.XInsert;

public class Configuracoes {

//	public static void main(String[] args) {
//		XBanco.setBanco( new XHSQLDB("data/equivium", "sa", "") );
//		XBanco.setShowSQL(true);
//		Configuracoes.insertDadosIniciais();
//	}
	
	public static void insertDadosIniciais(){
		try {
			limpaBanco();

			XInsert insert = new XInsert();

			insert.setTabela("Escolaridade");
			insert.addCampo("Esc_Descricao", "Fundamental");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Escolaridade");
			insert.addCampo("Esc_Descricao", "Médio");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Escolaridade");
			insert.addCampo("Esc_Descricao", "Superior");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Acre");	
			insert.addCampo("Est_Sigla", "AC");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Alagoas");	
			insert.addCampo("Est_Sigla", "AL");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Amapá");	
			insert.addCampo("Est_Sigla", "AP");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Amazonas");	
			insert.addCampo("Est_Sigla", "AM");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Bahia");	
			insert.addCampo("Est_Sigla", "BA");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Ceará");	
			insert.addCampo("Est_Sigla", "CE");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Distrito Federal");	
			insert.addCampo("Est_Sigla", "DF");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Espírito Santo");	
			insert.addCampo("Est_Sigla", "ES");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Goiás");	
			insert.addCampo("Est_Sigla", "GO");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Maranhão");	
			insert.addCampo("Est_Sigla", "MA");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Mato Grosso");	
			insert.addCampo("Est_Sigla", "MT");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Mato Grosso do Sul");	
			insert.addCampo("Est_Sigla", "MS");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Minas Gerais");	
			insert.addCampo("Est_Sigla", "MG");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Pará");	
			insert.addCampo("Est_Sigla", "PA");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Paraíba");	
			insert.addCampo("Est_Sigla", "PB");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Paraná");	
			insert.addCampo("Est_Sigla", "PR");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Pernambuco");	
			insert.addCampo("Est_Sigla", "PE");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Piauí");	
			insert.addCampo("Est_Sigla", "PI");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Rio de Janeiro");	
			insert.addCampo("Est_Sigla", "RJ");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Rio Grande do Norte");	
			insert.addCampo("Est_Sigla", "RN");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Rio Grande do Sul");	
			insert.addCampo("Est_Sigla", "RS");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Rondônia");	
			insert.addCampo("Est_Sigla", "RO");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Roraima");	
			insert.addCampo("Est_Sigla", "RR");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Santa Catarina");	
			insert.addCampo("Est_Sigla", "SC");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "São Paulo");	
			insert.addCampo("Est_Sigla", "SP");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Sergipe");	
			insert.addCampo("Est_Sigla", "SE");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Estado");
			insert.addCampo("Est_Descricao", "Tocantins");	
			insert.addCampo("Est_Sigla", "TO");
			insert.executa();

			insert = new XInsert();
			insert.setTabela("Pesquisadores");
			insert.addCampo("Pes_Nome","teste");
			insert.addCampo("Pes_Login","teste");
			insert.addCampo("Pes_Senha","teste");
			insert.executa();

		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}



	}

	private static void limpaBanco(){
		XDelete delete = new XDelete();

		try {

			delete.setTabela("Anotacoes");
			delete.executa();

			delete.setTabela("Estimulos_Sub_Fase");
			delete.executa();

			delete.setTabela("Fases_Sub_Fases");
			delete.executa();

			delete.setTabela("Itens_Escolha");
			delete.executa();

			delete.setTabela("Itens_Realiza_Sessao");
			delete.executa();

			delete.setTabela("Estimulos_Audios");
			delete.executa();

			delete.setTabela("Estimulos_Figuras");
			delete.executa();

			delete.setTabela("Reforcos_Audios");
			delete.executa();

			delete.setTabela("Realiza_Sessao");
			delete.executa();

			delete.setTabela("Sessao_Fases");
			delete.executa();

			delete.setTabela("Sessoes");
			delete.executa();

			delete.setTabela("Sub_Fases");
			delete.executa();

			delete.setTabela("Fases");
			delete.executa();

			delete.setTabela("Reforcos");
			delete.executa();

			delete.setTabela("Participantes");
			delete.executa();

			delete.setTabela("Pesquisadores");
			delete.executa();

			delete.setTabela("Escolaridade");
			delete.executa();

			delete.setTabela("Estado");
			delete.executa();

			delete.setTabela("Estimulos");
			delete.executa();


		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
	}
}
