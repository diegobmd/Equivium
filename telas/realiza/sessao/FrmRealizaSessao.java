package telas.realiza.sessao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import lib.beans.Fase;
import lib.beans.Participante;
import lib.beans.Sessao;
import lib.beans.SubFase;
import lib.campos.CFases;
import lib.campos.CParticipantes;
import lib.campos.CSessoes;
import lib.campos.CSubFases;
import lib.telas.FrmRelatorios;
import swing.XPanel;
import swing.XScrollPane;
import swing.XTableCampo;

public class FrmRealizaSessao extends FrmRelatorios {

   private final XPanel         panel        = new XPanel();
   private final XPanel         panel2       = new XPanel();
   private final XScrollPane    scrollPane   = new XScrollPane(panel);

   private final XTableCampo    taSessoes    = new XTableCampo(2, 2, 70, 8, "Sessões", new CSessoes(0, 0, 0, ""));
   private final XTableCampo    taFases      = new XTableCampo(8, 2, 70, 8, "Fases", new CFases(0, 0, 0, ""));
   private final XTableCampo    taSubFases   = new XTableCampo(14, 2, 70, 8, "Tentativas", new CSubFases(0, 0, 0, ""));

   private final CParticipantes participante = new CParticipantes(1, 15, 15, "Participante");

   public FrmRealizaSessao() {
      super("Realiza sessão");
      initComponentes();
   }

   public void initComponentes() {

      panel.add(participante);
      panel.add(taSessoes);
      panel.add(taFases);
      panel.add(taSubFases);

      panel.setPreferredSize(new Dimension(panel.getWidth(), 600));

      panel2.setLayout(new BorderLayout());

      panel2.add(scrollPane);

      participante.setObrigatorio(true);

      addPainel("Principal", panel2);

      controlView.add(CAMPO_NORMAL, participante);
      controlView.add(CAMPO_NORMAL, taSessoes);
      controlView.add(CAMPO_NORMAL, taFases);
      controlView.add(CAMPO_NORMAL, taSubFases);
   }

   public void doIniciar() {

      if (!controlView.validaCampos(CAMPO_NORMAL)) {
         return;
      }

      FrmExecutaSessao executaSessao = new FrmExecutaSessao(getParticipante(), getSessoes(), getFases(), getSubFases());

      executaSessao.setFullScreenMode();
      executaSessao.dispose2();
   }

   private Participante getParticipante() {
      Participante parti = new Participante();

      parti.setCodigo(participante.getNumber());

      return parti;
   }

   private ArrayList<Sessao> getSessoes() {
      if (taSessoes.getRowCount() > 0) {
         Vector<Object> vAux;
         Sessao s;
         ArrayList<Sessao> sessoes = new ArrayList<Sessao>();

         for (int i = 0; i < taSessoes.getRowCount(); i++) {
            vAux = taSessoes.getRow(i);

            s = new Sessao();
            s.setCodigo(Integer.parseInt(vAux.get(0).toString()));
            sessoes.add(s);
         }

         return sessoes;
      }

      return null;
   }

   private ArrayList<Fase> getFases() {
      if (taFases.getRowCount() > 0) {
         Vector<Object> vAux;
         Fase s;
         ArrayList<Fase> fases = new ArrayList<Fase>();

         for (int i = 0; i < taFases.getRowCount(); i++) {
            vAux = taFases.getRow(i);

            s = new Fase();
            s.setCodigo(Integer.parseInt(vAux.get(0).toString()));
            fases.add(s);
         }

         return fases;
      }

      return null;
   }

   private ArrayList<SubFase> getSubFases() {
      if (taSubFases.getRowCount() > 0) {
         Vector<Object> vAux;
         SubFase s;
         ArrayList<SubFase> subFases = new ArrayList<SubFase>();

         for (int i = 0; i < taSubFases.getRowCount(); i++) {
            vAux = taSubFases.getRow(i);

            s = new SubFase();
            s.setCodigo(Integer.parseInt(vAux.get(0).toString()));
            subFases.add(s);
         }

         return subFases;
      }

      return null;
   }

}
