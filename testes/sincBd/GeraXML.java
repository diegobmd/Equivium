package testes.sincBd;

import java.io.IOException;
import java.sql.SQLException;

import sql.bancos.XBanco;
import sql.bancos.XHSQLDB;
import sql.bancos.sinc.XGeraXML;


public class GeraXML {

	public static void main(String[] args) {
		try {
		   XBanco.setBanco(new XHSQLDB("banco/hsqldb2/equivium", "sa", "") );
		   
		   XBanco.setShowSQL(true);
		   
			new GeraXML().geraXml();
			
//			new XAtualizaBanco().atualizaBanco();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (JDOMException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public void geraXml() throws SQLException, IOException {
		XGeraXML g = new XGeraXML();
		
		g.open("EQUIVIUM");
		
		g.addTabela("ANOTACOES");
		g.addTabela("ESCOLARIDADE");
		g.addTabela("ESTADO");
		g.addTabela("ESTIMULOS");
		g.addTabela("ESTIMULOS_AUDIOS");
		g.addTabela("ESTIMULOS_FIGURAS");
		g.addTabela("ESTIMULOS_SUB_FASE");
		g.addTabela("FASES");
		g.addTabela("FASES_SUB_FASES");
		g.addTabela("ITENS_REALIZA_SESSAO");
		g.addTabela("PARTICIPANTES");
		g.addTabela("PESQUISADORES");
		g.addTabela("REALIZA_SESSAO");
		g.addTabela("REFORCOS");
		g.addTabela("REFORCOS_AUDIOS");
		g.addTabela("SESSAO_FASES");
		g.addTabela("SESSOES");
		g.addTabela("SUB_FASES");
		g.addTabela("ANAGRAMAS");
		g.addTabela("ANAGRAMA_ESTIMULOS");
		g.addTabela("ANAGRAMA_ESTI_EXTRA");
		g.addTabela("SESSAO_ANAGRAMA");
		g.addTabela("SESSOES_ANAGRAMA");
		g.addTabela("REALIZA_ANAGRAMA");
		g.addTabela("ITENS_REALIZA_ANAGRAMA");
		
		
		g.close();
	}
	
	
}
