package lib.beans;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import sql.querys.XInsert;
import sql.querys.XSelect;
import util.XTrataException;
import beans.XBeansInterface;

public class ItemRealizaSessao implements XBeansInterface {

   private RealizaSessao              realiza    = null; //
   private SubFase                    subFase    = null; //

   private int                        codigo     = 0;   //
   private int                        agrupador  = 0;   // bloco
   private int                        tentativa  = 0;   //
   private long                       tempo      = 0;
   private int                        ordemExec  = 0;

   private Estimulo                   modelo     = null; //
   private Estimulo                   escolhido  = null; //
   private Estimulo                   escCorreto = null; //
   private HashMap<Integer, Estimulo> escolhas   = null;
   private Reforco                    acada      = null;

   public boolean acertou() {
      return escolhido.getCodigo() == escCorreto.getCodigo();
   }

   public ItemRealizaSessao(RealizaSessao realiza, SubFase subFase) {
      setRealiza(realiza);
      setSubFase(subFase);
   }

   public RealizaSessao getRealiza() {
      return realiza;
   }

   public void setRealiza(RealizaSessao realiza) {
      this.realiza = realiza;
   }

   public SubFase getSubFase() {
      return subFase;
   }

   public void setSubFase(SubFase subFase) {
      this.subFase = subFase;
   }

   public int getCodigo() {
      return codigo;
   }

   public void setInstancia(int instancia) {
      if( subFase != null ){
         subFase.setInstancia(instancia);
      }
   }
   
   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public int getAgrupador() {
      return agrupador;
   }

   public void setAgrupador(int bloco) {
      this.agrupador = bloco;
   }

   public int getTentativa() {
      return tentativa;
   }

   public void setTentativa(int tentativa) {
      this.tentativa = tentativa;
   }

   public long getTempo() {
      return tempo;
   }

   public void setTempo(long tempo) {
      this.tempo = tempo;
   }

   public Estimulo getModelo() {
      return modelo;
   }

   public void setModelo(Estimulo modelo) {
      this.modelo = modelo;
   }

   public Estimulo getEscolhido() {
      return escolhido;
   }

   public void setEscolhido(Estimulo escolhido) {
      this.escolhido = escolhido;
   }

   public Estimulo getEscolha(int pos) {
      try{
         return escolhas.get(pos);
      }catch (ArrayIndexOutOfBoundsException e) {
         return null;
      }
   }
   
   public HashMap<Integer, Estimulo> getEscolhas() {
      return escolhas;
   }   

   public void setEscolhas(HashMap<Integer, Estimulo> escolhas) {
      this.escolhas = escolhas;
   }

   public void addEscolha(int posicao, Estimulo escolha) {
      if (escolhas == null) {
         escolhas = new HashMap<Integer, Estimulo>();
      }

      escolhas.put(posicao, escolha);
   }

   public Estimulo getEscCorreto() {
      return escCorreto;
   }

   public void setEscCorreto(Estimulo escCorreto) {
      this.escCorreto = escCorreto;
   }

   public Reforco getAcada() {
      return acada;
   }

   public void setAcada(Reforco acada) {
      this.acada = acada;
   }

   public int getOrdemExec() {
      return ordemExec;
   }

   public void setOrdemExec(int ordemExec) {
      this.ordemExec = ordemExec;
   }

   public boolean insert() {
      try {

         setCodigo(new XSelect().getSequencia("Itens_Realiza_Sessao", "Ite_Codigo"));

         XInsert insert = new XInsert();

         insert.setTabela("Itens_Realiza_Sessao");

         insert.addCampo("Ite_Sub_Fase", subFase.getCodigo());
         insert.addCampo("Ite_Realiza", realiza.getCodigo());
         insert.addCampo("Ite_Agrupador", agrupador);
         insert.addCampo("Ite_Tentativa", tentativa);
         insert.addCampo("Ite_Tempo", tempo);
         insert.addCampo("ITE_ORDEM_EXEC", ordemExec);
         insert.addCampo("ITE_SUB_INSTANCIA", subFase.getInstancia());
         insert.addCampo("Ite_Modelo", modelo.getCodigo());
         insert.addCampo("Ite_Modelo_Tipo", modelo.getTipo());
         insert.addCampo("Ite_Escolhido", escolhido.getCodigo());
         insert.addCampo("Ite_Escolhido_tipo", escolhido.getTipo());
         insert.addCampo("Ite_Esc_Correto", escCorreto.getCodigo());
         insert.addCampo("Ite_Esc_Correto_Tipo", escCorreto.getTipo());

         if (escolhas != null) {
            for (Iterator<Integer> it = escolhas.keySet().iterator(); it.hasNext();) {
               int posicao = it.next();
               Estimulo escolha = escolhas.get(posicao);

               if (escolha != null) {
                  insert.addCampo("ITE_ESCOLHA_" + posicao, escolha.getCodigo());
                  insert.addCampo("ITE_ESCOLHA_" + posicao + "_TIPO", escolha.getTipo());
               }
            }
         }

         return (insert.executa() > 0);
      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return true;
   }

   public boolean select() {
      return false;
   }

   public boolean update() {
      return false;
   }

   public boolean delete() {
      return false;
   }

   public void reset() {

   }

}
