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

public class Fase implements XBeansInterface{

	private int					codigo = 0;
	private String				nome	 = null;
	private String				sigla	 = null;
	private ArrayList<SubFase> 	subFases  = null;
	
	public Fase(){
		reset();
	}

	public void reset(){
		codigo		= 0;
		nome		= "";
		sigla		= "";
		subFases	= new ArrayList<SubFase>();
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public ArrayList<SubFase> getSubFases() {
		return subFases;
	}
	public void setSubFases(ArrayList<SubFase> fases) {
		this.subFases = fases;
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

	public boolean insert(){
		XInsert insert = new XInsert();

		insert.setTabela("Fases");

		insert.addCampo("Fas_Nome",nome);
		insert.addCampo("Fas_Sigla",sigla);


		try {
			insert.executa();
			
			codigo = new XSelect().getMax("Fases", "Fas_Codigo");

			insertSubFases();

			return true;
			
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}

	private void insertSubFases() throws SQLException{

		XDelete delete = new XDelete();

		delete.setTabela("Fases_Sub_Fases");

		delete.setWhere("Sub_Fas_Fase = ?", codigo);

		delete.executa();

		XInsert insert = new XInsert();

		insert.setTabela("Fases_Sub_Fases");

		for ( SubFase sub : subFases ){
			insert = new XInsert();

			insert.setTabela("Fases_Sub_Fases");

			insert.addCampo("Sub_Fas_Fase"		,codigo);
			insert.addCampo("Sub_Fas_Sub_Fase"	,sub.getCodigo());

			insert.executa();
		}
	}

	public boolean update(){
		XUpdate update = new XUpdate();

		update.setTabela("Fases");

		update.addCampo("Fas_Nome"		,nome);
		update.addCampo("Fas_Sigla"	, sigla);
		update.setWhere("Fas_Codigo = ?", codigo);

		try {
			update.executa();

			insertSubFases();

			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro durante update no banco de dados", "Fases", JOptionPane.ERROR_MESSAGE);
			XTrataException.printStackTrace(e);
		}
		
		return false;
		
	}

	public boolean delete(){
		XDelete delete = new XDelete();

		delete.setTabela("Fases");

		delete.setWhere("Fas_Codigo = ?", codigo);

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

		select.setTabela("Fases");

		select.addCampo("Fas_Nome");
		select.addCampo("Fas_Sigla");

		select.setWhere("Fas_Codigo = ?", codigo);

		try {

			ResultSet rs = select.executa();

			if( !rs.next() ){
				
				reset();
				return false;
			}

			nome = rs.getString("Fas_Nome");
			sigla = rs.getString("Fas_Sigla");

			select = new XSelect();

			select.setTabela("Fases_sub_fases");

			select.addCampo("*");
			select.setWhere("Sub_Fas_Fase = ?", codigo);

			rs = select.executa();

			subFases = new ArrayList<SubFase>();

			while( rs.next() ){
				SubFase sub = new SubFase();

				sub.setCodigo(rs.getInt("Sub_Fas_Sub_Fase"));
				sub.select();

				subFases.add(sub);
			}

			return true;
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}
		
		return false;
	}
	
	public int getGrade(){
		return subFases.size() > 0 ? subFases.get(0).getGrade() : 4;
	}

}
