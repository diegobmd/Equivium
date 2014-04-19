package telas.relatorios;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Vector;

import lib.campos.CParticipantes;
import lib.campos.CRealizaAnagrama;
import lib.telas.FrmRelatorios;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sql.bancos.XBanco;
import swing.XPanel;
import swing.XScrollPane;
import swing.XTableCampo;
import util.XTrataException;

/**
 * 
 * Relatorio de sessoes realizadas
 * 
 * @author Diego
 * 
 */
public class FrmRelAnaliseRespostasAnagrama extends FrmRelatorios {

   private final XPanel      panel           = new XPanel();
   private final XPanel      panel2          = new XPanel();
   private final XScrollPane scrollPane      = new XScrollPane(panel);

   private final XTableCampo taSessoes       = new XTableCampo(1, 2, 70, 8, "Realiza Sessões", new CRealizaAnagrama(0, 0, 0, ""));
   private final XTableCampo taParticipantes = new XTableCampo(7, 2, 70, 8, "Participantes", new CParticipantes(0, 0, 0, ""));

   public FrmRelAnaliseRespostasAnagrama() {
      super("Relatório de Análise de Respostas");

      initComponentes();
   }

   public void initComponentes() {

      panel.add(taSessoes);
      panel.add(taParticipantes);

      panel.setPreferredSize(new Dimension(panel.getWidth(), 600));

      panel2.setLayout(new BorderLayout());

      panel2.add(scrollPane);

      addPainel("Principal", panel2);

      controlView.add(CAMPO_NORMAL, taSessoes);
      controlView.add(CAMPO_NORMAL, taParticipantes);

   }

   public void doIniciar() {
      try {
         HashMap<String, String> parameters = new HashMap<String, String>();

         parameters.put("sql", getWhere());

         JasperPrint jasperPrint = JasperFillManager.fillReport("./relatorios/Realisa_Sessao_Anagrama.jasper", parameters, XBanco.getConexao());

         JasperViewer.viewReport(jasperPrint, false);

      } catch (JRException e) {
         XTrataException.printStackTrace(e);
      }
   }

   private String getWhere() {
      String where = "";
      Vector<Object> vAux;

      if (taSessoes.getRowCount() > 0) {
         if (where.length() == 0) {
            where = " WHERE ";
         }
         else {
            where += " AND ";
         }

         where += " RAN_CODIGO IN ( ";

         for (int i = 0; i < taSessoes.getRowCount(); i++) {
            vAux = taSessoes.getRow(i);

            if (i > 0) {
               where += " , ";
            }

            where += vAux.get(0);
         }

         where += " ) ";
      }
      if (taParticipantes.getRowCount() > 0) {
         if (where.length() == 0) {
            where = " WHERE ";
         }
         else {
            where += " AND ";
         }

         where += " PAR_CODIGO IN ( ";

         for (int i = 0; i < taParticipantes.getRowCount(); i++) {
            vAux = taParticipantes.getRow(i);

            if (i > 0) {
               where += " , ";
            }

            where += vAux.get(0);
         }

         where += " ) ";
      }

      return " " + where;
   }

}
