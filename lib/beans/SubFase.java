package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XTrataException;
import beans.XBeansInterface;

public class SubFase implements XBeansInterface {
   // principal
   private int                         codigo        = 0;
   private String                      nome          = null;
   private String                      sigla         = null;
   private Reforco                     acada         = null;
   private int                         minimo        = 0;
   private double                      acerto        = 0;
   private int                         entrModEsc    = 0;
   private HashMap<Estimulo, Estimulo> modeloEscolha = null;
   private int                         estiVez       = 0;
   private int                         grade         = 0;
   private int                         instancia     = 0;
   private int                         agrupador     = 0;
   private boolean                     respObs       = true;

   public SubFase() {
      reset();
   }

   public void reset() {
      codigo = 0;
      nome = "";
      acada = new Reforco();
      minimo = 0;
      acerto = 0;
      entrModEsc = 0;
      modeloEscolha = new HashMap<Estimulo, Estimulo>();
      estiVez = 0;
      grade = 0;
      instancia = 0;
      agrupador = 0;
   }

   public boolean isRespObs() {
      return respObs;
   }

   public void setRespObs(boolean respObs) {
      this.respObs = respObs;
   }

   public String getSigla() {
      return sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }

   public Reforco getAcada() {
      return acada;
   }

   public void setAcada(Reforco acada) {
      this.acada = acada;
   }

   public double getAcerto() {
      return acerto;
   }

