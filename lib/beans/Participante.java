package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XTrataException;
import beans.XBeansInterface;

public class Participante implements XBeansInterface {

   // principal
   private int    codigo       = 0;
   private String nome         = null;
   private String projeto      = null;
   private String iniciais     = null;
   private char   sexo         = ' ';
   private Date   nascimento   = null;
   private int    escolaridade = 0;
   // contato
   private String mae          = null;
   private int    escMae       = 0;
   private String pai          = null;
   private int    escPai       = 0;
   private String endereco     = null;
   private String complemento  = null;
   private int    numero       = 0;
   private String estado       = null;
   private int    cep          = 0;
   private long   telefone     = 0;

   public Participante() {
      reset();
   }

   public void reset() {
      // principal
      codigo = 0;
      nome = "";
      projeto = "";
      iniciais = "";
      sexo = ' ';
      nascimento = new Date();
      escolaridade = 0;
      mae = "";
      escMae = 0;
      pai = "";
      escPai = 0;
      endereco = "";
      complemento = "";
      numero = 0;
      estado = "";
      cep = 0;
      telefone = 0;
   }

   public int getCep() {
      return cep;
   }

   public void setCep(int cep) {
      this.cep = cep;
   }

   public String getComplemento() {
      return complemento;
   }

   public void setComplemento(String complemento) {
      this.complemento = complemento;
   }

   public String getEndereco() {
      return endereco;
   }

   public void setEndereco(String endereco) {
      this.endereco = endereco;
   }

   public int getEscMae() {
      return escMae;
   }

   public void setEscMae(int escMae) {
      this.escMae = escMae;
   }

   public int getEscolaridade() {
      return escolaridade;
   }

   public void setEscolaridade(int escolaridade) {
      this.escolaridade = escolaridade;
   }

   public int getEscPai() {
      return escPai;
   }

   public void setEscPai(int escPai) {
      this.escPai = escPai;
   }

   public String getEstado() {
      return estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public String getIniciais() {
      return iniciais;
   }

   public void setIniciais(String iniciais) {
      this.iniciais = iniciais;
   }

   public String getMae() {
      return mae;
   }

   public void setMae(String mae) {
      this.mae = mae;
   }

   public Date getNascimento() {
      return nascimento;
   }

   public void setNascimento(Date nascimento) {
      this.nascimento = nascimento;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public int getNumero() {
      return numero;
   }

   public void setNumero(int numero) {
      this.numero = numero;
   }

   public String getPai() {
      return pai;
   }

   public void setPai(String pai) {
      this.pai = pai;
   }

   public String getProjeto() {
      return projeto;
   }

   public void setProjeto(String projeto) {
      this.projeto = projeto;
   }

   public char getSexo() {
      return sexo;
   }

   public String getSexoDescricao() {
      switch (sexo) {
         case 'M':
            return "Masculino";
         case 'F':
            return "Feminino";
         default:
            return "";
      }
   }

   public void setSexo(char sexo) {
      this.sexo = sexo;
   }

   public long getTelefone() {
      return telefone;
   }

   public void setTelefone(long telefone) {
      this.telefone = telefone;
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public int getIdade() {

      if (nascimento != null) {
         Calendar data1 = GregorianCalendar.getInstance();
         Calendar data2 = GregorianCalendar.getInstance();

         data1.setTime(nascimento);
         data2.setTime(new Date());

         int dia1 = data1.get(Calendar.DAY_OF_YEAR);
         int ano1 = data1.get(Calendar.YEAR);

         int dia2 = data2.get(Calendar.DAY_OF_MONTH);
         int ano2 = data2.get(Calendar.YEAR);

         return (ano2 - ano1) - (dia1 < dia2 ? 0 : 1);
      }

      return 0;
   }

   public boolean insert() {
      XInsert insert = new XInsert();

      insert.setTabela("Participantes");

      insert.addCampo("Par_Escolaridade", escolaridade);
      insert.addCampo("Par_Pai_Escolaridade", escMae);
      insert.addCampo("Par_Mae_Escolaridade", escPai);
      insert.addCampo("Par_Estado", estado);
      insert.addCampo("Par_Nome", nome);
      insert.addCampo("Par_Projeto", projeto);
      insert.addCampo("Par_Iniciais", iniciais);
      insert.addCampo("Par_Sexo", sexo);
      insert.addCampo("Par_Nascimento", nascimento);
      insert.addCampo("Par_Mae_Nome", mae);
      insert.addCampo("Par_Pai_Nome", pai);
      insert.addCampo("Par_Endereco", endereco);
      insert.addCampo("Par_Complemento", complemento);
      insert.addCampo("Par_Numero", numero);
      insert.addCampo("Par_Cep", cep);
      insert.addCampo("Par_Telefone", telefone);

      try {
         insert.executa();

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }
      return false;
   }

   public boolean update() {
      XUpdate update = new XUpdate();

      update.setTabela("Participantes");

      update.addCampo("Par_Escolaridade", escolaridade);
      update.addCampo("Par_Pai_Escolaridade", escMae);
      update.addCampo("Par_Mae_Escolaridade", escPai);
      update.addCampo("Par_Estado", estado);
      update.addCampo("Par_Nome", nome);
      update.addCampo("Par_Projeto", projeto);
      update.addCampo("Par_Iniciais", iniciais);
      update.addCampo("Par_Sexo", sexo);
      update.addCampo("Par_Nascimento", nascimento);
      update.addCampo("Par_Mae_Nome", mae);
      update.addCampo("Par_Pai_Nome", pai);
      update.addCampo("Par_Endereco", endereco);
      update.addCampo("Par_Complemento", complemento);
      update.addCampo("Par_Numero", numero);
      update.addCampo("Par_Cep", cep);
      update.addCampo("Par_Telefone", telefone);

      update.setWhere("Par_Codigo = ?", codigo);

      try {
         update.executa();

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }
      return false;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("Participantes");

      delete.setWhere("Par_Codigo = ?", codigo);

      try {
         delete.executa();

         return true;
      } catch (SQLException e) {
         if (e.getMessage().contains("Integrity")) {
            JOptionPane.showMessageDialog(null, "Registro não pode ser excluído por estar sendo referenciado.");
            return true;
         }
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean select() {
      XSelect select = new XSelect();

      select.setTabela("Participantes");
      select.addCampo("*");
      select.setWhere("Par_Codigo = ?", codigo);

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {
            reset();
            return false;
         }

         // principal
         nome = rs.getString("Par_Nome");
         projeto = rs.getString("Par_Projeto");
         iniciais = rs.getString("Par_Iniciais");
         sexo = rs.getString("Par_Sexo").charAt(0);
         nascimento = rs.getDate("Par_Nascimento");
         escolaridade = rs.getInt("Par_Escolaridade");
         // contato
         mae = rs.getString("Par_Mae_Nome");
         escMae = rs.getInt("Par_Mae_Escolaridade");
         pai = rs.getString("Par_Pai_Nome");
         escPai = rs.getInt("Par_Pai_Escolaridade");
         endereco = rs.getString("Par_Endereco");
         complemento = rs.getString("Par_Complemento");
         numero = rs.getInt("Par_Numero");
         estado = rs.getString("Par_Estado");
         cep = rs.getInt("Par_Cep");
         telefone = rs.getLong("Par_Telefone");

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }
}
