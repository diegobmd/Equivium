package testes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import sql.Objects.XResultSet;
import sql.bancos.XBanco;

public class TestesMysql {

	public void geraXML() throws IOException, SQLException{

		DatabaseMetaData metadata = XBanco.getConexao().getMetaData();
		
		boolean     primaryKey;
		XResultSet 	rsTables ;
		XResultSet 	rsColums;
		XResultSet 	rsPrimarys;
		Element 	tabela;
		Element 	campo;
		Element 	banco = new Element("DATABASE");
		ArrayList<String> primarys ;
		
		banco.setAttribute( "PRODUCT_NAME", metadata.getDatabaseProductName()    );
		banco.setAttribute( "VERSION"     , metadata.getDatabaseProductVersion() );

		Document documento = new Document(banco);
		
		rsTables = new XResultSet( metadata.getTables(null, null, null, new String[]{"TABLE"} ) );
		
		while( rsTables.next() ){
			tabela = new Element( "TABLE" );
			tabela.setAttribute( "TABLE_NAME", rsTables.getString("TABLE_NAME") );
			
			rsColums   = new XResultSet( metadata.getColumns(null, null, rsTables.getString( "TABLE_NAME" ), null) );
			
			//rsPrimarys = new XResultSet( metadata.getPrimaryKeys(null, null, rsTables.getString("TABLE_NAME") ) );
			
			rsPrimarys = new XResultSet(  metadata.getIndexInfo(null, null, rsTables.getString( "TABLE_NAME" ), true, true) );
			
			primarys = new ArrayList<String>();
			
			while( rsPrimarys.next() ){
				if( rsPrimarys.getString("COLUMN_NAME") != null && rsPrimarys.getString("INDEX_NAME").startsWith("pk_") ){
					primarys.add( rsPrimarys.getString("COLUMN_NAME") );
				}
			}
			
			while( rsColums.next() ){
				
				campo = new Element( "COLUMN" );
				
				campo.setAttribute( "COLUMN_NAME", rsColums.getString("COLUMN_NAME") );
				campo.setAttribute( "DATA_TYPE"  , rsColums.getString("DATA_TYPE")   );
				
				primaryKey = false;
				
				for( String pr : primarys ){
					if( rsColums.getString("COLUMN_NAME").equalsIgnoreCase(pr) ){
						primaryKey = true;
						break;
					}
				}
				campo.setAttribute( "PRIMARY_KEY"  , String.valueOf(primaryKey) );
				
				tabela.addContent( campo );
			}
			
			banco.addContent( tabela );
		}

		XMLOutputter xout  = new XMLOutputter();
		FileWriter arquivo = new FileWriter( new File("c:\\teste.xml") );
		
		xout.output(documento, arquivo);
	}
	
	
	
	
	public static void main(String[] args) {
		ArrayList<String> tes = new ArrayList<String>();
		
		tes.add("1");
		tes.add("2");
		tes.add("3");
		tes.add("4");
		
		System.out.println("");
	}
	
}
