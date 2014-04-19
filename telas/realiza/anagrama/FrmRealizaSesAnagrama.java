package telas.realiza.anagrama;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import lib.beans.Anagrama;
import lib.beans.Participante;
import lib.beans.SessaoAnagrama;
import lib.campos.CAnagramas;
import lib.campos.CParticipantes;
import lib.campos.CSessoesAnagrama;
import lib.telas.FrmRelatorios;
import swing.XPanel;
import swing.XScrollPane;
import swing.XTableCampo;

public class FrmRealizaSesAnagrama extends FrmRelatorios {

   private final XPanel         panel        = new XPanel();
   private final XPanel         panel2       = new XPanel();
   private final XScrollPane    scrollPane   = new XScrollPane(panel);

   private final XTableCampo    taSessoes    = new XTableCampo(2, 2, 70, 8, "Sessões Anagramas", new CSessoesAnagrama(0, 0, 0, ""));
   private final XTableCampo    taFases      = new XTableCampo(8, 2, 70, 8, "Anagramas", new CAnagramas(0, 0, 0, ""));

   private final CParticipantes participante = new CParticipantes(1, 15, 15, "Participante");

   public FrmRealizaSesAnagrama() {
      super("Realiza sessão de anagramas");
      
      initComponentes();
   }

   public void initComponentes() {

      panel.add(participante);
      panel.add(taSessoes);
      panel.add(taFases);

      panel.setPreferredSize(new Dimension(panel.getWidth(), 400));

      panel2.setLayout(new BorderLayout());

      panel2.add(scrollPane);

      participante.setObrigatorio(true);

      addPainel("Principal", panel2);

      controlView.add(CAMPO_NORMAL, participante);
      controlView.add(CAMPO_NORMAL, taSessoes);
      controlView.add(CAMPO_NORMAL, taFases);
   }

   public void doIniciar() {

      if (!controlView.validaCampos(CAMPO_NORMAL)) {
         return;
      }

      FrmExecutaSesAnagrama executaSessao = new FrmExecutaSesAnagrama(getParticipante(), getSessoes(), getAnagramas());

      executaSessao.setFullScreenMode();
      executaSessao.dispose2();
   }

   private Participante getParticipante() {
      Participante parti = new Participante();

      parti.setCodigo(participante.getNumber());

      return parti;
   }

   private ArrayList<SessaoAnagrama> getSessoes() {
      if (taSessoes.getRowCount() > 0) {
         Vector<Object> vAux;
         SessaoAnagrama s;
         ArrayList<SessaoAnagrama> sessoes = new ArrayList<SessaoAnagrama>();

         for (int i = 0; i < taSessoes.getRowCount(); i++) {
            vAux = taSessoes.getRow(i);

            s = new SessaoAnagrama();
            s.setCodigo(Integer.parseInt(vAux.get(0).toString()));
            sessoes.add(s);
         }

         return sessoes;
      }

      return null;
   }

   private ArrayList<Anagrama> getAnagramas() {
      if (taFases.getRowCount() > 0) {
         Vector<Object> vAux;
         Anagrama s;
         ArrayList<Anagrama> fases = new ArrayList<Anagrama>();

         for (int i = 0; i < taFases.getRowCount(); i++) {
            vAux = taFases.getRow(i);

            s = new Anagrama();
            s.setCodigo(Integer.parseInt(vAux.get(0).toString()));
            fases.add(s);
         }

         return fases;
      }

      return null;
   }

}
