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

public class Sessao implements XBeansInterface {

   private int             codigo = 0;
   private String          nome   = null;
   private String          sigla  = null;
   private ArrayList<Fase> fases  = null;
   private Reforco         aoFim  = null;

   public Sessao() {
      reset();
   }

   public void reset() {
      codigo = 0;
      nome = "";
      sigla = "";
      fases = new ArrayList<Fase>();
      aoFim = new Reforco();
   }

   public void addFase(Fase f) {
      fases.add(f);
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public ArrayList<Fase> getFases() {
      return fases;
   }

   public void setFases(ArrayList<Fase> fases) {
      this.fases = fases;
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

   public boolean insert() {
      XInsert insert = new XInsert();

      insert.setTabela("Sessoes");

      insert.addCampo("Ses_Nome", getNome());
      insert.addCampo("Ses_Sigla", getSigla());
      insert.addCampo("Ses_Reforco_Fim", getAoFim().getCodigo());

      try {
         insert.executa();

         codigo = new XSelect().getMax("Sessoes", "Ses_Codigo");

         insertFases();

         return true;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   private void insertFases() throws SQLException {

      XDelete delete = new XDelete();

      delete.setTabela("Sessao_Fases");

      delete.setWhere("Ses_Fas_Sessao = ?", getCodigo());

      delete.executa();

      XInsert insert = new XInsert();

      for (Fase sub : fases) {
         insert = new XInsert();

         insert.setTabela("Sessao_Fases");

         insert.addCampo("Ses_Fas_Fase", sub.getCodigo());
         insert.addCampo("Ses_Fas_Sessao", getCodigo());

         insert.executa();
      }
   }

   public boolean update() {
      XUpdate update = new XUpdate();

      update.setTabela("Sessoes");

      update.addCampo("Ses_Nome", getNome());
      update.addCampo("Ses_sigla", getSigla());
      update.addCampo("Ses_Reforco_Fim", getAoFim().getCodigo());

      update.setWhere("Ses_Codigo = ?", getCodigo());

      try {
         update.executa();

         insertFases();

         return true;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("Sessoes");

      delete.setWhere("Ses_Codigo = ?", getCodigo());

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

      select.setTabela("Sessoes");

      select.addCampo("*");

      select.setWhere("Ses_Codigo = ?", getCodigo());

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {
            reset();
            return false;
         }

         setNome(rs.getString("Ses_Nome"));
         setSigla(rs.getString("Ses_Sigla"));
         setAoFim(new Reforco());
         getAoFim().setCodigo(rs.getInt("Ses_Reforco_Fim"));

         select = new XSelect();

         select.setTabela("SESSAO_FASES");

         select.addCampo("FAS_CODIGO");
         select.addCampo("FAS_NOME");

         select.addInnerJoin("FASES", "FAS_CODIGO = SES_FAS_FASE");

         select.setWhere("SES_FAS_SESSAO = ?", getCodigo());

         rs = select.executa();

         fases = new ArrayList<Fase>();

         while (rs.next()) {
            Fase sub = new Fase();

            sub.setCodigo(rs.getInt("FAS_CODIGO"));
            sub.setNome(rs.getString("FAS_NOME"));

            fases.add(sub);
         }

         return true;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public int getGrade() {
      return fases.size() > 0 ? fases.get(0).getGrade() : 4;
   }

}
