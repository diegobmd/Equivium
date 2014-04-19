package lib.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import configuracao.Configuracao;

import sql.querys.XDelete;
import sql.querys.XInsert;
import sql.querys.XSelect;
import sql.querys.XUpdate;
import util.XFileUtil;
import util.XStringUtil;
import util.XTrataException;
import beans.XBeansInterface;

public class Estimulo implements XBeansInterface {

   private int              codigo    = 0;
   private String           sigla     = null;
   private String           nome      = null;
   private String           audio     = null;
   private String           figura    = null;
   private String           palavra   = null;
   private double           intervalo = 0;
   private int              corFundo  = 0;
   private int              corFonte  = 0;
   private char             tipo      = ' ';

   public static final char DESENHO   = 'D';
   public static final char PALAVRA   = 'P';
   public static final char AUDIO     = 'A';
   public static final char COR       = 'C';
   public static final char NENHUM    = 'N';
   
   public static final String DSC_DESENHO   = "Desenho";
   public static final String DSC_PALAVRA   = "Palavra";
   public static final String DSC_AUDIO     = "Áudio";
   public static final String DSC_COR       = "Cor";  
   public static final String DSC_NENHUM    = "Nenhum";  

   public Estimulo() {
      reset();
   }

   public void reset() {
      codigo = 0;
      sigla = "";
      nome = "";
      audio = "";
      figura = "";
      palavra = "";
      intervalo = 0;
      corFundo = 0;
      corFonte = 0;
      tipo = ' ';
   }

   public char getTipo() {
      return tipo;
   }

   public String getTipoDescricao() {
      switch (tipo) {
         case DESENHO:
            return DSC_DESENHO;
         case PALAVRA:
            return DSC_PALAVRA;
         case AUDIO:
            return DSC_AUDIO;
         case COR:
            return DSC_COR;            
         case NENHUM:
            return DSC_NENHUM;
      }
      return "";
   }

   public void setTipo(String tipo) {
      if( tipo.equals(DSC_DESENHO) ){
         setTipo(DESENHO);
         return;
      }
      
      if( tipo.equals(DSC_PALAVRA) ){
         setTipo(PALAVRA);
         return;
      }
      
      if( tipo.equals(DSC_AUDIO) ){
         setTipo(AUDIO);
         return;
      }
      
      if( tipo.equals(DSC_COR) ){
         setTipo(COR);
         return;
      }
      
      if( tipo.equals(DSC_NENHUM) ){
         setTipo(NENHUM);
         return;
      }      
   }   
   
   public void setTipo(char tipo) {
      this.tipo = tipo;
   }

   public String getAudio() {
      return audio;
   }

