package telas.cadastros;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Fase;
import lib.beans.Reforco;
import lib.beans.Sessao;
import lib.campos.CFases;
import lib.campos.CReforcos;
import lib.campos.CSessoes;
import lib.telas.FrmCadastros;
import sql.querys.XSelect;
import swing.XComboBox;
import swing.XPanel;
import swing.XTableCampo;
import swing.XTextField;
import util.XTrataException;

public class FrmCadSessao extends FrmCadastros {

   private final XPanel             panel    = new XPanel();
   private final CSessoes           codigo   = new CSessoes(1, 20, 15, "Código");
   private final XTextField         nome     = new XTextField(2, 20, 50, "Nome");
   private final XTextField         sigla    = new XTextField(3, 20, 4, "Sigla");
   private final CReforcos          aofim    = new CReforcos(4, 20, 15, "Reforço ao final");
   private final XComboBox<Integer> grade    = new XComboBox<Integer>(5, 20, 10, "Grade");
   private final XTableCampo        tableAdd = new XTableCampo(6, 2, 70, 8, "Fases", new CFases(0, 0, 0, "") {

                                                public String getWhere() {
                                                   return " SUB_GRADE = ? ";
                                                }

                                                public ArrayList<Object> getWhereParm() {
                                                   ArrayList<Object> x = new ArrayList<Object>();
                                                   x.add(grade.getSelectedItem());
                                                   return x;
                                                }

                                                public XSelect getSelect() {
                                                   XSelect s = super.getSelect();

                                                   s.addCampo("max(sub_grade) as grade");
                                                   s.addInnerJoin("Fases_Sub_Fases", "FAS_CODIGO = Sub_Fas_Fase");
                                                   s.addInnerJoin("Sub_Fases", "Sub_CODIGO = Sub_Fas_Sub_Fase");

                                                   ArrayList<String> gru = new ArrayList<String>();

                                                   gru.add("Fas_Codigo");
                                                   gru.add("Fas_Sigla");
                                                   gru.add("Fas_Nome");

                                                   s.setGroupBy(gru);

                                                   return s;
                                                }

                                             }) {
                                                public Vector<Object> getLinhaAdd() {
                                                   if (grade.getSelectedIndex() < 0) {
                                                      JOptionPane.showMessageDialog(null, "Deve-se ser selecionado uma grade", "Fases", JOptionPane.ERROR_MESSAGE);
                                                      return null;
                                                   }

                                                   return super.getLinhaAdd();
                                                }
                                             };

   private final Sessao             sessao   = new Sessao();

   public FrmCadSessao() {
      super("Cadastro de Sessões");

      initComponentes();
   }

   public void initComponentes() {
      panel.add(codigo);
      panel.add(nome);
      panel.add(sigla);
      panel.add(tableAdd);
      panel.add(aofim);
      panel.add(grade);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      sigla.setObrigatorio(true);
      tableAdd.setObrigatorio(true);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, sigla);
      controlView.add(CAMPO_NORMAL, aofim);
      controlView.add(CAMPO_NORMAL, tableAdd);
      controlView.add(CAMPO_NORMAL, grade);

      grade.addItem(4);
      grade.addItem(6);

      grade.addItemListener(change);

      setBean(sessao);

      nome.setTamanho(50);
      sigla.setTamanho(4);

      addPainel("Principal", panel);

      codigo.addInputVerifier(new VerifierCodigo());

      cancelarClick();
   }

   public boolean setDadosBean() {
      try {
         sessao.reset();

         sessao.setCodigo(codigo.getNumber());
         sessao.setNome(nome.getText());
         sessao.setSigla(sigla.getText());
         Reforco reforco = new Reforco();
         reforco.setCodigo(aofim.getNumber());
         sessao.setAoFim(reforco);

         Fase fases;
         Vector<Object> data;
         ArrayList<Fase> list = new ArrayList<Fase>();

         for (int i = 0; i < tableAdd.length(); i++) {
            fases = new Fase();
            data = tableAdd.getRow(i);

            fases.setCodigo(Integer.parseInt(data.get(0).toString()));
            fases.setNome(data.get(1).toString());

            list.add(fases);
         }

         sessao.setFases(list);

         return true;

      } catch (Exception e) {
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private ItemListener change = new ItemListener() {

                                  public void itemStateChanged(ItemEvent e) {

                                     if (grade.getSelectedIndex() >= 0) {
                                        ArrayList<Integer> exc = new ArrayList<Integer>();

                                        for (int i = 0; i < tableAdd.getRowCount(); i++) {
                                           if (!tableAdd.getRow(i).get(2).equals(grade.getSelectedItem())) {
                                              exc.add(i);
                                           }
                                        }
                                        int m = 0;
                                        for (Integer i : exc) {
                                           tableAdd.removeRow(i - m);
                                           m++;
                                        }
                                     }

                                  }
                               };

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         sessao.reset();

         sessao.setCodigo(codigo.getNumber());

         if (sessao.select()) {

            nome.setText(sessao.getNome());
            sigla.setText(sessao.getSigla());
            aofim.setNumber(sessao.getAoFim().getCodigo());
            grade.setSelectedItem(sessao.getGrade());

            ArrayList<Fase> list = sessao.getFases();

            for (Fase fas : list) {
               fas.select();

               tableAdd.addRow(String.valueOf(fas.getCodigo()), fas.getSigla(), fas.getNome(), String.valueOf(fas.getGrade()));
            }

            if (OPE == ALT) {
               controlView.setControl(CAMPO_NORMAL, true, false);

               grade.setEnabled(false);
            }
            else {
               controlView.setControl(CAMPO_NORMAL, false, false);
            }

            return true;
         }

         JOptionPane.showMessageDialog(null, "Registro não encontrado", "Sessões", JOptionPane.ERROR_MESSAGE);

         controlView.setControl(CAMPO_NORMAL, false, true);
         codigo.requestFocus();

         return false;

      }

   }
}
