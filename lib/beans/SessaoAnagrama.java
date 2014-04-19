package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XTrataException;
import beans.XBeansInterface;

public class SessaoAnagrama implements XBeansInterface{

	private int						codigo = 0;
	private String					nome   = null;
	private String 					sigla  = null;
	private ArrayList<Anagrama> 	anargamas  = null;
	private Reforco					aoFim  = null; 

	public SessaoAnagrama(){
		reset();
	}

	public void reset(){
		codigo	= 0;
		nome	= "";
		sigla   = "";
		anargamas   = new ArrayList<Anagrama>();
		aoFim   = new Reforco();
	}
	
	public void addAnagrama(Anagrama f){
		anargamas.add(f);
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public ArrayList<Anagrama> getAnagramas() {
		return anargamas;
	}
	public void setAnagramas(ArrayList<Anagrama> fases) {
		this.anargamas = fases;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Reforco getAoFim() {
		return aoFim;
	}
	public void setAoFim(Reforco aoFim) {
		this.aoFim = aoFim;
	}
	
	public boolean insert(){
		XInsert insert = new XInsert();

		insert.setTabela("SESSOES_ANAGRAMA");

		insert.addCampo("SAN_NOME", getNome());
		insert.addCampo("SAN_SIGLA", getSigla());
		insert.addCampo("SAN_REFORCO_FIM", getAoFim().getCodigo());

		try {
			insert.executa();

			codigo = new XSelect().getMax("SESSOES_ANAGRAMA", "SAN_CODIGO");

			insertFases();

			return true;
			
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

	private void insertFases() throws SQLException{

		XDelete delete = new XDelete();

		delete.setTabela("SESSAO_ANAGRAMA");

		delete.setWhere("SES_ANA_SESSAO = ?", getCodigo());

		delete.executa();

		XInsert insert = new XInsert();

		for ( Anagrama sub : anargamas ){
			insert = new XInsert();

			insert.setTabela("SESSAO_ANAGRAMA");

			insert.addCampo("SES_ANA_ANAGRAMA", sub.getCodigo());
			insert.addCampo("SES_ANA_SESSAO", getCodigo());

			insert.executa();
		}
	}

	public boolean update() {
		XUpdate update = new XUpdate();

		update.setTabela("SESSOES_ANAGRAMA");

		update.addCampo("SAN_NOME", getNome());
		update.addCampo("SAN_SIGLA", getSigla());
		update.addCampo("SAN_REFORCO_FIM", getAoFim().getCodigo());

		update.setWhere("SAN_CODIGO = ?", getCodigo());

		try {
			update.executa();

			insertFases();

			return true;
			
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

	public boolean delete(){
		XDelete delete = new XDelete();

		delete.setTabela("SESSOES_ANAGRAMA");

		delete.setWhere("SAN_CODIGO = ?", getCodigo());

		try {
			delete.executa();
			
			return true;
			
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

		select.setTabela("SESSOES_ANAGRAMA");

		select.addCampo("*");

		select.setWhere("SAN_CODIGO = ?", getCodigo());

		try {

			ResultSet rs = select.executa();

			if( !rs.next() ){
				reset();
				return false;
			}

			setNome(rs.getString("SAN_NOME"));
			setSigla(rs.getString("SAN_SIGLA"));
			setAoFim(new Reforco());
			getAoFim().setCodigo(rs.getInt("SAN_REFORCO_FIM"));

			select = new XSelect();

			select.setTabela("SESSAO_ANAGRAMA");

			select.addCampo("SES_ANA_SESSAO");
			select.addCampo("SES_ANA_ANAGRAMA");

			select.setWhere("SES_ANA_SESSAO = ?", getCodigo());

			rs = select.executa();

			anargamas = new ArrayList<Anagrama>();

			Anagrama sub = new Anagrama();
			while( rs.next() ){
				sub.setCodigo(rs.getInt("SES_ANA_ANAGRAMA"));
				sub.select();
				
				anargamas.add(sub);
			}

			return true;
			
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}
	
}