   public void setAudio(String audio) {
      this.audio = audio;
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

   public String getFigura() {
      return figura;
   }

   public void setFigura(String figura) {
      this.figura = figura;
   }

   public double getIntervalo() {
      return intervalo;
   }

   public void setIntervalo(double intervalo) {
      this.intervalo = intervalo;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getPalavra() {
      return palavra;
   }

   public void setPalavra(String palavra) {
      this.palavra = palavra;
   }

   public String getSigla() {
      return sigla;
   }

   public void setSigla(String sigla) {
      this.sigla = sigla;
   }

   public int getCorFundo() {
      return corFundo;
   }

   public void setCorFundo(int corFundo) {
      this.corFundo = corFundo;
   }

   public int getCorFonte() {
      return corFonte;
   }

   public void setCorFonte(int corFonte) {
      this.corFonte = corFonte;
   }

   public boolean insert() {
      XInsert insert = new XInsert();

      insert.setTabela("Estimulos");

      insert.addCampo("Est_Sigla", sigla);
      insert.addCampo("Est_Nome", nome);
      
      if (!XStringUtil.isVazio(audio)) {
         insert.addCampo("Est_Audio_Path", XFileUtil.getNomeArquivo(audio));
      }
      else {
         insert.addCampo("Est_Audio_Path", null);
      }
      
      if (!XStringUtil.isVazio(figura)) {
         insert.addCampo("Est_Figura_Path", XFileUtil.getNomeArquivo(figura));
      }
      else {
         insert.addCampo("Est_Figura_Path", null);
      }
      
      if (!XStringUtil.isVazio(palavra)) {
         insert.addCampo("Est_Palavra", palavra);
      }
      else {
         insert.addCampo("Est_Palavra", null);
      }

      insert.addCampo("Est_Intervalo", intervalo);
      insert.addCampo("Est_Cor_fundo", corFundo);
      insert.addCampo("Est_Cor_fonte", corFonte);

      try {
         if (insert.executa() > 0) {
            XInsert insert2 = new XInsert();

            if (!XStringUtil.isVazio(audio)) {
               insert2.setTabela("Estimulos_Audios");
               insert2.addCampo("Aud_Estimulo", new XSelect().getMax("Estimulos", "Est_Codigo"));
               insert2.addCampo("Aud_Arquivo", new File(audio));

               insert2.executa();

               XFileUtil.copiarArquivo(audio, Configuracao.dirAudioEstimulos + XFileUtil.getNomeArquivo(audio));
            }
            if (!XStringUtil.isVazio(figura)) {
               insert2 = new XInsert();

               insert2.setTabela("Estimulos_Figuras");
               insert2.addCampo("Fig_Estimulo", new XSelect().getMax("Estimulos", "Est_Codigo"));
               insert2.addCampo("Fig_Arquivo", new File(figura));

               insert2.executa();

               XFileUtil.copiarArquivo(figura, Configuracao.dirFiguraEstimulos + XFileUtil.getNomeArquivo(figura));
            }

            return true;
         }

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      } catch (FileNotFoundException e) {
         XTrataException.printStackTrace(e);
      } catch (IOException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean update() {
      try {
         
      XUpdate update = new XUpdate();

      update.setTabela("Estimulos");

      update.addCampo("Est_Sigla", sigla);
      update.addCampo("Est_Nome", nome);

      if (!XStringUtil.isVazio(audio)) {
         update.addCampo("Est_Audio_Path", XFileUtil.getNomeArquivo(audio));
      }
      else {
         if(!verificaTipoTentativa(AUDIO)){
            JOptionPane.showMessageDialog(null, "Não poderá retirar o audio pois está sendo refenrenciado.");
            return true;            
         }
         update.addCampo("Est_Audio_Path", null);
      }
      
      if (!XStringUtil.isVazio(figura)) {
         update.addCampo("Est_Figura_Path", XFileUtil.getNomeArquivo(figura));
      }
      else {
         
         if(!verificaTipoTentativa(DESENHO)){
            JOptionPane.showMessageDialog(null, "Não poderá retirar o desenho pois está sendo refenrenciado.");
            return true;            
         }
         update.addCampo("Est_Figura_Path", null);
      }
      
      if (!XStringUtil.isVazio(palavra)) {
         update.addCampo("Est_Palavra", palavra);
      }
      else {
         
         if(!verificaTipoTentativa(PALAVRA)){
            JOptionPane.showMessageDialog(null, "Não poderá retirar a palavra pois está sendo refenrenciado.");
            return true;            
         }
         update.addCampo("Est_Palavra", null);
      }

      update.addCampo("Est_Intervalo", intervalo);
      update.addCampo("Est_Cor_fundo", corFundo);
      update.addCampo("Est_Cor_fonte", corFonte);

      update.setWhere("Est_Codigo = ?", codigo);

         if (update.executa() > 0) {
            XUpdate update2 = new XUpdate();

            if (!XStringUtil.isVazio(audio)) {
               update2.setTabela("Estimulos_Audios");
               update2.addCampo("Aud_Arquivo", new File(audio));
               update2.setWhere("Aud_Estimulo = ?", codigo);

               update2.executa();

               XFileUtil.copiarArquivo(audio, Configuracao.dirAudioEstimulos + XFileUtil.getNomeArquivo(audio));
            }
            if (!XStringUtil.isVazio(figura)) {
               update2 = new XUpdate();

               update2.setTabela("Estimulos_Figuras");
               update2.addCampo("Fig_Arquivo", new File(figura));
               update2.setWhere("Fig_Estimulo = ?", codigo);

               update2.executa();

               XFileUtil.copiarArquivo(figura, Configuracao.dirFiguraEstimulos + XFileUtil.getNomeArquivo(figura));
            }

            return true;
         }

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      } catch (FileNotFoundException e) {
         XTrataException.printStackTrace(e);
      } catch (IOException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }

   public boolean delete() {
      XDelete delete = new XDelete();

      delete.setTabela("Estimulos");

      delete.setWhere("Est_Codigo = ?", codigo);

      try {

         if (delete.executa() > 0) {
            XDelete delete2 = new XDelete();

            delete2.setTabela("Estimulos_Audios");
            delete2.setWhere("Aud_Estimulo = ?", codigo);

            delete2.executa();

            delete2 = new XDelete();

            delete2.setTabela("Estimulos_Figuras");
            delete2.setWhere("Fig_Estimulo = ?", codigo);

            delete2.executa();

            return true;
         }

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

      select.setTabela("Estimulos");
      select.addCampo("*");
      select.setWhere("Est_Codigo = ?", codigo);

      try {

         ResultSet rs = select.executa();

         if (!rs.next()) {
            reset();

            return false;
         }

         sigla = rs.getString("Est_Sigla");
         nome = rs.getString("Est_Nome");
         
         if (!XStringUtil.isVazio(rs.getString("Est_Audio_Path"))) {
            audio = Configuracao.dirAudioEstimulos + rs.getString("Est_Audio_Path");
         }

         if (!XStringUtil.isVazio(rs.getString("Est_Figura_Path"))) {
            figura = Configuracao.dirFiguraEstimulos + rs.getString("Est_Figura_Path");
         }
         
         palavra = rs.getString("Est_Palavra");
         intervalo = rs.getInt("Est_Intervalo");
         corFundo = rs.getInt("Est_Cor_fundo");
         corFonte = rs.getInt("Est_Cor_fonte");

         return true;

      } catch (SQLException e) {
         XTrataException.printStackTrace(e);
      }

      return false;
   }
   
   private boolean verificaTipoTentativa(char tipo) throws SQLException{
      XSelect select = new XSelect();
      select.setTabela("Estimulos_Sub_Fase");
      select.addCampo("count(Est_Sub_Fase)");

      select.setWhere("(Est_Sub_Estimulo_Modelo = ? or Est_Sub_Estimulo_Escolha = ? ) " +
      		"and (Est_Sub_Estimulo_Modelo_Tipo = ? or Est_Sub_Estimulo_Escolha_Tipo = ?) ", codigo, codigo, tipo, tipo);
      
      ResultSet rs = select.executa();

      if(rs.next()){
         if(rs.getInt(1) > 0){
            return false;
         }
      }
      
      return true;
   }

}
