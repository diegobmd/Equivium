package configuracao;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdom.JDOMException;

import sql.bancos.sinc.XAtualizaBanco;
import sql.querys.XInsert;
import sql.querys.XSelect;
import telas.principal.FrmLogin;
import util.XFileUtil;
import conversores.ScriptSQL;

public class Configuracao {

   public final static String dirAudioReforco    = XFileUtil.getCurrentPath() + "\\arquivos\\reforcos\\audio\\";

   public final static String dirAudioEstimulos  = XFileUtil.getCurrentPath() + "\\arquivos\\estimulos\\audio\\";
   public final static String dirFiguraEstimulos = XFileUtil.getCurrentPath() + "\\arquivos\\estimulos\\figuras\\";

   public final static String dirImageSis        = XFileUtil.getCurrentPath() + "\\imagens\\sis\\";

   public static void verificaTabelasPadrao(FrmLogin f) throws IOException, SQLException, JDOMException {
      f.setTitle("Atualizando banco de dados...");
      new XAtualizaBanco().atualizaBanco();
      f.setTitle("Executando Scripts...");
      new ScriptSQL().rodarScripts();
      f.setTitle("Inserindo Usuário para testes...");
      insereUserTeste();
      f.setTitle("Inserindo estados...");
      insereEstados();
      f.setTitle("Inserindo escolaridades...");
      insereEscolaridades();

      descarregaArquivos();
   }

   private static void insereEscolaridades() throws SQLException {
      XSelect select = new XSelect();
      select.setTabela("escolaridade");
      select.addCampo("count(esc_codigo)");

      ResultSet rs = select.executa();

      if (!rs.next() || rs.getInt(1) <= 0) {
         XInsert insert;

         insert = new XInsert();
         insert.setTabela("escolaridade");
         insert.addCampo("esc_descricao", "Pré");
         insert.addCampo("ESC_CODIGO", 1);
         insert.executa();
         insert = new XInsert();
         insert.setTabela("escolaridade");
         insert.addCampo("esc_descricao", "Ensino Fundamental");
         insert.addCampo("ESC_CODIGO", 2);
         insert.executa();
         insert = new XInsert();
         insert.setTabela("escolaridade");
         insert.addCampo("esc_descricao", "Ensino Médio");
         insert.addCampo("ESC_CODIGO", 3);
         insert.executa();
         insert = new XInsert();
         insert.setTabela("escolaridade");
         insert.addCampo("esc_descricao", "Técnico");
         insert.addCampo("ESC_CODIGO", 4);
         insert.executa();
         insert = new XInsert();
         insert.setTabela("escolaridade");
         insert.addCampo("esc_descricao", "Superior");
         insert.addCampo("ESC_CODIGO", 5);
         insert.executa();

      }

   }

