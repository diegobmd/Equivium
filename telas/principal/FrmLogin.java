package telas.principal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lib.beans.Estimulo;
import lib.beans.Usuario;
import sql.bancos.XBanco;
import sql.bancos.XHSQLDB;
import swing.XButton;
import swing.XFrame;
import swing.XInError;
import swing.XPasswordField;
import swing.XTextField;
import util.XFileUtil;
import util.XThread;
import util.XTrataException;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import configuracao.Configuracao;

public class FrmLogin extends XFrame {

   private final XTextField     login   = new XTextField(2, 12, 25, "Login :");
   private final XPasswordField senha   = new XPasswordField(3, 12, 25, "Senha :");

   private final XButton        btConfi = new XButton(5, 5, 10, "Confirmar");
   private final XButton        btCance = new XButton(5, 25, 10, "Cancelar");

   public FrmLogin() {
      super(320, 220);
      setTitle("LOGIN");

      initComponents();
   }

   private void initComponents() {
      conectar();

      addWindowListener(new CloseListener());

      add(login);
      add(senha);

      add(btConfi);
      add(btCance);

      btConfi.addActionListener(acConfirmar);
      btCance.addActionListener(acCancelar);

      new Carregar().start();

   }

   private void conectar() {
      try {
         //Se está no eclipse
         if (XFileUtil.getCurrentPath().contains("workspace")) {

            XBanco.setBanco(new XHSQLDB("banco/hsqldbGino/equivium", "sa", ""));
            XBanco.setShowSQL(false);
            XTrataException.setShowException(true);
         }
         else {
            File f2 = new File("banco/hsqldb2/");
            File f1 = new File("banco/hsqldb/");

            if (f2.exists()) {
               if (f1.exists()) {
                  f1.delete();
               }

               f1.mkdirs();

               XFileUtil.copiarArquivo("banco/hsqldb2/equivium.properties", "banco/hsqldb/equivium.properties");
               XFileUtil.copiarArquivo("banco/hsqldb2/equivium.script", "banco/hsqldb/equivium.script");

               for (File ff : f2.listFiles()) {
                  ff.delete();
               }

               f2.delete();
            }

            XBanco.setBanco(new XHSQLDB("banco/hsqldb/equivium", "sa", ""));
            XBanco.setShowSQL(false);
            XTrataException.setShowException(false);
         }
         
         Estimulo est = new Estimulo();
         
         est.setCodigo(23);
         
         System.out.println(est.getFigura());
         
         
      } catch (Exception e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, "Erro ao se conectar ao banco de dados o sistema será fechado!!");
         System.exit(0);
      }
   }

   private class Carregar extends XThread {

      public void run() {
         btConfi.setEnabled(false);
         btCance.setEnabled(false);
         login.setEnabled(false);
         senha.setEnabled(false);

         try {

            Configuracao.verificaTabelasPadrao(FrmLogin.this);

         } catch (Exception e) {
            XTrataException.printStackTrace(e);
         }

         setTitle("Benvindo ao EQUIVIUM");

         btConfi.setEnabled(true);
         btCance.setEnabled(true);
         login.setEnabled(true);
         senha.setEnabled(true);

         login.requestFocus();
      }
   }

   Action acCancelar  = new AbstractAction() {
                         public void actionPerformed(ActionEvent e) {
                            try {
                               XBanco.getConexao().close();
                            } catch (SQLException e1) {
                               XTrataException.printStackTrace(e1);
                            }
                            System.exit(0);
                         }
                      };

   Action acConfirmar = new AbstractAction() {
                         public void actionPerformed(ActionEvent e) {
                            XInError.setInError(login, true);
                            XInError.setInError(senha, true);

                            if (!(login.getText().trim().length() == 0 || String.valueOf(senha.getPassword()).trim().length() == 0) && Usuario.validaUser(login.getText(), String.valueOf(senha.getPassword()))) {
                               new FrmMenuPrincipal().setVisible(true);
                               setVisible(false);
                            }
                            else {
                               JOptionPane.showMessageDialog(FrmLogin.this, "Dados de usuário inválidos!!!");

                               login.requestFocus();
                               login.selectAll();
                            }
                         }
                      };

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());

         UIManager.put("ComboBox.disabledBackground", Color.WHITE);
         UIManager.put("ComboBox.disabledForeground", Color.BLACK);
         UIManager.put("TextField.disabledBackground", Color.WHITE);
         UIManager.put("TextField.disabledForeground", Color.BLACK);
         UIManager.put("PasswordField.disabledBackground", Color.WHITE);
         UIManager.put("PasswordField.disabledForeground", Color.BLACK);
         UIManager.put("OptionPane.yesButtonText", "Sim");
         UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.cancelButtonText", "Cancelar");  

      } catch (UnsupportedLookAndFeelException e) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception e1) {
            XTrataException.printStackTrace(e);
         }
      }

      new FrmLogin().setVisible(true);
   }

}
