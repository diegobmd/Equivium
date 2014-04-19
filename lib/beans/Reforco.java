package lib.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import configuracao.Configuracao;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XFileUtil;
import util.XTrataException;
import beans.XBeansInterface;


public class Reforco implements XBeansInterface{

	private int    codigo 		= 0;
	private String nome   		= null;
	private String audio  		= null;
	
	public Reforco(){
		reset();
	}

	public void reset(){
		codigo		= 0;
		nome		= "";
		audio		= "";
	}
	
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean insert(){
		XInsert insert = new XInsert();

		insert.setTabela("Reforcos");

		insert.addCampo("Ref_Nome"	, nome			   );
		insert.addCampo("Ref_Audio"	, XFileUtil.getNomeArquivo(audio));

		try {
			if(insert.executa() > 0){
				XInsert insert2 = new XInsert();

				insert2.setTabela("Reforcos_Audios");
				insert2.addCampo("Aud_Reforco", new XSelect().getMax("Reforcos", "Ref_Codigo"));
				insert2.addCampo("Aud_Arquivo" , new File(audio));

				insert2.executa();
				

				XFileUtil.copiarArquivo(getAudio(), Configuracao.dirAudioReforco + XFileUtil.getNomeArquivo(audio) );
				
				return true;
			}

		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}  catch (FileNotFoundException e) {
			XTrataException.printStackTrace(e);
		} catch (IOException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

	public boolean update(){
		XUpdate update = new XUpdate();

		update.setTabela("Reforcos");

		update.addCampo("Ref_Nome"	, nome				 );
		update.addCampo("Ref_Audio"	, XFileUtil.getNomeArquivo(audio) );

		update.setWhere("Ref_Codigo = ?", codigo);

		try {
			if(update.executa() > 0){

				XUpdate update2 = new XUpdate();

				update2.setTabela("Reforcos_Audios");
				update2.addCampo("Aud_Arquivo" , new File(getAudio()));

				update2.setWhere("Aud_Reforco = ?", codigo);

				update2.executa();
				
				XFileUtil.copiarArquivo(getAudio(), Configuracao.dirAudioReforco + XFileUtil.getNomeArquivo(audio) );
				
				return true;
			}

		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}  catch (FileNotFoundException e) {
			XTrataException.printStackTrace(e);
		} catch (IOException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

	public boolean delete(){
		XDelete delete = new XDelete();
		XDelete delete2 = new XDelete();

		delete2.setTabela("Reforcos_Audios");
		delete2.setWhere("Aud_Reforco = ?", codigo);
		
		delete.setTabela("Reforcos");
		delete.setWhere("Ref_Codigo = ?", codigo);

		try {
			if( delete2.executa() > 0 ){
				delete.executa();
				
				return true;
			}
		} catch (SQLException e) {
			if(e.getMessage().contains("Integrity")){
				JOptionPane.showMessageDialog(null, "Registro não pode ser excluído por estar sendo referenciado.");
				return true;
			}
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

	public boolean select(){
		XSelect select = new XSelect();

		select.setTabela("Reforcos");
		select.addCampo("*");
		select.setWhere("Ref_Codigo = ?", codigo);

		try {

			ResultSet rs = select.executa();

			if( !rs.next() ){
				reset();
				
				return false;
			}

			//principal
			nome 		= rs.getString("Ref_Nome");
			audio 		= Configuracao.dirAudioReforco + rs.getString("Ref_Audio");

			return true;
			
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

}