   private static void insereEstados() throws SQLException {
      XSelect select = new XSelect();
      select.setTabela("estado");
      select.addCampo("count(est_sigla)");

      ResultSet rs = select.executa();

      if (!rs.next() || rs.getInt(1) <= 0) {
         XInsert insert;

         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "AC");
         insert.addCampo("est_descricao", "Acre");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "AL");
         insert.addCampo("est_descricao", "Alagoas");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "AP");
         insert.addCampo("est_descricao", "Amapá");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "AM");
         insert.addCampo("est_descricao", "Amazonas");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "BA");
         insert.addCampo("est_descricao", "Bahia");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "CE");
         insert.addCampo("est_descricao", "Ceará");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "DF");
         insert.addCampo("est_descricao", "Distrito Federal");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "ES");
         insert.addCampo("est_descricao", "Espírito Santo");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "GO");
         insert.addCampo("est_descricao", "Goiás");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "MA");
         insert.addCampo("est_descricao", "Maranhão");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "MT");
         insert.addCampo("est_descricao", "Mato Grosso");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "MS");
         insert.addCampo("est_descricao", "Mato Grosso do Sul");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "MG");
         insert.addCampo("est_descricao", "Minas Gerais");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "PA");
         insert.addCampo("est_descricao", "Pará");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "PB");
         insert.addCampo("est_descricao", "Paraíba");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "PR");
         insert.addCampo("est_descricao", "Paraná");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "PE");
         insert.addCampo("est_descricao", "Pernambuco");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "PI");
         insert.addCampo("est_descricao", "Piauí");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "RJ");
         insert.addCampo("est_descricao", "Rio de Janeiro");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "RN");
         insert.addCampo("est_descricao", "Rio Grande do Norte");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "RS");
         insert.addCampo("est_descricao", "Rio Grande do Sul");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "RO");
         insert.addCampo("est_descricao", "Rondônia");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "RR");
         insert.addCampo("est_descricao", "Roraima");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "SC");
         insert.addCampo("est_descricao", "Santa Catarina");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "SP");
         insert.addCampo("est_descricao", "São Paulo");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "SE");
         insert.addCampo("est_descricao", "Sergipe");
         insert.executa();
         insert = new XInsert();
         insert.setTabela("estado");
         insert.addCampo("est_sigla", "TO");
         insert.addCampo("est_descricao", "Tocantins");
         insert.executa();

      }

   }

   private static void insereUserTeste() throws SQLException {

      XSelect select = new XSelect();
      select.setTabela("Pesquisadores");
      select.addCampo("Pes_Nome");
      select.setWhere("Pes_Nome = ?", "teste");

      ResultSet rs = select.executa();

      if (!rs.next()) {

         XInsert insert = new XInsert();

         insert.setTabela("Pesquisadores");
         insert.addCampo("Pes_Nome", "teste");
         insert.addCampo("Pes_Login", "teste");
         insert.addCampo("Pes_Senha", "teste");

         insert.executa();

      }

   }

   private static void descarregaArquivos() throws SQLException, IOException {
      XSelect select;
      File file;
      ResultSet rs;

      /** ******* REFORçOS ****************** */
      /** *********************************** */
      /** ******* AUDIOS ******************** */
      /** *********************************** */

      select = new XSelect();

      select.setTabela("Reforcos");

      select.addCampo("Ref_Audio");
      select.addCampo("Aud_Arquivo");

      select.addInnerJoin("Reforcos_Audios", "Reforcos.Ref_Codigo = Reforcos_Audios.Aud_Reforco");

      file = new File(Configuracao.dirAudioReforco);

      if (file.exists()) {
         for (File f : file.listFiles()) {
            f.delete();
         }
      }
      else {
         file.mkdirs();
      }

      rs = select.executa();

      while (rs.next()) {
         XFileUtil.createFile(rs.getBinaryStream("Aud_Arquivo"), Configuracao.dirAudioReforco + rs.getString("Ref_Audio"));
      }

      /** ******* ESTIMULOS ***************** */
      /** *********************************** */
      /** ******* AUDIOS ******************** */
      /** *********************************** */

      select = new XSelect();

      select.setTabela("Estimulos");

      select.addCampo("Est_Audio_Path");
      select.addCampo("Aud_Arquivo");

      select.addInnerJoin("Estimulos_Audios", "Estimulos.Est_Codigo = Estimulos_Audios.Aud_Estimulo");

      file = new File(Configuracao.dirAudioEstimulos);

      if (file.exists()) {
         for (File f : file.listFiles()) {
            f.delete();
         }
      }
      else {
         file.mkdirs();
      }

      rs = select.executa();

      while (rs.next()) {
         XFileUtil.createFile(rs.getBinaryStream("Aud_Arquivo"), Configuracao.dirAudioEstimulos + rs.getString("Est_Audio_Path"));
      }

      /** ******* ESTIMULOS ***************** */
      /** *********************************** */
      /** ******* FIGURAS ******************* */
      /** *********************************** */

      select = new XSelect();

      select.setTabela("Estimulos");

      select.addCampo("Est_Figura_Path");
      select.addCampo("Fig_Arquivo");

      select.addInnerJoin("Estimulos_Figuras", "Estimulos.Est_Codigo = Estimulos_Figuras.Fig_Estimulo");

      file = new File(Configuracao.dirFiguraEstimulos);

      if (file.exists()) {
         for (File f : file.listFiles()) {
            f.delete();
         }
      }
      else {
         file.mkdirs();
      }

      rs = select.executa();

      while (rs.next()) {
         XFileUtil.createFile(rs.getBinaryStream("Fig_Arquivo"), Configuracao.dirFiguraEstimulos + rs.getString("Est_Figura_Path"));
      }
   }

}
