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

public class Anotacao implements XBeansInterface {

   private int          codigo       = 0;
   private Participante participante = null;
   private String       nome         = null;
   private String       descricao    = null;
   private Date         data         = null;

   public Anotacao() {
      reset();
   }

   public void reset() {
      codigo = 0;
      participante = new Participante();
      nome = "";
      descricao = "";
      data = new Date();
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public Participante getParticipante() {
      return participante;
   }

   public void setParticipante(Participante participante) {
      this.participante = participante;
   }

   public int getCodigoParticipante() {
      return participante.getCodigo();
   }

   public void setCodigoParticipante(int p) {
      this.participante.setCodigo(p);
   }

   public Date getData() {
      return data;
   }

   public void setData(Date data) {
      this.data = data;
   }

   public boolean insert() {
      XInsert insert = new XInsert();

      insert.setTabela("Anotacoes");

      insert.addCampo("Ano_Participante", participante.getCodigo());
      insert.addCampo("Ano_Data", data);
      insert.addCampo("Ano_Nome", nome);
      insert.addCampo("Ano_Descricao", descricao);

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

      update.setTabela("Anotacoes");

      update.addCampo("Ano_Participante", participante.getCodigo());
      update.addCampo("Ano_Data", data);
      update.addCampo("Ano_Nome", nome);
      update.addCampo("Ano_Descricao", descricao);

      update.setWhere("Ano_Codigo = ?", codigo);

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

      delete.setTabela("Anotacoes");

      delete.setWhere("Ano_Codigo = ?", codigo);

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

      select.setTabela("Anotacoes");

      select.addCampo("Ano_Participante");
      select.addCampo("Ano_Data");
      select.addCampo("Ano_Nome");
      select.addCampo("Ano_Descricao");

      select.setWhere("Ano_Codigo = ?", codigo);

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {

            reset();

            return false;
         }

         participante.setCodigo(rs.getInt("Ano_Participante"));
         data = rs.getDate("Ano_Data");
         nome = rs.getString("Ano_Nome");
         descricao = rs.getString("Ano_Descricao");

         return true;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;

   }

}
