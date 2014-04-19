package lib.telas;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import configuracao.Configuracao;

import beans.XBeansInterface;

/**
 * 
 * <B> Tela para formularios de cadastro </B>
 * 
 * @author Diego
 * 
 */
public abstract class FrmCadastros extends FrmMenuBar {

   protected final int     INC      = 0;
   protected final int     ALT      = 1;
   protected final int     EXC      = 2;
   protected final int     CON      = 3;

   protected int           OPE      = -1;

   private final int       operacao = 1000;
   private final int       cancelar = 2000;

   private XBeansInterface bean;

   public FrmCadastros(String title) {
      super(title);

      addButton("Incluir", actionIncluir, Configuracao.dirImageSis + "b_incluir.gif", operacao);
      addButton("Alterar", actionAlterar, Configuracao.dirImageSis + "b_alterar.gif", operacao);
      addButton("Excluir", actionExcluir, Configuracao.dirImageSis + "b_excluir.gif", operacao);
      addButton("Buscar", actionConsultar, Configuracao.dirImageSis + "b_buscar.gif", operacao);
      addButton("Ok", actionOk, Configuracao.dirImageSis + "b_ok.gif", cancelar);
      addButton("Cancelar", actionCancelar, Configuracao.dirImageSis + "b_cancelar.gif", cancelar);

      addMenu("Arquivo");

      addSubMenu("Arquivo", "Incluir", actionIncluir, Configuracao.dirImageSis + "bp_incluir.gif", operacao);
      addSubMenu("Arquivo", "Alterar", actionAlterar, Configuracao.dirImageSis + "bp_alterar.gif", operacao);
      addSubMenu("Arquivo", "Excluir", actionExcluir, Configuracao.dirImageSis + "bp_excluir.gif", operacao);
      addSubMenu("Arquivo", "Buscar", actionConsultar, Configuracao.dirImageSis + "bp_buscar.gif", operacao);
      addSubMenu("Arquivo", "Ok", actionOk, Configuracao.dirImageSis + "bp_ok.gif", cancelar);
      addSubMenu("Arquivo", "Cancelar", actionCancelar, Configuracao.dirImageSis + "bp_cancelar.gif", cancelar);
      
      addSubMenu("Arquivo", "Sair", actioSair, null, operacao);

   }

   public void setVisible(boolean b) {
      super.setVisible(b);

      if (b) {
         cancelarClick();
      }
   }

   public abstract void initComponentes();

   
   Action actioSair        = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
         dispose();
      }
   };
   
   Action actionOk        = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                okClick();
                             }
                          };

   Action actionCancelar  = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                getButton("Ok").setVerifyInputWhenFocusTarget(true);
                                cancelarClick();
                             }
                          };

   Action actionIncluir   = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                incluirClick();
                             }
                          };

   Action actionAlterar   = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                alterarClick();
                             }
                          };

   Action actionExcluir   = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                excluirClick();
                             }
                          };

   Action actionConsultar = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                consultarClick();
                             }
                          };

   Action actionSair      = new AbstractAction() {
                             public void actionPerformed(ActionEvent e) {
                                dispose();
                             }
                          };

   public void alterarClick() {
      controlView.setControl(operacao, false, false);
      controlView.setControl(cancelar, true, false);
      controlView.setControl(CAMPO_CHAVE, true, true);
      controlView.setControl(CAMPO_NORMAL, false, true);
      controlView.requestFocus(CAMPO_CHAVE);
      OPE = ALT;
   }

   public void cancelarClick() {
      controlView.setControl(operacao, true, false);
      controlView.setControl(cancelar, false, false);
      controlView.setControl(CAMPO_CHAVE, false, true);
      controlView.setControl(CAMPO_NORMAL, false, true);
      tabbedPane.setSelectedIndex(0);
   }

   public void consultarClick() {
      controlView.setControl(operacao, false, false);
      controlView.setControl(cancelar, true, false);
      controlView.setControl(CAMPO_CHAVE, true, true);
      controlView.setControl(CAMPO_NORMAL, false, true);
      controlView.requestFocus(CAMPO_CHAVE);
      OPE = CON;
   }

   public void excluirClick() {
      controlView.setControl(operacao, false, false);
      controlView.setControl(cancelar, true, false);
      controlView.setControl(CAMPO_CHAVE, true, true);
      controlView.setControl(CAMPO_NORMAL, false, true);
      controlView.requestFocus(CAMPO_CHAVE);
      OPE = EXC;
   }

   public void incluirClick() {
      controlView.setControl(operacao, false, false);
      controlView.setControl(cancelar, true, false);
      controlView.setControl(CAMPO_CHAVE, false, true);
      controlView.setControl(CAMPO_NORMAL, true, true);
      controlView.requestFocus(CAMPO_NORMAL);
      OPE = INC;
   }

   public void okClick() {

      switch (OPE) {
         case INC:
            if (!controlView.validaCampos(CAMPO_NORMAL)) {
               return;
            }

            if (JOptionPane.showConfirmDialog(FrmCadastros.this, "Deseja realmente incluir?", "Confirmar...", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
               if (doIncluir()) {
                  JOptionPane.showMessageDialog(null, "Registro incluído com sucesso", "Cadastros", JOptionPane.INFORMATION_MESSAGE);
                  incluirClick();
               }
               else {
                  JOptionPane.showMessageDialog(null, "Erro ao incluir o registro", "Cadastros", JOptionPane.ERROR_MESSAGE);
               }
            }
            break;
         case ALT:
            if (!controlView.validaCampos(CAMPO_NORMAL)) {
               return;
            }
            if (JOptionPane.showConfirmDialog(FrmCadastros.this, "Deseja realmente alterar?", "Confirmar...", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
               if (doAlterar()) {
                  JOptionPane.showMessageDialog(null, "Registro alterado com sucesso", "Cadastros", JOptionPane.INFORMATION_MESSAGE);
                  alterarClick();
               }
               else {
                  JOptionPane.showMessageDialog(null, "Erro ao alterar o registro", "Cadastros", JOptionPane.ERROR_MESSAGE);
               }
            }
            break;
         case EXC:
            if (JOptionPane.showConfirmDialog(FrmCadastros.this, "Deseja realmente excluir?", "Confirmar...", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
               if (doExcluir()) {
                  JOptionPane.showMessageDialog(null, "Registro excluído com sucesso", "Cadastros", JOptionPane.INFORMATION_MESSAGE);
                  excluirClick();
               }
               else {
                  JOptionPane.showMessageDialog(null, "Erro ao excluir o registro", "Cadastros", JOptionPane.ERROR_MESSAGE);
               }
            }
            break;
         case CON:
            consultarClick();
            break;

         default:
            break;
      }

   }

   public abstract boolean setDadosBean();

   public XBeansInterface getBean() {
      return bean;
   }

   public void setBean(XBeansInterface bean) {
      this.bean = bean;
   }

   public boolean doIncluir() {
      if (setDadosBean()) {
         return bean.insert();
      }
      return false;
   }

   public boolean doAlterar() {
      if (setDadosBean()) {
         return bean.update();
      }
      return false;
   }

   public boolean doExcluir() {
      if (setDadosBean()) {
         return bean.delete();
      }
      return false;
   }

}
