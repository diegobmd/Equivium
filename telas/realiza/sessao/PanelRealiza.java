package telas.realiza.sessao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.border.EmptyBorder;

import lib.beans.Estimulo;
import lib.beans.ItemRealizaSessao;
import media.XAudio;
import swing.XLabel;
import swing.XPanel;
import util.XThread;
import util.XTrataException;

public class PanelRealiza extends XPanel {

   private final ButtonRealiza    btE1          = new ButtonRealiza();
   private final ButtonRealiza    btE2          = new ButtonRealiza();
   private final ButtonRealiza    btE3          = new ButtonRealiza();
   private final ButtonRealiza    btE4          = new ButtonRealiza();
   private final ButtonRealiza    btE5          = new ButtonRealiza();
   private final ButtonRealiza    btE6          = new ButtonRealiza();

   private final ButtonRealiza    btMO          = new ButtonRealiza();
   private final XLabel           lb01          = new XLabel();
   private final XLabel           lb02          = new XLabel();

   private ItemRealizaSessao      itemDaTela    = null;
   private boolean                liberaEscolha = false;
   private boolean                liberaAtalho  = true;
   private boolean                acabou        = false;
   private long                   tempo         = 0;

   private final FrmExecutaSessao execucao;

   public PanelRealiza(FrmExecutaSessao exec) {

      this.execucao = exec;

      setLayout(new GridLayout(3, 3, 30, 30));
      setBorder(new EmptyBorder(10, 10, 10, 10));

      add(btE1);
      add(btE2);
      add(btE3);

      add(btE4);
      add(btE5);
      add(btE6);

      add(lb01);
      add(btMO);
      add(lb02);

      addKeyListener(new TeclasAtalho());

      btE1.addKeyListener(new TeclasAtalho());
      btE2.addKeyListener(new TeclasAtalho());
      btE3.addKeyListener(new TeclasAtalho());
      btE4.addKeyListener(new TeclasAtalho());
      btE5.addKeyListener(new TeclasAtalho());
      btE6.addKeyListener(new TeclasAtalho());
      btMO.addKeyListener(new TeclasAtalho());
   }

   public void limpaTela() {
      btE1.limpar();
      btE2.limpar();
      btE3.limpar();
      btE4.limpar();
      btE5.limpar();
      btE6.limpar();
      btMO.limpar();
      btMO.limpaAcoes();

      repaint();
   }

   public void limpaAcoes() {
      btE1.limpaAcoes();
      btE2.limpaAcoes();
      btE3.limpaAcoes();
      btE4.limpaAcoes();
      btE5.limpaAcoes();
      btE6.limpaAcoes();
      btMO.limpaAcoes();
   }

   public void setEstimulo(int i, Estimulo e, boolean mostra) {
      switch (i) {
         case 0:
            btMO.setEstimulo(e);
            btMO.setVisible(mostra);
            break;
         case 1:
            btE1.setEstimulo(e);
            btE1.setVisible(mostra);
            break;
         case 2:
            btE2.setEstimulo(e);
            btE2.setVisible(mostra);
            break;
         case 3:
            btE3.setEstimulo(e);
            btE3.setVisible(mostra);
            break;
         case 4:
            btE4.setEstimulo(e);
            btE4.setVisible(mostra);
            break;
         case 5:
            btE5.setEstimulo(e);
            btE5.setVisible(mostra);
            break;
         case 6:
            btE6.setEstimulo(e);
            btE6.setVisible(mostra);
            break;
      }
   }

   public void addActionListener(int i, ActionListener l) {
      switch (i) {
         case 0:
            btMO.addActionListener(l);
            break;
         case 1:
            btE1.addActionListener(l);
            break;
         case 2:
            btE2.addActionListener(l);
            break;
         case 3:
            btE3.addActionListener(l);
            break;
         case 4:
            btE4.addActionListener(l);
            break;
         case 5:
            btE5.addActionListener(l);
            break;
         case 6:
            btE6.addActionListener(l);
            break;
      }
   }

   public void setTela(ItemRealizaSessao itRea) {
      new SetTela(itRea).start();
   }

   private class ActionAcertou implements ActionListener {

      public void actionPerformed(ActionEvent e) {
         ButtonRealiza b = (ButtonRealiza) e.getSource();

         if (liberaEscolha) {
            acertou(b.getEstimulo());
         }
      }

   }

