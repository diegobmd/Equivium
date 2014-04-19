package telas.realiza.sessao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import lib.beans.Estimulo;
import media.XAudio;
import swing.XButton;
import util.XTrataException;

public class ButtonRealiza extends XButton {

   private ThreadTexto threadTexto = new ThreadTexto();
   private Estimulo    estimulo    = null;

   public ButtonRealiza() {
      super("");
      setUI(new BasicButtonUI());
      setOpaque(true);
      setBorder(new LineBorder(Color.BLACK, 1));
      setFont(new Font("Verdana", Font.PLAIN, 72));
      setHorizontalAlignment(CENTER);
      setBackground(Color.WHITE);
      setForeground(Color.BLACK);
   }

   public void limpar() {
      setText(null);
      setIcon(null);
      estimulo = null;

      if (threadTexto != null) {
         threadTexto.parar();
         threadTexto = null;
      }

      setVisible(false);
   }

   public void limpaAcoes() {
      for (ActionListener m : getActionListeners()) {
         removeActionListener(m);
      }

      setEnabled(false);
   }

   public void setEstimulo(Estimulo estimulo) {

      this.estimulo = estimulo;

      if (estimulo == null) {
         return;
      }

      setVisible(true);

      estimulo.select();
      try {
         switch (estimulo.getTipo()) {
            case Estimulo.DESENHO:
               setIconTotal(new ImageIcon(estimulo.getFigura()));
               break;
            case Estimulo.PALAVRA:
               setBackground(new Color(estimulo.getCorFundo()));
               setForeground(new Color(estimulo.getCorFonte()));

               threadTexto = new ThreadTexto();
               threadTexto.setTexto(this, estimulo.getPalavra(), estimulo.getIntervalo());
               threadTexto.start();

               break;
            case Estimulo.COR:
               setOpaque(true);
               setBackground(new Color(estimulo.getCorFundo()));
               break;

            case Estimulo.AUDIO:
               new XAudio().play(estimulo.getAudio());

               break;
         }
      } catch (Exception e) {
         XTrataException.printStackTrace(e);
      }
   }

   public Estimulo getEstimulo() {
      return estimulo;
   }

   public void setVisible(boolean flag) {
      if (flag && estimulo == null) {
         return;
      }
      super.setVisible(flag);
      setEnabled(flag);
   }
}
