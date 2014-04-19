package lib.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XTrataException;
import beans.XBeansInterface;

public class RealizaSessao implements XBeansInterface {

   private int          codigo       = 0;
   private Participante participante = null;
   private Pesquisador  pesquisador  = null;

   private String       nome         = null;
   private String       sigla        = null;
   private String       descricao    = null;
   
   private ArrayList<ItemRealizaSessao> itens = new ArrayList<ItemRealizaSessao>();

   public RealizaSessao() {
      reset();
   }

   public RealizaSessao(Participante participante) {
      reset();
      setParticipante(participante);
      setPesquisador(Usuario.getPesquisadorAtivo());
   }
   
   public ArrayList<ItemRealizaSessao> getItens() {
      return itens;
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

   public boolean insert() {
      try {
         setCodigo(new XSelect().getSequencia("Realiza_Sessao", "RSe_Codigo"));

         XInsert insert = new XInsert();

         insert.setTabela("Realiza_Sessao");

         insert.addCampo("RSe_Codigo", codigo);
         insert.addCampo("RSe_Participante", participante.getCodigo());
         insert.addCampo("RSe_Data", new Date());
         insert.addCampo("Rse_Pesquisador", pesquisador.getCodigo());

         return insert.executa() > 0;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean select() {
      XSelect select = new XSelect();

      select.setTabela("Realiza_Sessao");

      select.addCampo("*");

      select.setWhere("RSe_Codigo = ?", codigo);

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {
            reset();

            return false;
         }

         nome = rs.getString("RSE_NOME");
         sigla = rs.getString("RSE_SIGLA");
         descricao = rs.getString("RSE_ANOTACAO");

         setItens();
         
         return true;
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean update() {
      try {
         XUpdate update = new XUpdate();
         XUpdate update2 = new XUpdate();

         update.setTabela("REALIZA_SESSAO");

         update.setWhere("RSE_CODIGO = ?", codigo);

         update.addCampo("RSE_SIGLA", sigla);
         update.addCampo("RSE_NOME", nome);
         update.addCampo("RSE_ANOTACAO", descricao);

         for (ItemRealizaSessao item : getItens()) {
            update2 = new XUpdate();
            
            update2.setTabela("Itens_Realiza_Sessao");

            update2.setWhere("Ite_Codigo = ?", item.getCodigo());

            update2.addCampo("Ite_Escolhido", item.getEscolhido().getCodigo());
            update2.addCampo("Ite_Escolhido_tipo", item.getEscolhido().getTipo());
            
            update2.executa();
         }         
         
         return update.executa() > 0;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("REALIZA_SESSAO");

      delete.setWhere("RSE_CODIGO = ?", codigo);

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
//      itens = new ArrayList<ItemRealizaSessao>();
   }
   
   public void setItemEscolhido(int linha, Estimulo escolhido){
      itens.get(linha).setEscolhido(escolhido);
   }

   private void setItens() throws SQLException{

      XSelect select = new XSelect();
      ItemRealizaSessao item;
      SubFase subFase;
      Estimulo estimulo;
      itens = new ArrayList<ItemRealizaSessao>();

      select.setTabela("Itens_Realiza_Sessao");
      select.addCampo("*");
      select.setWhere("Ite_Realiza = ?", getCodigo());
      
      ResultSet rs = select.executa();
      
      while(rs.next()){
         subFase = new SubFase();
         subFase.setCodigo(rs.getInt("Ite_Sub_Fase"));
         item = new ItemRealizaSessao(this, subFase);
         
         item.setCodigo(rs.getInt("Ite_Codigo"));
         
         item.setAgrupador(rs.getInt("Ite_Agrupador"));
         item.setTentativa(rs.getInt("Ite_Tentativa"));
         item.setTempo(rs.getLong("Ite_Tempo"));
         item.setOrdemExec(rs.getInt("ITE_ORDEM_EXEC"));
         item.setInstancia(rs.getInt("ITE_SUB_INSTANCIA"));
         
         estimulo = new Estimulo();
         estimulo.setCodigo(rs.getInt("Ite_Modelo"));
         estimulo.setTipo(rs.getString("Ite_Modelo_Tipo").charAt(0));
         estimulo.select();
         item.setModelo(estimulo);
         
         estimulo = new Estimulo();
         estimulo.setCodigo(rs.getInt("Ite_Escolhido"));
         estimulo.setTipo(rs.getString("Ite_Escolhido_tipo").charAt(0));
         estimulo.select();
         item.setEscolhido(estimulo);
         
         estimulo = new Estimulo();
         estimulo.setCodigo(rs.getInt("Ite_Esc_Correto"));
         estimulo.setTipo(rs.getString("Ite_Esc_Correto_Tipo").charAt(0));
         estimulo.select();
         item.setEscCorreto(estimulo);
         
         for (int i = 1; i <= 6; i++) {
            if( rs.getString("ITE_ESCOLHA_" + i + "_TIPO") != null ){
               estimulo = new Estimulo();
               estimulo.setCodigo(rs.getInt("ITE_ESCOLHA_" + i));
               estimulo.setTipo(rs.getString("ITE_ESCOLHA_" + i + "_TIPO").charAt(0));
               estimulo.select();
               item.addEscolha(i, estimulo);            
            }
         }
         
         itens.add(item);
      }
   }
}