   private class ActionModelo implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         liberaEscolha = true;

         if (itemDaTela.getSubFase().isRespObs()) {
            btE1.setVisible(true);
            btE2.setVisible(true);
            btE3.setVisible(true);
            btE4.setVisible(true);
            btE5.setVisible(true);
            btE6.setVisible(true);
         }
      }
   }

   private class ActionErrou implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         ButtonRealiza b = (ButtonRealiza) e.getSource();

         errou(b.getEstimulo());
      }
   }

   private void acertou(Estimulo e) {
      if (!liberaEscolha && itemDaTela.getSubFase().isRespObs()) {
         btE1.setVisible(true);
         btE2.setVisible(true);
         btE3.setVisible(true);
         btE4.setVisible(true);
         btE5.setVisible(true);
         btE6.setVisible(true);
         liberaEscolha = true;
         return;
      }

      liberaAtalho = false;

      limpaAcoes();

      itemDaTela.setEscolhido(e);
      itemDaTela.setTempo(System.currentTimeMillis() - tempo);
      itemDaTela.insert();

      execucao.addAcerto(itemDaTela.getSubFase().getInstancia());

      if (itemDaTela.getAcada() != null && itemDaTela.getAcada().getCodigo() > 0) {
         if (new File(itemDaTela.getAcada().getAudio()).exists()) {
            try {
               new XAudio().play(itemDaTela.getAcada().getAudio());
            } catch (Exception e1) {
               XTrataException.printStackTrace(e1);
            }
         }
         else {
            XTrataException.addLog("Arquivo não encontrado :");
            XTrataException.addLog(itemDaTela.getAcada().getAudio());
         }
      }

      execucao.proximo();
   }

   private void errou(Estimulo e) {
      liberaAtalho = false;

      limpaAcoes();
      
      if( e!= null)
      itemDaTela.setEscolhido(e);
      else
      itemDaTela.setEscolhido(new Estimulo());
      
      itemDaTela.setTempo(System.currentTimeMillis() - tempo);
      itemDaTela.insert();

      execucao.proximo();

   }

   private class TeclasAtalho implements KeyListener {
      public void keyPressed(KeyEvent e) {
         if (liberaAtalho) {
            switch (e.getKeyCode()) {
               case KeyEvent.VK_1:
                  acertou(itemDaTela.getEscCorreto());
                  break;

               case KeyEvent.VK_3:
                  errou(null);
                  break;

               default:
                  break;
            }
         }
      }

      public void keyReleased(KeyEvent e) {
      }

      public void keyTyped(KeyEvent e) {
      }
   }

   private class SetTela extends XThread {

      private ItemRealizaSessao itRea;

      public SetTela(ItemRealizaSessao itRea) {
         this.itRea = itRea;
      }

      public void run() {
         
         if(acabou){
            return;
         }
         
         limpaTela();

         liberaEscolha = false;

         Estimulo es;
         int posicao;
         itemDaTela = itRea;

         HashMap<Integer, Estimulo> escolhas = itemDaTela.getEscolhas();

         setEstimulo(0, itemDaTela.getModelo(), true);

         if (itemDaTela.getSubFase().getEntrModEsc() > 0 && escolhas != null && !itemDaTela.getSubFase().isRespObs()) {
            try {
               sleep(itemDaTela.getSubFase().getEntrModEsc() * 1000);
            } catch (InterruptedException e) {
            }
         }

         if (escolhas != null) {
            addActionListener(0, new ActionModelo());

            for (Iterator<Integer> it = escolhas.keySet().iterator(); it.hasNext();) {
               posicao = it.next();
               es = escolhas.get(posicao);

               if (es != null) {
                  if (es.getCodigo() == itemDaTela.getEscCorreto().getCodigo()) {
                     addActionListener(posicao, new ActionAcertou());
                  }
                  else {
                     addActionListener(posicao, new ActionErrou());
                  }

                  setEstimulo(posicao, es, !itemDaTela.getSubFase().isRespObs());
               }
            }

         }
         else {

            liberaEscolha = true;

            addActionListener(0, new ActionAcertou());
         }

         tempo = System.currentTimeMillis();

         requestFocus();

         liberaAtalho = true;
      }
   }
   
   public void acabou(){
      acabou = true;
   }
}
