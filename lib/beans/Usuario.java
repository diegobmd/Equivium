package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sql.querys.XSelect;
import util.XTrataException;

public class Usuario extends Pesquisador{

	private static Pesquisador pesquisador = new Pesquisador();
	
	public static Pesquisador getPesquisadorAtivo(){
		return pesquisador;
	}
	
	public static boolean validaUser(String login, String senha) {

		XSelect select = new XSelect();

		select.setTabela("Pesquisadores");

		select.addCampo("Pes_Nome");
		select.addCampo("Pes_Codigo");
		select.addCampo("Pes_Login");
		select.addCampo("Pes_Senha");

		ArrayList<Object> par = new ArrayList<Object>();

		par.add(login);
		par.add(senha);

		select.setWhere("Pes_Login = ? AND Pes_Senha = ?", par);

		try {
			ResultSet rs = select.executa();

			pesquisador = new Pesquisador();
			
			if( rs.next() ){
				
				pesquisador.setNome(rs.getString("Pes_Nome"));
				pesquisador.setCodigo(rs.getInt("Pes_Codigo"));
				pesquisador.setLogin(rs.getString("Pes_Login"));
				pesquisador.setSenha(rs.getString("Pes_Senha"));
				
				return true;
			}
			
			return false;

		} catch (SQLException e) {
			XTrataException.printStackTrace(e);
		}

		return false;
	}

	public int getCodigo() {
		return pesquisador.getCodigo();
	}

	public String getLogin() {
		return pesquisador.getLogin();
	}

	public String getNome() {
		return pesquisador.getNome();
	}

	public String getSenha() {
		return pesquisador.getSenha();
	}

	public boolean insert() {
		return false;
	}

	public boolean select() {
		return false;
	}

	public void setCodigo(int codigo) {
	}

	public void setLogin(String login) {
	}

	public void setNome(String nome) {
	}

	public void setSenha(String senha) {
	}

	public boolean update() {
		return false;
	}


}
