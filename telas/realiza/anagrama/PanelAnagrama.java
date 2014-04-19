package telas.realiza.anagrama;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

import swing.XLabel;
import swing.XLabelMove;
import swing.XPanel;

public class PanelAnagrama extends XPanel {

   private final XLabel             delimitador = new XLabel();
   private final XLabel             modelo      = new XLabel();
   private final ArrayList<LbCubos> cubos       = new ArrayList<LbCubos>();
   private final ArrayList<XLabel>  encai       = new ArrayList<XLabel>();
   private Dimension                tela        = Toolkit.getDefaultToolkit().getScreenSize();

   public PanelAnagrama() {
      this(0);
   }

   public PanelAnagrama(int qtdEncaixes) {

      if (qtdEncaixes > 0 && qtdEncaixes < 5) {
         setEncaixes(qtdEncaixes);
      }

      delimitador.setBounds(getDelimitador());
      delimitador.setBorder(new LineBorder(Color.BLACK));

      modelo.setOpaque(true);
      modelo.setBackground(Color.WHITE);
      modelo.setForeground(Color.BLACK);
      modelo.setBorder(new LineBorder(Color.BLACK));
      modelo.setFont(new Font("Verdana", Font.PLAIN, getTamFonte()));
      modelo.setHorizontalAlignment(XLabel.CENTER);

      add(delimitador);
      add(modelo);
   }

   public void setModeloImagem(ImageIcon p) {
      modelo.setIcon(null);
      modelo.setText(null);
      modelo.setVisible(true);

      Rectangle[] r = getEncaixes(1);

      modelo.setIconTotal(p);

      r[0].y = 10;

      modelo.setBounds(r[0]);
   }

   public void setModeloPalavra(String p) {
      modelo.setIcon(null);
      modelo.setText(null);
      modelo.setVisible(true);

      int t = p.split("[+]").length;

      Rectangle[] r = getEncaixes(t);

      modelo.setText(p.replaceAll("[+]", "  "));

      r[0].width = (r[t - 1].x + r[t - 1].width) - r[0].x;
      r[0].y = 10;

      modelo.setBounds(r[0]);
   }

   public void setEncaixes(int qtdEncaixes) {
      if (qtdEncaixes > 4 || qtdEncaixes < 0) {
         throw new RuntimeException("Quantidade de encaixes invalido > 4 e < 0 : " + qtdEncaixes);
      }

      XLabel lbtst;

      limparEncaixes();

      for (Rectangle r : getEncaixes(qtdEncaixes)) {
         lbtst = new XLabel();
         lbtst.setBorder(new LineBorder(Color.BLACK));
         lbtst.setBounds(r);

         encai.add(lbtst);
         add(lbtst);
      }
   }

   public void addCubos(String... conjuntos) {
      LbCubos label;
      ArrayList<Rectangle> encaixes = getEncaixes();

      for (int i = 0; i < conjuntos.length && i < 4; i++) {

         label = new LbCubos(conjuntos[i], delimitador.getBounds(), encaixes);

         cubos.add(label);

         add(label);
      }
   }

   public String getCubosEncaixados() {
      String ret = "";

      TreeMap<Integer, LbCubos> map = new TreeMap<Integer, LbCubos>();

      ArrayList<Rectangle> encaixes = getEncaixes();

      for (LbCubos l : cubos) {
         if (encaixes.contains(l.getBounds())) {
            map.put(l.getBounds().x, l);
         }
      }

      Iterator<Integer> itera = map.keySet().iterator();

      while (itera.hasNext()) {
         ret += map.get(itera.next()).getText();
      }

      return ret;
   }

   public void limparModelo() {
      modelo.setVisible(false);
      repaint();
   }

   public void limparCubos() {

      for (LbCubos l : cubos) {
         remove(l);
      }

      cubos.clear();
      repaint();
   }

   private void limparEncaixes() {
      for (XLabel l : encai) {
         remove(l);
      }

      encai.clear();
      repaint();
   }

   private class LbCubos extends XLabelMove {

      public LbCubos(String slabel, Rectangle perim, ArrayList<Rectangle> recIma) {
         super(slabel.toUpperCase());

         setFont(new Font("Verdana", Font.PLAIN, getTamFonte()));

         Rectangle[] r = getEncaixes(1);

         setSize(new Dimension(r[0].width, r[0].height));
         setHorizontalAlignment(CENTER);

         setPerimetro(perim);
         setRectangleIma(recIma);

         setOpaque(true);

         setBackground(Color.YELLOW);
         setForeground(Color.BLACK);

         setPorcentagemAux(0.5);

         setBorder(new LineBorder(Color.BLACK));

      }
   }

   private int getTamFonte() {
      return (int) (tela.height * 0.1);
   }

   private Rectangle[] getEncaixes(int qtd) {
      Rectangle[] posicao = new Rectangle[qtd];
      Rectangle r = new Rectangle();
      int total;
      int telaa;

      r.width = (int) (tela.height * 0.2);
      r.height = (int) (tela.height * 0.2);
      r.y = (int) (tela.height * 0.25);

      total = ((r.width * qtd) + (qtd * 10)) / 2;
      telaa = (tela.width / 2) - total;

      for (int i = 0; i < posicao.length; i++) {
         r.x = telaa + (r.width * i) + (i * 10);

         posicao[i] = new Rectangle(r);
      }
      return posicao;
   }

   private Rectangle getDelimitador() {
      Rectangle perimetro = new Rectangle();

      perimetro.height = (int) (tela.height * 0.4);
      perimetro.width = (int) (tela.width - (tela.width * 0.05));
      perimetro.x = (int) (tela.width * 0.025);
      perimetro.y = (int) (tela.height * 0.5);

      return perimetro;
   }

   public ArrayList<Rectangle> getEncaixes() {
      ArrayList<Rectangle> encaixes = new ArrayList<Rectangle>();

      for (XLabel l : encai) {
         encaixes.add(l.getBounds());
      }
      return encaixes;
   }
}
