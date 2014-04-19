package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XTrataException;
import beans.XBeansInterface;

public class RealizaAnagrama implements XBeansInterface {

   private int          codigo       = 0;
   private Participante participante = null;
   private Pesquisador  pesquisador  = null;

   private String       nome         = null;
   private String       sigla        = null;
   private String       descricao    = null;

   public RealizaAnagrama() {
   }

   public RealizaAnagrama(Participante participante) {
      setParticipante(participante);
      setPesquisador(Usuario.getPesquisadorAtivo());
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

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public Participante getParticipante() {
      return participante;
   }

   public void setParticipante(Participante participante) {
      this.participante = participante;
   }

   public Pesquisador getPesquisador() {
      return pesquisador;
   }

   public void setPesquisador(Pesquisador pesquisador) {
      this.pesquisador = pesquisador;
   }

   public boolean insert() {
      try {
         setCodigo(new XSelect().getSequencia("REALIZA_ANAGRAMA", "RAN_CODIGO"));

         XInsert insert = new XInsert();

         insert.setTabela("REALIZA_ANAGRAMA");

         insert.addCampo("RAN_CODIGO", codigo);
         insert.addCampo("RAN_PARTICIPANTE", participante.getCodigo());
         insert.addCampo("RAN_DATA", new Date());
         insert.addCampo("RAN_PESQUISADOR", pesquisador.getCodigo());

         return insert.executa() > 0;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }


   public boolean select() {
      XSelect select = new XSelect();

      select.setTabela("REALIZA_ANAGRAMA");

      select.addCampo("*");

      select.setWhere("RAN_Codigo = ?", codigo);

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {
            reset();

            return false;
         }

         nome = rs.getString("RAN_NOME");
         sigla = rs.getString("RAN_SIGLA");
         descricao = rs.getString("RAN_ANOTACAO");

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean update() {
      try {
         XUpdate update = new XUpdate();

         update.setTabela("REALIZA_ANAGRAMA");

         update.setWhere("RAN_CODIGO = ?", codigo);

         update.addCampo("RAN_SIGLA", sigla);
         update.addCampo("RAN_NOME", nome);
         update.addCampo("RAN_ANOTACAO", descricao);

         return update.executa() > 0;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("REALIZA_ANAGRAMA");

      delete.setWhere("RAN_CODIGO = ?", codigo);

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
   
   public void reset() {
      codigo = 0;
      nome = "";
      sigla = "";
      descricao = "";
   }

}
