package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XTrataException;
import beans.XBeansInterface;


public class Pesquisador implements XBeansInterface{

	private int	   codigo = 0;
	private String nome   = null;
	private String login  = null;
	private String senha  = null;

	public Pesquisador(){
		reset();
	}

	public void reset(){
		codigo= 0;
		nome  = "";
		login = "";
		senha = "";
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public boolean insert(){
		XInsert insert = new XInsert();

		insert.setTabela("Pesquisadores");

		insert.addCampo("Pes_Nome", nome);
		insert.addCampo("Pes_Login", login);
		insert.addCampo("Pes_Senha", senha);

		try {
			insert.executa();

			return true;
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}

		return false;
	}

	public boolean update(){
		XUpdate update = new XUpdate();

		update.setTabela("Pesquisadores");

		update.addCampo("Pes_Nome",nome);
		update.addCampo("Pes_Login", login);
		update.addCampo("Pes_Senha",senha);

		update.setWhere("Pes_Codigo = ?", codigo);

		try {
			update.executa();

			return true;
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}

		return false;
	}

	public boolean delete(){
		XDelete delete = new XDelete();

		delete.setTabela("Pesquisadores");

		delete.setWhere("Pes_Codigo = ?",codigo);

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

		select.setTabela("Pesquisadores");

		select.addCampo("*");

		select.setWhere("Pes_Codigo = ?", codigo);

		try {

			ResultSet rs = select.executa();

			if( !rs.next() ){
				reset();
				return false;
			}

			nome  = rs.getString("Pes_Nome");
			login = rs.getString("Pes_Login");
			senha = rs.getString("Pes_Senha");

			return true;
		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}

		return false;
	}
}
