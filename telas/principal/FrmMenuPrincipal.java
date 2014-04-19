package telas.principal;

import swing.XFrame;
import util.XFileUtil;

public class FrmMenuPrincipal extends XFrame {

//   private final JMenuBar menuBar = new JMenuBar();

   public FrmMenuPrincipal() {
      super(800, 600);
      setTitle("Equivium");
      setLayout(null);
      setLocationRelativeTo(null);
      setResizable(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setExtendedState(MAXIMIZED_BOTH);

      addWindowListener(new CloseListener());

      initComponentes();
   }

   private void initComponentes() {
      addJMenuBar(XFileUtil.getCurrentPath() + "\\menu.xml");
      
//      setJMenuBar(menuBar);
//
//      try {
//         for (JMenuItem m : new GeraMenu().getMenus()) {
//            menuBar.add(m);
//         }
//      } catch (Exception e) {
//         XTrataException.printStackTrace(e);
//      }

   }

}
