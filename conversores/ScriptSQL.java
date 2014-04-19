package conversores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import sql.bancos.XBanco;
import util.XFileUtil;

public class ScriptSQL {

//	public static void main(String[] args) {
//		
//		XBanco.setShowSQL(true);
//
//		XBanco.setBanco(new XHSQLDB( "banco/hsqldb/equivium" , "sa", ""  ));
//
//		try{
//			new ScriptSQL().rodarScripts();
//			
//			new LimpaBanco().limpar();
//			
//			XBanco.getConexao().close();
//		}
//		catch(SQLException e){
//			XTrataException.printStackTrace(e);
//		} catch (IOException e) {
//			XTrataException.printStackTrace(e);
//		}
//		
//	}
	
	public void rodarScripts() throws IOException, SQLException{
		File file = new File("banco/scripts");
		
		if( file.exists()&& file.isDirectory() ){
			for( File f : file.listFiles() ){
				if( !f.isDirectory() ){
					rodaArquivo(f);
				}
			}
		}
	}
	
	private void rodaArquivo(File file) throws IOException, SQLException{
		if( file.exists() ){
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String linha;
			
			while ( (  linha = reader.readLine() ) != null ){
				builder.append(linha);
			}
			
			reader.close();
			
			Statement st = XBanco.getStatement();
			
			System.out.println(builder.toString());
			
			st.executeUpdate(builder.toString());
			
			String nome = XFileUtil.getNomeArquivo(file.getPath());
			
			XFileUtil.copiarArquivo(file, new File("banco/scripts/exec/"+nome));
			
			file.delete();
		}
	}
	
}
