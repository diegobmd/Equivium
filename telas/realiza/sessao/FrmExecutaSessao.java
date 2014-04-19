package telas.realiza.sessao;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import lib.beans.Estimulo;
import lib.beans.Fase;
import lib.beans.ItemRealizaSessao;
import lib.beans.Participante;
import lib.beans.RealizaSessao;
import lib.beans.Reforco;
import lib.beans.Sessao;
import lib.beans.SubFase;
import media.XAudio;
import telas.realiza.DialogRealiza;
import util.XDistribuicao;
import util.XPermutacao;
import util.XTrataException;

public class FrmExecutaSessao extends DialogRealiza {

   private final PanelRealiza                        panel        = new PanelRealiza(this);
   private ItemRealizaSessao                         itemDaTela   = null;

   private ArrayList<SubFase>                        subFases;
   private ArrayList<ItemRealizaSessao>              itensExecutar;

   private final TreeMap<Integer, InstanciasSubFase> instSubFases = new TreeMap<Integer, InstanciasSubFase>();
   private int                                       instancia    = 0;
   private final RealizaSessao                       realiza;

   private int                                       indexItens   = 0;
   private int                                       ordemExec    = 0;

   private Reforco                                   reforco      = null;

   public FrmExecutaSessao(Participante participante, ArrayList<Sessao> sessoes, ArrayList<Fase> fases, ArrayList<SubFase> sfases) {
      super();
      setLayout(new BorderLayout());

      add(panel);

      realiza = new RealizaSessao(participante);
      realiza.insert();

      subFases = new ArrayList<SubFase>();

      if (sessoes != null) {
         for (Sessao sessao : sessoes) {
            sessao.select();

            if (sessao.getAoFim() != null && sessao.getAoFim().getCodigo() > 0) {
               reforco = sessao.getAoFim();
            }

            for (Fase f : sessao.getFases()) {
               f.select();
               for (SubFase s : f.getSubFases()) {
                  s.setInstancia(instancia);
                  s.setAgrupador(0);
                  instancia++;
                  subFases.add(s);
               }
            }
         }
      }

      if (fases != null) {
         for (Fase f : fases) {
            f.select();
            for (SubFase s : f.getSubFases()) {
               s.setInstancia(instancia);
               s.setAgrupador(0);
               instancia++;
               subFases.add(s);
            }
         }
      }

      if (sfases != null) {
         for (SubFase s : sfases) {
            s.setInstancia(instancia);
            s.setAgrupador(0);
            instancia++;
            subFases.add(s);
         }
      }

      if (subFases.size() == 0) {
         return;
      }

      geraExecucao(this.subFases);

      indexItens = 0;

      proximo();
   }

   private void geraExecucao(ArrayList<SubFase> subFases) {

      ArrayList<ArrayList<ItemRealizaSessao>> itSub = new ArrayList<ArrayList<ItemRealizaSessao>>();

      for (SubFase sub : subFases) {
         sub.select();
         if (sub.getEstiVez() == 0) {

            itSub.add(geraExecucaoSemEscolhas(sub));

         }
         else {

            itSub.add(geraExecucaoComEscolhas(sub));

         }
      }

      XDistribuicao<ItemRealizaSessao> distribuicao = new XDistribuicao<ItemRealizaSessao>(itSub);

      itensExecutar = distribuicao.getAll();
   }

   private ArrayList<ItemRealizaSessao> geraExecucaoSemEscolhas(SubFase sub) {

      ArrayList<Estimulo> modelos = new ArrayList<Estimulo>();
      ArrayList<Estimulo> modAux;
      XPermutacao<Estimulo> permModelos;
      ItemRealizaSessao itemAdic;
      ArrayList<ItemRealizaSessao> itens = new ArrayList<ItemRealizaSessao>();

      // percorre todas as subFases
      //sub.select();

      // serve para verificar se atingiu a meta na execução
      sub.setAgrupador(sub.getAgrupador() + 1);
      instSubFases.put(sub.getInstancia(), new InstanciasSubFase(sub));

      for (Iterator<Estimulo> it = sub.getModeloEscolha().keySet().iterator(); it.hasNext();) {
         modelos.add(it.next());
      }
      // System.out.println(modelos.size());
      permModelos = new XPermutacao<Estimulo>(modelos, 1);

      permModelos.setCircular(true);

      for (int i = 0; i < sub.getQuantidade(); i++) {

         itemAdic = new ItemRealizaSessao(realiza, sub);

         itemAdic.setAcada(sub.getAcada());
         itemAdic.setAgrupador(sub.getAgrupador());
         itemAdic.setTentativa((i / sub.getModeloEscolha().size()) + 1);

         modAux = permModelos.next();

         itemAdic.setModelo(modAux.get(0));
         itemAdic.setEscCorreto(modAux.get(0));
         itemAdic.setEscolhas(null);

         itens.add(itemAdic);
      }

      return itens;
   }