   public void setAcerto(double acerto) {
      this.acerto = acerto;
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public int getEntrModEsc() {
      return entrModEsc;
   }

   public void setEntrModEsc(int entrModEsc) {
      this.entrModEsc = entrModEsc;
   }

   public ArrayList<Estimulo> getEscolha() {
      ArrayList<Estimulo> escolha = new ArrayList<Estimulo>();

      for (Iterator<Estimulo> it = modeloEscolha.keySet().iterator(); it.hasNext();) {
         Estimulo modelo = it.next();
         Estimulo es = modeloEscolha.get(modelo);

         if (es != null) {
            escolha.add(es);
         }

      }

      return escolha.size() > 0 ? escolha : null;
   }

   public int getEstiVez() {
      return estiVez;
   }

   public void setEstiVez(int estiVez) {
      this.estiVez = estiVez;
   }

   public int getMinimo() {
      return minimo;
   }

   public void setMinimo(int minimo) {
      this.minimo = minimo;
   }

   public ArrayList<Estimulo> getModelos() {
      ArrayList<Estimulo> modelos = new ArrayList<Estimulo>();

      for (Iterator<Estimulo> it = modeloEscolha.keySet().iterator(); it.hasNext();) {
         Estimulo mo = it.next();
         if (mo != null) {
            modelos.add(mo);
         }
      }

      return modelos.size() > 0 ? modelos : null;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public HashMap<Estimulo, Estimulo> getModeloEscolha() {
      return modeloEscolha;
   }

   public void setModeloEscolha(HashMap<Estimulo, Estimulo> modeloEscolha) {
      this.modeloEscolha = modeloEscolha;
   }

   public int getInstancia() {
      return instancia;
   }

   public void setInstancia(int instancia) {
      this.instancia = instancia;
   }

   public int getAgrupador() {
      return agrupador;
   }

   public void setAgrupador(int agrupador) {
      this.agrupador = agrupador;
   }

   public int getGrade() {
      return grade;
   }

   public void setGrade(int grade) {
      this.grade = grade;
   }

   public boolean insert() {
      XInsert insert = new XInsert();

      insert.setTabela("Sub_Fases");

      insert.addCampo("Sub_Reforco_Acerto", acada.getCodigo() <= 0 ? null : acada.getCodigo());
      insert.addCampo("Sub_Nome", nome);
      insert.addCampo("Sub_sigla", sigla);
      insert.addCampo("Sub_Minimo_Tentativas", minimo);
      insert.addCampo("Sub_Estimulos_Vez", estiVez);
      insert.addCampo("Sub_Porcentagem", acerto);
      insert.addCampo("Sub_Intervalo_Mod_Esc", entrModEsc);
      insert.addCampo("Sub_Grade", grade);
      insert.addCampo("SUB_RESP_OBS", respObs);

      try {

         insert.executa();

         setCodigo(new XSelect().getMax("Sub_Fases", "Sub_Codigo"));

         insertEstimulos();

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   private void insertEstimulos() throws SQLException {

      XDelete delete = new XDelete();

      delete.setTabela("Estimulos_Sub_Fase");

      delete.setWhere("Est_Sub_Fase = ?", codigo);

      delete.executa();

      // percorre o map
      for (Iterator<Estimulo> it = modeloEscolha.keySet().iterator(); it.hasNext();) {
         Estimulo modelo = it.next();
         Estimulo escolha = modeloEscolha.get(modelo);

         XInsert insert = new XInsert();

         insert.setTabela("Estimulos_Sub_Fase");

         insert.addCampo("Est_Sub_Fase", codigo);
         insert.addCampo("Est_Sub_Estimulo_Modelo", modelo.getCodigo());
         insert.addCampo("Est_Sub_Estimulo_Modelo_Tipo", modelo.getTipo());

         if (escolha != null && estiVez > 0) {
            insert.addCampo("Est_Sub_Estimulo_Escolha", escolha.getCodigo());
            insert.addCampo("Est_Sub_Estimulo_Escolha_Tipo", escolha.getTipo());
         }

         insert.executa();
      }
   }

   public boolean update() {
      XUpdate update = new XUpdate();

      update.setTabela("Sub_Fases");

      update.addCampo("Sub_Reforco_Acerto", acada.getCodigo() <= 0 ? null : acada.getCodigo());
      update.addCampo("Sub_Nome", nome);
      update.addCampo("Sub_sigla", sigla);
      update.addCampo("Sub_Minimo_Tentativas", minimo);
      update.addCampo("Sub_Estimulos_Vez", estiVez);
      update.addCampo("Sub_Porcentagem", acerto);
      update.addCampo("Sub_Intervalo_Mod_Esc", entrModEsc);
      update.addCampo("Sub_Grade", grade);
      update.addCampo("SUB_RESP_OBS", respObs);

      update.setWhere("Sub_Codigo = ?", codigo);

      try {
         update.executa();

         insertEstimulos();

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("Sub_Fases");

      delete.setWhere("Sub_Codigo = ?", codigo);

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

      select.setTabela("Sub_Fases");

      select.addCampo("*");

      select.setWhere("Sub_Codigo = ?", codigo);

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {
            reset();
            return false;
         }

         Reforco ref;

         ref = new Reforco();
         ref.setCodigo(rs.getInt("Sub_Reforco_Acerto"));
         ref.select();
         acada = ref;

         nome = rs.getString("Sub_Nome");
         minimo = rs.getInt("Sub_Minimo_Tentativas");
         estiVez = rs.getInt("Sub_Estimulos_Vez");
         entrModEsc = rs.getInt("Sub_Intervalo_Mod_Esc");
         acerto = rs.getDouble("Sub_Porcentagem");
         grade = rs.getInt("Sub_Grade");
         sigla = rs.getString("Sub_Sigla");
         respObs = rs.getBoolean("SUB_RESP_OBS");

         grade = grade == 0 ? 4 : grade;

         select = new XSelect();

         select.setTabela("Estimulos_Sub_Fase");

         select.addCampo("Est_Sub_Estimulo_Escolha");
         select.addCampo("Est_Sub_Estimulo_Modelo");
         select.addCampo("Est_Sub_Estimulo_Escolha_Tipo");
         select.addCampo("Est_Sub_Estimulo_Modelo_Tipo");

         select.setWhere("Est_Sub_Fase = ?", codigo);

         rs = select.executa();

         modeloEscolha = new HashMap<Estimulo, Estimulo>();

         Estimulo es;
         Estimulo mo;

         while (rs.next()) {
            es = new Estimulo();
            mo = new Estimulo();

            mo.setCodigo(rs.getInt("Est_Sub_Estimulo_Modelo"));
            mo.setTipo(rs.getString("Est_Sub_Estimulo_Modelo_Tipo").charAt(0));

            if (rs.getInt("Est_Sub_Estimulo_Escolha") > 0) {
               es.setCodigo(rs.getInt("Est_Sub_Estimulo_Escolha"));
               es.setTipo(rs.getString("Est_Sub_Estimulo_Escolha_Tipo").charAt(0));
            }
            else {
               es = null;
            }

            modeloEscolha.put(mo, es);

         }

         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public int getQuantidade() {
      if (getEstiVez() == 0) {
         return getModelos().size() * getMinimo();
      }

      return getEstiVez() * getMinimo();
   }

}
