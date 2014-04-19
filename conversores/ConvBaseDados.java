package conversores;


public class ConvBaseDados {
	
//	public static void main(String[] args) {
//		new ConvBaseDados();
//	}
//	
//	private XAccess access;
//	private XHSQLDB hsqldb;
//	
//	private HashMap<Integer, String> estados = new HashMap<Integer, String>();
//
//	public ConvBaseDados() {
//		File bancoAccess = new File("banco/access/equiv.mdb");
//		
//		boolean aux = false;
//		
//		if( !bancoAccess.exists() ){
//			return;
//		}
//		
//		access = new XAccess( "banco/access/equiv.mdb", null, null);
//		hsqldb = new XHSQLDB( "banco/hsqldb/equivium" , "sa", ""  );
//		
//		XBanco.setShowSQL(true);
//
//		XBanco.setBanco(hsqldb);
//
//		
//		
//		try {
//			limpar();
//			
////			convEstado();
////			convEscolaridade();
//			convParticipantes();
//			convAnotacoes();
//			convPesquisadores();
//			convReforcos();
//			convReforcos_Audios();
//			convSub_Fases();
//			convFases();
//			convFases_Sub_Fases();
//			convSessoes();
//			convSessao_Fases();
//			convEstimulos();
//			convEstimulos_Audios();
//			convEstimulos_Figuras();
//			convEstimulos_Sub_Fase();
//		
//			aux = true;
//			
//		} catch (SQLException e) {
//			XTrataException.printStackTrace(e);
//		}finally{
//			try {
//				access.getConexao().close();
//				hsqldb.getConexao().close();
//				
//			} catch (SQLException e) {
//				XTrataException.printStackTrace(e);
//			}
//		}
//		
//		if(aux){
//				System.out.println( String.valueOf(bancoAccess.renameTo( new File( "banco/access/equiv_bkp.mdb" ) ) ) );
//		}
//		
//	}
//	
//	private void convAnotacoes() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		slAc.setTabela("Anotacoes");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Anotacoes");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Anotacoes");
//			
//			inHs.addCampo("ANO_CODIGO"      , rsAc.getInt("ANO_CODIGO"));
//			inHs.addCampo("ANO_PARTICIPANTE", rsAc.getInt("ANO_PARTICIPANTE"));
//			inHs.addCampo("ANO_NOME"        , rsAc.getString("ANO_NOME"));
//			inHs.addCampo("ANO_DESCRICAO"   , rsAc.getString("ANO_DESCRICAO"));
//			inHs.addCampo("ANO_DATA"        , rsAc.getDate("ANO_DATA"));
//			
//			inHs.executa();
//		}
//	}
//
////	private void convEscolaridade() throws SQLException{
////		XBanco.setBanco(access);
////
////		XSelect slAc = new XSelect();
////		slAc.addCampo("*");
////		slAc.setTabela("Escolaridade");
////		
////		ResultSet rsAc = slAc.executa();
////
////		XBanco.setBanco(hsqldb);
////
////		XDelete delete = new XDelete();
////		delete.setTabela("Escolaridade");			
////		delete.executa();
////		
////		while( rsAc.next() ){
////			XInsert inHs = new XInsert();
////			
////			inHs.setTabela("Escolaridade");
////			
////			inHs.addCampo("ESC_CODIGO"      , rsAc.getInt("ESC_CODIGO"));
////			inHs.addCampo("ESC_DESCRICAO"   , rsAc.getString("ESC_DESCRICAO"));
////			
////			inHs.executa();
////		}
////	}
//
////	private void convEstado() throws SQLException{
////		XBanco.setBanco(access);
////		ResultSet rsAc = new XResultSet(XBanco.getStatement().executeQuery("SELECT max(est_codigo) as codigo, EST_SIGLA, est_descricao from estado group by EST_SIGLA, est_descricao"));
////
////		XBanco.setBanco(hsqldb);
////
////		XDelete delete = new XDelete();
////		delete.setTabela("Estado");			
////		delete.executa();
////		
////		while( rsAc.next() ){
////			XInsert inHs = new XInsert();
////			
////			inHs.setTabela("Estado");
////			
////			inHs.addCampo("EST_SIGLA"      , rsAc.getString("EST_SIGLA"));
////			inHs.addCampo("EST_DESCRICAO"  , rsAc.getString("EST_DESCRICAO"));
////			
////			estados.put( rsAc.getInt("codigo"), rsAc.getString("EST_SIGLA") );
////			
////			inHs.executa();
////		}
////	}
//	
//	private void convEstimulos() throws SQLException{
//		XBanco.setBanco(access);
//		String p ;
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Estimulos");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Estimulos");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Estimulos");
//			
//			inHs.addCampo("EST_CODIGO"      , rsAc.getInt("EST_CODIGO"));
//			inHs.addCampo("EST_SIGLA"       , rsAc.getString("EST_SIGLA"));
//			inHs.addCampo("EST_NOME"        , rsAc.getString("EST_NOME"));
//			p = XFileUtil.getNomeArquivo(rsAc.getString("Est_Audio_Path"));
//			inHs.addCampo("EST_AUDIO_PATH"  , p);
//			p = XFileUtil.getNomeArquivo(rsAc.getString("EST_FIGURA_PATH"));
//			inHs.addCampo("EST_FIGURA_PATH" , p);
//			inHs.addCampo("EST_PALAVRA" , rsAc.getString("EST_PALAVRA"));
//			inHs.addCampo("EST_INTERVALO"   , rsAc.getDouble("EST_INTERVALO"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convEstimulos_Audios() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Estimulos_Audios");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Estimulos_Audios");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Estimulos_Audios");
//			
//			inHs.addCampo("AUD_ESTIMULO"      , rsAc.getInt("AUD_ESTIMULO"));
//			inHs.addCampo("AUD_ARQUIVO"       , rsAc.getBytes("AUD_ARQUIVO"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convEstimulos_Figuras() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Estimulos_Figuras");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Estimulos_Figuras");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Estimulos_Figuras");
//			
//			inHs.addCampo("FIG_ESTIMULO"      , rsAc.getInt("FIG_ESTIMULO"));
//			inHs.addCampo("FIG_ARQUIVO"       , rsAc.getBytes("FIG_ARQUIVO"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convEstimulos_Sub_Fase() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Estimulos_Sub_Fase");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Estimulos_Sub_Fase");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Estimulos_Sub_Fase");
//			
//			inHs.addCampo("EST_SUB_FASE"                 , rsAc.getInt("EST_SUB_FASE"));
//			inHs.addCampo("EST_SUB_CODIGO"                 , rsAc.getInt("EST_SUB_CODIGO"));
//			inHs.addCampo("EST_SUB_ESTIMULO_MODELO"        , rsAc.getInt("EST_SUB_ESTIMULO_MODELO"));
//			inHs.addCampo("EST_SUB_ESTIMULO_MODELO_TIPO"   , rsAc.getString("EST_SUB_ESTIMULO_MODELO_TIPO"));
//			inHs.addCampo("EST_SUB_ESTIMULO_ESCOLHA"       , rsAc.getInt("EST_SUB_ESTIMULO_ESCOLHA") == 0 ? null : rsAc.getInt("EST_SUB_ESTIMULO_ESCOLHA"));
//			inHs.addCampo("EST_SUB_ESTIMULO_ESCOLHA_TIPO"  , rsAc.getInt("EST_SUB_ESTIMULO_ESCOLHA") == 0 ? null :  rsAc.getString("EST_SUB_ESTIMULO_ESCOLHA_TIPO"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convFases() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Fases");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Fases");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Fases");
//			
//			inHs.addCampo("FAS_CODIGO"  , rsAc.getInt("FAS_CODIGO"));
//			inHs.addCampo("FAS_NOME"    , rsAc.getString("FAS_NOME"));
//			inHs.addCampo("FAS_SIGLA"   , rsAc.getString("FAS_SIGLA"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convFases_Sub_Fases() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Fases_Sub_Fases");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Fases_Sub_Fases");			
//		delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Fases_Sub_Fases");
//			
//			inHs.addCampo("SUB_FAS_CODIGO"   , rsAc.getInt("SUB_FAS_CODIGO"));
//			inHs.addCampo("SUB_FAS_SUB_FASE" , rsAc.getInt("SUB_FAS_SUB_FASE"));
//			inHs.addCampo("SUB_FAS_FASE"     , rsAc.getInt("SUB_FAS_FASE"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convParticipantes() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Participantes");
//		
//		ResultSet rsAc = slAc.executa();
//
//		int aux = new XSelect().getMax("Estado", "Est_Codigo");
//		
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Participantes");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Participantes");
//			
//			String estado = estados.get(rsAc.getInt("Par_Estado")) == null ? estados.get(aux) : estados.get(rsAc.getInt("Par_Estado"));
//			
//			if( estado == null ){
//				estado = "SP";
//			}
//			
//			inHs.addCampo("Par_Codigo"          ,rsAc.getInt("Par_Codigo"));
//			inHs.addCampo("Par_Escolaridade"    ,rsAc.getInt("Par_Escolaridade"));
//			inHs.addCampo("Par_Pai_Escolaridade",rsAc.getInt("Par_Pai_Escolaridade"));
//			inHs.addCampo("Par_Mae_Escolaridade",rsAc.getInt("Par_Mae_Escolaridade"));
//			inHs.addCampo("Par_Estado"          ,estado);
//			inHs.addCampo("Par_Nome"            ,rsAc.getString("Par_Nome"));
//			inHs.addCampo("Par_Projeto"         ,rsAc.getString("Par_Projeto"));
//			inHs.addCampo("Par_Iniciais"        ,rsAc.getString("Par_Iniciais"));
//			inHs.addCampo("Par_Sexo"            ,rsAc.getString("Par_Sexo"));
//			inHs.addCampo("Par_Nascimento"      ,rsAc.getDate("Par_Nascimento"));
//			inHs.addCampo("Par_Mae_Nome"        ,rsAc.getString("Par_Mae_Nome"));
//			inHs.addCampo("Par_Pai_Nome"        ,rsAc.getString("Par_Pai_Nome"));
//			inHs.addCampo("Par_Endereco"        ,rsAc.getString("Par_Endereco"));
//			inHs.addCampo("Par_Complemento"     ,rsAc.getString("Par_Complemento"));
//			inHs.addCampo("Par_Numero"          ,rsAc.getInt("Par_Numero"));
//			inHs.addCampo("Par_Cep"             ,rsAc.getInt("Par_Cep"));
//			inHs.addCampo("Par_Telefone"        ,rsAc.getInt("Par_Telefone"));
//			
//			inHs.executa();
//		}
//	}
//	
//	private void convPesquisadores() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Pesquisadores");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Pesquisadores");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Pesquisadores");
//			
//			inHs.addCampo("PES_CODIGO"  , rsAc.getInt("PES_CODIGO"));
//			inHs.addCampo("PES_NOME"    , rsAc.getString("PES_NOME"));
//			inHs.addCampo("PES_LOGIN"   , rsAc.getString("PES_LOGIN"));
//			inHs.addCampo("PES_SENHA"   , rsAc.getString("PES_SENHA"));
//			
//			inHs.executa();
//		}
//	}
//	
//	private void convReforcos() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Reforcos");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Reforcos");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Reforcos");
//			
//			inHs.addCampo("REF_CODIGO" , rsAc.getInt("REF_CODIGO"));
//			inHs.addCampo("REF_NOME"   , rsAc.getString("REF_NOME"));
//			inHs.addCampo("REF_AUDIO"  , rsAc.getString("REF_AUDIO"));
//			
//			inHs.executa();
//		}
//	}
//	
//	private void convReforcos_Audios() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Reforcos_Audios");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Reforcos_Audios");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Reforcos_Audios");
//			
//			inHs.addCampo("AUD_REFORCO" , rsAc.getInt("AUD_REFORCO"));
//			inHs.addCampo("AUD_ARQUIVO" , rsAc.getBytes("AUD_ARQUIVO"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convSessao_Fases() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Sessao_Fases");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Sessao_Fases");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Sessao_Fases");
//			
//			inHs.addCampo("SES_FAS_CODIGO" , rsAc.getInt("SES_FAS_CODIGO"));
//			inHs.addCampo("SES_FAS_SESSAO" , rsAc.getInt("SES_FAS_SESSAO"));
//			inHs.addCampo("SES_FAS_FASE"   , rsAc.getInt("SES_FAS_FASE"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convSessoes() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Sessoes");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Sessoes");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Sessoes");
//			
//			inHs.addCampo("SES_CODIGO" , rsAc.getInt("SES_CODIGO"));
//			inHs.addCampo("SES_NOME"   , rsAc.getString("SES_NOME"));
//			inHs.addCampo("SES_SIGLA"  , rsAc.getString("SES_SIGLA"));
//			
//			inHs.executa();
//		}
//	}
//
//	private void convSub_Fases() throws SQLException{
//		XBanco.setBanco(access);
//
//		XSelect slAc = new XSelect();
//		slAc.addCampo("*");
//		
//		slAc.setTabela("Sub_Fases");
//		
//		ResultSet rsAc = slAc.executa();
//
//		XBanco.setBanco(hsqldb);
//
//		XDelete delete = new XDelete();
//		delete.setTabela("Sub_Fases");			
//		//delete.executa();
//		
//		while( rsAc.next() ){
//			XInsert inHs = new XInsert();
//			
//			inHs.setTabela("Sub_Fases");
//			
//			inHs.addCampo("SUB_CODIGO"            , rsAc.getInt("SUB_CODIGO"));
//			inHs.addCampo("SUB_REFORCO_ACERTO"    , rsAc.getInt("SUB_REFORCO_ACERTO") == 0 ? null : rsAc.getInt("SUB_REFORCO_ACERTO"));
////			inHs.addCampo("SUB_REFORCO_FIM"       , rsAc.getInt("SUB_REFORCO_FIM")    == 0 ? null : rsAc.getInt("SUB_REFORCO_FIM"));
//			inHs.addCampo("SUB_NOME"              , rsAc.getString("SUB_NOME"));
//			inHs.addCampo("SUB_MINIMO_TENTATIVAS" , rsAc.getInt("SUB_MINIMO_TENTATIVAS"));
//			inHs.addCampo("SUB_ESTIMULOS_VEZ"     , rsAc.getInt("SUB_ESTIMULOS_VEZ"));
//			inHs.addCampo("SUB_INTERVALO_MOD_ESC" , rsAc.getDouble("SUB_INTERVALO_MOD_ESC"));
//			inHs.addCampo("SUB_PORCENTAGEM"       , rsAc.getDouble("SUB_PORCENTAGEM"));
//			inHs.addCampo("SUB_GRADE"       , 0);
//			
//			inHs.executa();
//		}
//	}
//
//	public void limpar() throws SQLException{
//		new LimpaBanco().limpar();
//	}
	
}
