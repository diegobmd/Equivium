package telas.realiza.anagrama;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

import lib.beans.Anagrama;
import lib.beans.Estimulo;
import lib.beans.ItemRealizaAnagrama;
import lib.beans.Participante;
import lib.beans.RealizaAnagrama;
import lib.beans.SessaoAnagrama;
import media.XAudio;
import telas.realiza.DialogRealiza;
import util.XPermutacao;

public class FrmExecutaSesAnagrama extends DialogRealiza {

   private final PanelAnagrama            panelAnagram = new PanelAnagrama();

   private ArrayList<Anagrama>            anagramas    = new ArrayList<Anagrama>();
   private ArrayList<ItemRealizaAnagrama> itensExecutar;
   private RealizaAnagrama                realiza;
   private ItemRealizaAnagrama            itemDaTela;

   private long                           tempo;
   private int                            indexItens   = 0;
   private int                            instan       = 0;

   private int                            ordem        = 0;
   private int                            tentativa    = 0;

   private ArrayList<IntanciaAnagramas>   instancias;

   public FrmExecutaSesAnagrama(Participante participante, ArrayList<SessaoAnagrama> sessoes, ArrayList<Anagrama> anagra) {
      super();
      setLayout(new BorderLayout());

      addKeyListener(new TeclasAtalho());

      add(panelAnagram);

      if (sessoes != null) {
         for (SessaoAnagrama sessao : sessoes) {
            sessao.select();
            for (Anagrama f : sessao.getAnagramas()) {
               f.select();

               anagramas.add(f);
            }
         }
      }

      if (anagra != null) {
         for (Anagrama a : anagra) {
            a.select();
            anagramas.add(a);
         }
      }

      if (anagramas.size() == 0) {
         return;
      }

      realiza = new RealizaAnagrama(participante);
      realiza.insert();

      geraExecucao(anagramas);

      indexItens = 0;

      proximo();
   }

   private void geraExecucao(ArrayList<Anagrama> anagra) {
      tentativa++;

      instancias = new ArrayList<IntanciaAnagramas>();

      int indice = 0;

      ItemRealizaAnagrama item;
      ArrayList<ItemRealizaAnagrama> itens = new ArrayList<ItemRealizaAnagrama>();
      XPermutacao<ItemRealizaAnagrama> permutacao;
      itensExecutar = new ArrayList<ItemRealizaAnagrama>();

      for (Anagrama sub : anagra) {
         for (int y = 0; y < sub.getMinimo(); y++) {
            IntanciaAnagramas inst = new IntanciaAnagramas();

            inst.setAnagrama(sub);

            indice = 0;
            for (int i = 0; i < sub.getEstimulos().size(); i++) {
               item = new ItemRealizaAnagrama();

               item.setRealiza(realiza);
               item.setAnagrama(sub);

               item.setIndice(indice);
               item.setInstancia(instan);

               item.setTentativa(tentativa);

               itens.add(item);
               inst.addItem(item);

               indice++;
            }

            instancias.add(inst);

            instan++;
         }
      }

      if (itens.size() > 0) {
         permutacao = new XPermutacao<ItemRealizaAnagrama>(itens, 1);

         for (ArrayList<ItemRealizaAnagrama> k : permutacao.getAll()) {
            itensExecutar.add(k.get(0));
         }
      }
   }

   private void proximo() {

      if (indexItens >= itensExecutar.size()) {
         indexItens = 0;

         if (verificaMetas()) {
            dispose();
         }
      }

      itemDaTela = itensExecutar.get(indexItens);

      setTela(itemDaTela);

      tempo = System.currentTimeMillis();

      indexItens++;
   }

   private boolean verificaMetas() {

      anagramas = new ArrayList<Anagrama>();

      for (IntanciaAnagramas a : instancias) {
         if (!a.atingiuMeta()) {

            anagramas.add(a.getAnagrama());
         }
      }

      if (anagramas.size() > 0) {

         geraExecucao(anagramas);

         return false;
      }
      return true;
   }

   public void setTela(ItemRealizaAnagrama itRea) {

      Estimulo estimulo = itRea.getEstimulo();
      estimulo.select();

      panelAnagram.limparCubos();
      panelAnagram.limparModelo();

      switch (estimulo.getTipo()) {
         case Estimulo.DESENHO:
            if (!new File(estimulo.getFigura()).exists()) {
               panelAnagram.setModeloPalavra("ERROR");
            }
            else {
               panelAnagram.setModeloImagem(new ImageIcon(estimulo.getFigura()));
            }
            break;
         case Estimulo.PALAVRA:
            panelAnagram.setModeloPalavra(estimulo.getPalavra());
            break;
         case Estimulo.COR:
//            panelAnagram.setModeloPalavra(estimulo.getPalavra());
            System.out.println("");
            break;            
         case Estimulo.AUDIO:
            try {
               new XAudio().play(itemDaTela.getEstimulo().getAudio());
            } catch (Exception e) {
               e.printStackTrace();
            }
            break;
         default:
            break;
      }

      panelAnagram.setEncaixes(estimulo.getPalavra().split("[+]").length);
      panelAnagram.addCubos(estimulo.getPalavra().split("[+]"));

      ArrayList<String> extras = new ArrayList<String>();

      for (Estimulo es : itRea.getAnagrama().getEstExtras()) {
         for (String s : es.getPalavra().split("[+]")) {
            if (!extras.contains(s)) {
               extras.add(s);
            }
         }
      }

      Collections.shuffle(extras);

      for (int i = 0; i < extras.size() && i < itRea.getAnagrama().getQtdExtra(); i++) {
         panelAnagram.addCubos(extras.get(i));
      }

      repaint();
   }

   public void passa() {
      ordem++;

      itemDaTela.setResultado(panelAnagram.getCubosEncaixados());
      itemDaTela.setTempo(System.currentTimeMillis() - tempo);
      itemDaTela.setOrdemExec(ordem);

      itemDaTela.insert();

      if (itemDaTela.acertou()) {

         try {
            if(itemDaTela.getAnagrama().getReforco().getCodigo() > 0 && new File(itemDaTela.getAnagrama().getReforco().getAudio()).exists()){
               new XAudio().play(itemDaTela.getAnagrama().getReforco().getAudio());
            }
         } catch (Exception e) {
            e.printStackTrace();
//            XTrataException.printStackTrace(e);
         }
      }

      proximo();
   }

   public void errou() {
      itemDaTela.setResultado(panelAnagram.getCubosEncaixados());
      itemDaTela.setTempo(System.currentTimeMillis() - tempo);
      itemDaTela.insert();

      proximo();
   }

   private class TeclasAtalho implements KeyListener {
      public void keyPressed(KeyEvent e) {
         switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
               passa();
               break;

            case KeyEvent.VK_3:
               errou();
               break;

            default:
               break;
         }
      }

      public void keyReleased(KeyEvent e) {
      }

      public void keyTyped(KeyEvent e) {
      }
   }
}
