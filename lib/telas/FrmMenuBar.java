package lib.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import swing.XButton;
import swing.XFrame;
import swing.XMenu;
import swing.XMenuItem;
import swing.XTabbedPane;
import swing.util.XControlView;
import util.XStringUtil;

public abstract class FrmMenuBar extends XFrame {

   protected final JMenuBar               menuBar      = new JMenuBar();
   protected final JToolBar               toolBar      = new JToolBar(JToolBar.HORIZONTAL);
   protected final XTabbedPane            tabbedPane   = new XTabbedPane();
   protected final Dimension              dSeparador   = new Dimension(3, 3);

   private final HashMap<String, XButton> botoes       = new HashMap<String, XButton>();
   private final HashMap<String, JMenu>   menus        = new HashMap<String, JMenu>();
   public final XControlView              controlView  = new XControlView();

   protected final int                    CAMPO_NORMAL = 0;
   protected final int                    CAMPO_CHAVE  = 1;

   public FrmMenuBar(String titulo) {
      super(640, 480);
      setTitle(titulo);

      setLocationRelativeTo(null);

      setLayout(new BorderLayout());

      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

      add(toolBar, BorderLayout.NORTH);
      add(tabbedPane, BorderLayout.CENTER);

      // tabbedPane.setVerifyInputWhenFocusTarget(false);

      setJMenuBar(menuBar);
      
      setResizable(true);
      
      setExtendedState(MAXIMIZED_BOTH);
   }

   public void addPainel(String stext, JPanel panel) {
      tabbedPane.addTab(stext, panel);
   }

   public void addButton(String label, ActionListener action, String image, Object oper) {
      XButton button = new XButton(label);

      Dimension d = new Dimension(90, 50);

      button.setPreferredSize(d);
      button.setMaximumSize(d);
      button.setMinimumSize(d);

      if (!XStringUtil.isVazio(image)) {
         button.setIcon(new ImageIcon(image));
      }

      button.addActionListener(action);

      if (label.equalsIgnoreCase("cancelar")) {
         button.setVerifyInputWhenFocusTarget(false);
      }

      if (oper != null) {
         controlView.add(oper, button);
      }

      botoes.put(label, button);

      toolBar.add(button);
      toolBar.addSeparator(dSeparador);
   }

   public XButton getButton(String label) {
      if (botoes.containsKey(label)) {
         return botoes.get(label);
      }

      throw new NullPointerException("Botão não encontrado");
   }

   public void addMenu(String label) {
      XMenu menu = new XMenu(label);

      menuBar.add(menu);

      menus.put(label, menu);
   }

   public void removeButton(String label) {
      if (botoes.containsKey(label)) {
         toolBar.remove(botoes.get(label));
         botoes.remove(label);

         return;
      }

      throw new NullPointerException("Botão não encontrado");
   }

   public void removeSubMenu(String label) {
      for (Iterator<String> it = menus.keySet().iterator(); it.hasNext();) {
         String posicao = it.next();

         for (Component m : menus.get(posicao).getMenuComponents()) {
            if (((JMenuItem) m).getText().equalsIgnoreCase(label)) {
               menus.get(posicao).remove(m);

               return;
            }
         }

      }
      throw new NullPointerException("Botão não encontrado");
   }

   public void addSubMenu(String parent, String label, ActionListener action, String image, Object oper) {

      if (menus.containsKey(parent)) {
         XMenuItem menuItem = new XMenuItem(label);

         if (label.equalsIgnoreCase("sair")) {
            menus.get(parent).addSeparator();
         }

         menus.get(parent).add(menuItem);

         if (!XStringUtil.isVazio(image)) {
            menuItem.setIcon(new ImageIcon(image));
         }

         menuItem.addActionListener(action);

         if (label.equalsIgnoreCase("cancelar")) {
            menuItem.setVerifyInputWhenFocusTarget(false);
         }

         if (oper != null) {
            controlView.add(oper, menuItem);
         }
      }
   }

   public abstract void initComponentes();

}
