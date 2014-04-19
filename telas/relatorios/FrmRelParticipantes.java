package telas.relatorios;

import java.util.HashMap;
import java.util.Vector;

import lib.campos.CParticipantes;
import lib.telas.FrmRelatorios;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sql.bancos.XBanco;
import swing.XPanel;
import swing.XTableCampo;
import util.XTrataException;

/**
 * 
 * Relatorio de sessoes realizadas
 * 
 * @author Diego
 * 
 */
public class FrmRelParticipantes extends FrmRelatorios {

   private final XPanel      panel           = new XPanel();

   private final XTableCampo taPesquisadores = new XTableCampo(1, 2, 70, 8, "Participantes", new CParticipantes(0, 0, 0, ""));

   public FrmRelParticipantes() {
      super("Relatório de Participantes");

      initComponentes();
   }

   public void initComponentes() {

      panel.add(taPesquisadores);

      addPainel("Principal", panel);

      controlView.add(CAMPO_NORMAL, taPesquisadores);

   }

   public void doIniciar() {
      try {
         HashMap<String, String> parameters = new HashMap<String, String>();

//         JasperReport jasperReport = JasperCompileManager.compileReport("./relatorios/Participantes.jrxml");

         parameters.put("sql", getWhere());

         JasperPrint jasperPrint = JasperFillManager.fillReport("./relatorios/Participantes.jasper", parameters, XBanco.getConexao());

         JasperViewer.viewReport(jasperPrint, false);

      } catch (JRException e) {
         XTrataException.printStackTrace(e);
      }
   }

   private String getWhere() {
      String where = "";
      Vector<Object> vAux;

      if (taPesquisadores.getRowCount() > 0) {
         if (where.length() == 0) {
            where = " WHERE ";
         }
         else {
            where += " AND ";
         }

         where += " PAR_CODIGO IN ( ";

         for (int i = 0; i < taPesquisadores.getRowCount(); i++) {
            vAux = taPesquisadores.getRow(i);

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