   private ArrayList<ItemRealizaSessao> geraExecucaoComEscolhas(SubFase sub) {

      ArrayList<Integer> grade4 = new ArrayList<Integer>();
      ArrayList<Integer> grade6 = new ArrayList<Integer>();

      grade4.add(0);
      grade4.add(1);
      grade4.add(2);
      grade4.add(3);

      grade6.add(0);
      grade6.add(1);
      grade6.add(2);
      grade6.add(3);
      grade6.add(4);
      grade6.add(5);

      XPermutacao<Integer> perm4 = new XPermutacao<Integer>(grade4);
      XPermutacao<Integer> perm6 = new XPermutacao<Integer>(grade6);

      perm4.setCircular(true);
      perm6.setCircular(true);

      perm4.shuffle();
      perm6.shuffle();

      ArrayList<ItemRealizaSessao> itens = new ArrayList<ItemRealizaSessao>();

      ArrayList<Estimulo[]> modelos = new ArrayList<Estimulo[]>();
      ArrayList<Estimulo> escolhas = new ArrayList<Estimulo>();
      XPermutacao<Estimulo[]> permModelos;
      XPermutacao<Estimulo> permEscolhas;
      ItemRealizaSessao itemAdic;
      Estimulo mo;
      Estimulo es;
      Estimulo[] modAux;

      // percorre todas as subFases
      //sub.select();

      // serve para verificar se atingiu a meta na execução
      sub.setAgrupador(sub.getAgrupador() + 1);
      instSubFases.put(sub.getInstancia(), new InstanciasSubFase(sub));

      // guarda o modelo e o correto para aplicar a permutação
      for (Iterator<Estimulo> it = sub.getModeloEscolha().keySet().iterator(); it.hasNext();) {
         mo = it.next();
         es = sub.getModeloEscolha().get(mo);

         modelos.add(new Estimulo[] { mo, es });

         escolhas.add(es);
      }

      permModelos = new XPermutacao<Estimulo[]>(modelos, 1);
      permEscolhas = new XPermutacao<Estimulo>(escolhas, sub.getEstiVez());

      permModelos.setCircular(true);
      permEscolhas.setCircular(true);

      permEscolhas.shuffle();

      int posAnt = -1;
      ArrayList<Integer> p;

      for (int i = 0; i < sub.getQuantidade(); i++) {

         itemAdic = new ItemRealizaSessao(realiza, sub);

         itemAdic.setAcada(sub.getAcada());
         itemAdic.setAgrupador(sub.getAgrupador());
         itemAdic.setTentativa((i / sub.getModeloEscolha().size()) + 1);

         modAux = permModelos.next().get(0);

         itemAdic.setModelo(modAux[0]);
         itemAdic.setEscCorreto(modAux[1]);

         escolhas = permEscolhas.next();

         while (!inEscolhas(modAux[1].getCodigo(), escolhas)) {
            escolhas = permEscolhas.next();
         }

         for (int k = escolhas.size(); k < sub.getGrade(); k++) {
            escolhas.add(null);
         }

         if (sub.getGrade() == 4) {
            p = perm4.next();

            while (getPosicaoCorreto(p, escolhas, modAux[1].getCodigo()) == posAnt) {
               p = perm4.next();
            }

            posAnt = getPosicaoCorreto(p, escolhas, modAux[1].getCodigo());

            itemAdic.addEscolha(1, escolhas.get(p.get(0)));
            itemAdic.addEscolha(3, escolhas.get(p.get(1)));
            itemAdic.addEscolha(4, escolhas.get(p.get(2)));
            itemAdic.addEscolha(6, escolhas.get(p.get(3)));
         }
         else {
            p = perm6.next();

            while (getPosicaoCorreto(p, escolhas, modAux[1].getCodigo()) == posAnt) {
               p = perm6.next();
            }

            posAnt = getPosicaoCorreto(p, escolhas, modAux[1].getCodigo());

            itemAdic.addEscolha(1, escolhas.get(p.get(0)));
            itemAdic.addEscolha(2, escolhas.get(p.get(1)));
            itemAdic.addEscolha(3, escolhas.get(p.get(2)));
            itemAdic.addEscolha(4, escolhas.get(p.get(3)));
            itemAdic.addEscolha(5, escolhas.get(p.get(4)));
            itemAdic.addEscolha(6, escolhas.get(p.get(5)));
         }

         itens.add(itemAdic);
      }

      return itens;
   }

   private int getPosicaoCorreto(ArrayList<Integer> posi, ArrayList<Estimulo> escolhas, int codCorr) {

      for (int i = 0; i < escolhas.size(); i++) {
         if (escolhas.get(i) != null && escolhas.get(i).getCodigo() == codCorr) {
            return posi.get(i);
         }
      }

      return -1;
   }

   private boolean inEscolhas(int cod, ArrayList<Estimulo> escolhas) {
      for (Estimulo e : escolhas) {
         if (e != null && e.getCodigo() == cod) {
            return true;
         }
      }
      return false;
   }

   protected void proximo() {

      if (indexItens >= itensExecutar.size()) {
         indexItens = 0;

         if (verificaMetas()) {
            try {
               if (reforco != null) {
                  reforco.select();
                  if (new File(reforco.getAudio()).exists()) {
                     new XAudio().play(reforco.getAudio());
                  }
               }
            } catch (Exception e) {
               XTrataException.printStackTrace(e);
            }

            panel.acabou();
            dispose();
         }
      }

      itemDaTela = itensExecutar.get(indexItens);
      itemDaTela.setOrdemExec(ordemExec);

      panel.setTela(itemDaTela);

      indexItens++;
      ordemExec++;

   }

   private boolean verificaMetas() {
      ArrayList<SubFase> subFases = new ArrayList<SubFase>();

      for (Iterator<Integer> it = instSubFases.keySet().iterator(); it.hasNext();) {
         InstanciasSubFase aux = instSubFases.get(it.next());
         if (!aux.atingiuMeta()) {
            subFases.add(aux.getSubFase());
         }
      }

      if (subFases.size() > 0) {
         geraExecucao(subFases);
         return false;
      }

      return true;
   }

   protected void addAcerto(int i) {
      instSubFases.get(i).addAcerto();
   }

}
