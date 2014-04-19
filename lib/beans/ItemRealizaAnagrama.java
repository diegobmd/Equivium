package lib.beans;

import java.sql.SQLException;

import sql.querys.XInsert;
import sql.querys.XSelect;
import util.XTrataException;
import beans.XBeansInterface;

public class ItemRealizaAnagrama implements XBeansInterface {

   private int             codigo    = 0;
   private RealizaAnagrama realiza   = null;
   private Anagrama        anagrama  = null;
   private int             instancia = 0;
   private int             indice    = 0;
   private String          resultado = null;
   private long            tempo     = 0;
   private int             tentativa = 0;
   private int             ordemExec = 0;

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public int getOrdemExec() {
      return ordemExec;
   }

   public void setOrdemExec(int ordemExec) {
      this.ordemExec = ordemExec;
   }

   public int getTentativa() {
      return tentativa;
   }

   public void setTentativa(int tentativa) {
      this.tentativa = tentativa;
   }

   public Estimulo getEstimulo() {
      return anagrama.getEstimulos().get(indice);
   }

   public RealizaAnagrama getRealiza() {
      return realiza;
   }

   public void setRealiza(RealizaAnagrama realiza) {
      this.realiza = realiza;
   }

   public Anagrama getAnagrama() {
      return anagrama;
   }

   public void setAnagrama(Anagrama anagrama) {
      this.anagrama = anagrama;
   }

   public int getInstancia() {
      return instancia;
   }

   public void setInstancia(int instancia) {
      this.instancia = instancia;
   }

   public int getIndice() {
      return indice;
   }

   public void setIndice(int indice) {
      this.indice = indice;
   }

   public String getResultado() {
      return resultado;
   }

   public void setResultado(String resultado) {
      this.resultado = resultado;
   }

   public long getTempo() {
      return tempo;
   }

   public void setTempo(long tempo) {
      this.tempo = tempo;
   }

   public boolean acertou() {
      if (getEstimulo().getPalavra().indexOf("+") >= 0)
         return getEstimulo().getPalavra().replaceAll("[+]", "").equalsIgnoreCase(resultado);

      return getEstimulo().getPalavra().equalsIgnoreCase(resultado);
   }

   public boolean insert() {
      try {

         setCodigo(new XSelect().getSequencia("ITENS_REALIZA_ANAGRAMA", "IAN_CODIGO"));

         XInsert insert = new XInsert();

         insert.setTabela("ITENS_REALIZA_ANAGRAMA");

         insert.addCampo("IAN_REALIZA", realiza.getCodigo());
         insert.addCampo("IAN_ANAGRAMA", anagrama.getCodigo());
         insert.addCampo("IAN_ANA_INSTANCIA", instancia);
         insert.addCampo("IAN_MODELO", getEstimulo().getCodigo());
         insert.addCampo("IAN_MODELO_TIPO", getEstimulo().getTipo());
         insert.addCampo("IAN_RESULTADO", resultado);
         insert.addCampo("IAN_TENTATIVA", tentativa);
         insert.addCampo("IAN_TEMPO", tempo);
         insert.addCampo("IAN_ORDEM_EXEC", ordemExec);

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
