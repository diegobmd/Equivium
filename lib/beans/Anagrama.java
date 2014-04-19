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

public class Anagrama implements XBeansInterface {

   private int                 codigo    = 0;
   private String              nome      = null;
   private String              sigla     = null;
   private Reforco             acada     = null;
   private int                 minimo    = 0;
   private double              pacerto   = 0;
   private int                 qtdExtra  = 0;
   private ArrayList<Estimulo> estimulos = new ArrayList<Estimulo>();
   private ArrayList<Estimulo> estExtras = new ArrayList<Estimulo>();

   public Anagrama() {
      reset();
   }

   public void reset() {
      codigo = 0;
      nome = "";
      sigla = "";
      acada = new Reforco();
      minimo = 0;
      pacerto = 0;
      qtdExtra = 0;
      estimulos = new ArrayList<Estimulo>();
      estExtras = new ArrayList<Estimulo>();
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

   public String getSigla() {
      return sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }

   public Reforco getReforco() {
      return acada;
   }

   public void setReforco(Reforco acada) {
      this.acada = acada;
   }

   public int getMinimo() {
      return minimo;
   }

   public void setMinimo(int minimo) {
      this.minimo = minimo;
   }

   public double getPorcAcerto() {
      return pacerto;
   }

   public void setPorcAcerto(double acerto) {
      this.pacerto = acerto;
   }

   public int getQtdExtra() {
      return qtdExtra;
   }

   public void setQtdExtra(int qtdExtra) {
      this.qtdExtra = qtdExtra;
   }

   public ArrayList<Estimulo> getEstimulos() {
      return estimulos;
   }

   public void setEstimulos(ArrayList<Estimulo> estimulos) {
      this.estimulos = estimulos;
   }

   public ArrayList<Estimulo> getEstExtras() {
      return estExtras;
   }

   public void setEstExtras(ArrayList<Estimulo> estExtras) {
      this.estExtras = estExtras;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("ANAGRAMAS");

      delete.setWhere("ANA_CODIGO = ?", codigo);

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

   public boolean insert() {
      XInsert insert = new XInsert();

      insert.setTabela("ANAGRAMAS");

      insert.addCampo("ANA_SIGLA", sigla);
      insert.addCampo("ANA_NOME", nome);
      insert.addCampo("ANA_REFORCO_ACERTO", acada.getCodigo());
      insert.addCampo("ANA_MINIMO_TENTATIVAS", minimo);
      insert.addCampo("ANA_PORCENTAGEM", pacerto);
      insert.addCampo("ANA_EXTRA_VEZ", qtdExtra);

      try {
         insert.executa();

         setCodigo(new XSelect().getMax("ANAGRAMAS", "ANA_CODIGO"));

         insertEstimulos();

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean select() {
      XSelect select = new XSelect();
      Reforco ref;
      Estimulo es;
      ResultSet rs;

      select.setTabela("ANAGRAMAS");
      select.addCampo("*");
      select.setWhere("ANA_CODIGO = ?", codigo);

      try {
         rs = select.executa();

         if (!rs.next()) {
            reset();
            return false;
         }

         sigla = rs.getString("ANA_SIGLA");
         nome = rs.getString("ANA_NOME");
         minimo = rs.getInt("ANA_MINIMO_TENTATIVAS");
         pacerto = rs.getDouble("ANA_PORCENTAGEM");
         qtdExtra = rs.getInt("ANA_EXTRA_VEZ");

         ref = new Reforco();
         ref.setCodigo(rs.getInt("ANA_REFORCO_ACERTO"));
         ref.select();
         acada = ref;

         select = new XSelect();
         select.setTabela("ANAGRAMA_ESTIMULOS");
         select.addCampo("*");
         select.setWhere("ANA_EST_CODIGO = ?", codigo);

         rs = select.executa();

         estimulos = new ArrayList<Estimulo>();

         while (rs.next()) {
            es = new Estimulo();

            es.setCodigo(rs.getInt("ANA_EST_ESTIMULO"));
            es.setTipo(rs.getString("ANA_EST_TIPO").charAt(0));

            estimulos.add(es);
         }

         select = new XSelect();
         select.setTabela("ANAGRAMA_ESTI_EXTRA");
         select.addCampo("*");
         select.setWhere("ANA_EST_EXT_CODIGO = ?", codigo);

         rs = select.executa();

         estExtras = new ArrayList<Estimulo>();

         while (rs.next()) {
            es = new Estimulo();

            es.setCodigo(rs.getInt("ANA_EST_EXT_ESTIMULO"));

            es.select();

            estExtras.add(es);
         }

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }
      return false;
   }

   public boolean update() {
      XUpdate update = new XUpdate();

      update.setTabela("ANAGRAMAS");

      update.addCampo("ANA_SIGLA", sigla);
      update.addCampo("ANA_NOME", nome);
      update.addCampo("ANA_REFORCO_ACERTO", acada.getCodigo());
      update.addCampo("ANA_MINIMO_TENTATIVAS", minimo);
      update.addCampo("ANA_PORCENTAGEM", pacerto);
      update.addCampo("ANA_EXTRA_VEZ", qtdExtra);

      update.setWhere("ANA_CODIGO = ?", codigo);

      try {
         update.executa();

         insertEstimulos();

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   private void insertEstimulos() throws SQLException {

      XDelete delete;
      XInsert insert;

      delete = new XDelete();

      delete.setTabela("ANAGRAMA_ESTIMULOS");
      delete.setWhere("ANA_EST_CODIGO = ?", codigo);
      delete.executa();

      delete = new XDelete();

      delete.setTabela("ANAGRAMA_ESTI_EXTRA");
      delete.setWhere("ANA_EST_EXT_CODIGO = ?", codigo);
      delete.executa();

      for (Estimulo e : estimulos) {
         insert = new XInsert();

         insert.setTabela("ANAGRAMA_ESTIMULOS");

         insert.addCampo("ANA_EST_CODIGO", codigo);
         insert.addCampo("ANA_EST_ESTIMULO", e.getCodigo());
         insert.addCampo("ANA_EST_TIPO", e.getTipo());

         insert.executa();
      }

      for (Estimulo e : estExtras) {
         insert = new XInsert();

         insert.setTabela("ANAGRAMA_ESTI_EXTRA");

         insert.addCampo("ANA_EST_EXT_CODIGO", codigo);
         insert.addCampo("ANA_EST_EXT_ESTIMULO", e.getCodigo());

         insert.executa();
      }

   }
}
