package telas.principal;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import sql.bancos.XBanco;
import util.XTrataException;

public class CloseListener implements WindowListener {

   public void windowActivated(WindowEvent e) {
   }

   public void windowClosed(WindowEvent e) {
   }

   public void windowClosing(WindowEvent e) {
      try {
         XBanco.getConexao().close();
      } catch (SQLException e1) {
         XTrataException.printStackTrace(e1);
      }
   }

   public void windowDeactivated(WindowEvent e) {
   }

   public void windowDeiconified(WindowEvent e) {
   }

   public void windowIconified(WindowEvent e) {
   }

   public void windowOpened(WindowEvent e) {
   }

}
