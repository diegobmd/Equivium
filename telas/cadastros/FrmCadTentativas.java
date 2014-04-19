package telas.cadastros;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Estimulo;
import lib.beans.Reforco;
import lib.beans.SubFase;
import lib.campos.CEstimulos;
import lib.campos.CReforcos;
import lib.campos.CSubFases;
import lib.telas.FrmCadastros;
import swing.XButton;
import swing.XCheckBox;
import swing.XComboBox;
import swing.XDecimalField;
import swing.XDialog;
import swing.XNumberField;
import swing.XPanel;
import swing.XTableAdd;
import swing.XTextField;
import util.XStringUtil;
import util.XTrataException;

public class FrmCadTentativas extends FrmCadastros {

   // aba1
   private final CSubFases          codigo     = new CSubFases(1, 20, 15, "Código");
   private final XTextField         nome       = new XTextField(2, 20, 50, "Nome");
   private final XTextField         sigla      = new XTextField(3, 20, 5, "Sigla");
   private final CReforcos          acada      = new CReforcos(4, 20, 15, "Reforço a cada acerto");
   private final XNumberField       minimo     = new XNumberField(5, 20, 10, "Mínimo de tentativas");
   private final XDecimalField      acerto     = new XDecimalField(6, 20, 10, "% de acerto");
   private final XNumberField       entrModEsc = new XNumberField(7, 20, 10, "Intervalo Modelo / escolha");
   private final XCheckBox          respObs    = new XCheckBox(8, 20, "Resposta de observação");

   private final XPanel             panel      = new XPanel();
   private final XPanel             panel2     = new XPanel();

   // aba2
   private final XComboBox<Integer> estiVez    = new XComboBox<Integer>(1, 20, 10, "Estímulos por vez");
   private final XComboBox<Integer> grade      = new XComboBox<Integer>(2, 20, 10, "Grade");
   private final XTableAdd          tableAdd   = new XTableAdd(3, 1, 77, 11, "Estímulos") {
                                                  public Vector<Object> getLinhaAdd() {

                                                     if (tableAdd.length() == 6) {
                                                        return null;
                                                     }

                                                     return new DialogEscolha().getLinha();
                                                  }
                                               };

   private final SubFase            subFases   = new SubFase();

   public FrmCadTentativas() {
      super("Cadastro de tentativas");

      initComponentes();
   }

   public void initComponentes() {

      panel.add(codigo);
      panel.add(nome);
      panel.add(acada);
      panel.add(minimo);
      panel.add(acerto);
      panel.add(entrModEsc);
      panel.add(sigla);
      panel.add(respObs);

      panel2.add(estiVez);
      panel2.add(grade);
      panel2.add(tableAdd);

      nome.setTamanho(50);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      // acada.setObrigatorio(true);
      // aofim.setObrigatorio(true);
      minimo.setObrigatorio(true);
      // acerto.setObrigatorio(true);
      entrModEsc.setObrigatorio(true);
      estiVez.setObrigatorio(true);
      grade.setObrigatorio(true);
      tableAdd.setObrigatorio(true);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, sigla);
      controlView.add(CAMPO_NORMAL, acada);
      controlView.add(CAMPO_NORMAL, minimo);
      controlView.add(CAMPO_NORMAL, acerto);
      controlView.add(CAMPO_NORMAL, entrModEsc);
      controlView.add(CAMPO_NORMAL, estiVez);
      controlView.add(CAMPO_NORMAL, tableAdd);
      controlView.add(CAMPO_NORMAL, grade);
      controlView.add(CAMPO_NORMAL, respObs);

      addPainel("Principal", panel);
      addPainel("Configurações", panel2);

      sigla.setTamanho(4);

      tableAdd.addColuna("COD");
      tableAdd.addColuna("MODELO");
      tableAdd.addColuna("TIPO");
      tableAdd.addColuna("COD");
      tableAdd.addColuna("ESCOLHA");
      tableAdd.addColuna("TIPO");

      tableAdd.setTamanho(0, 0);
      tableAdd.setTamanho(1, 22);
      tableAdd.setTamanho(2, 10);

      tableAdd.setTamanho(3, 0);
      tableAdd.setTamanho(4, 22);
      tableAdd.setTamanho(5, 10);

      acerto.addInputVerifier(new ValidPAcerto());

      estiVez.addItem(0);
      estiVez.addItem(1);
      estiVez.addItem(2);
      estiVez.addItem(3);
      estiVez.addItem(4);
      estiVez.addItem(5);
      estiVez.addItem(6);

      grade.addItem(4);
      grade.addItem(6);

      tableAdd.setMaxRows(6);

      codigo.addInputVerifier(new VerifierCodigo());

      setBean(subFases);

      cancelarClick();
   }

   private class DialogEscolha {

      private XDialog                 dialog  = new XDialog(420, 200, "Selecione o estimulo modelo/escolha");
      private final CEstimulos        modelo  = new CEstimulos(2, 15, 15, "Estímulo modelo");
      private final CEstimulos        escolha = new CEstimulos(3, 15, 15, "Estímulo escolha");

      private final XComboBox<String> boxMod  = new XComboBox<String>(2, 31, 15, null);
      private final XComboBox<String> boxEsc  = new XComboBox<String>(3, 31, 15, null);

      private final XButton           add     = new XButton(5, 7, 15, "Adicionar");
      private final XButton           can     = new XButton(5, 31, 15, "Cancelar");

      Estimulo                        mod     = new Estimulo();
      Estimulo                        esc     = new Estimulo();

      private Vector<Object>          retorno = null;

      public DialogEscolha() {
         initComponentes();
      }

      private void initComponentes() {

         dialog.add(modelo);
         dialog.add(escolha);

         dialog.add(boxMod);
         dialog.add(boxEsc);

         dialog.add(add);
         dialog.add(can);

         add.addActionListener(action);
         can.addActionListener(action);

         boxMod.addItem(Estimulo.DSC_DESENHO);
         boxMod.addItem(Estimulo.DSC_PALAVRA);
         boxMod.addItem(Estimulo.DSC_AUDIO);
         boxMod.addItem(Estimulo.DSC_COR);

         boxEsc.addItem(Estimulo.DSC_DESENHO);
         boxEsc.addItem(Estimulo.DSC_PALAVRA);
         boxEsc.addItem(Estimulo.DSC_COR);
         boxEsc.addItem(Estimulo.DSC_NENHUM);

         boxEsc.setSelectedIndex(3);
         escolha.setEnabled(false);

         boxEsc.addItemListener(change);

         modelo.addFocusListener(new AAA());
         escolha.addFocusListener(new BBB());
      }

      private class AAA implements FocusListener {
         int ant = -1;

         public void focusGained(FocusEvent e) {
         }

         public void focusLost(FocusEvent e) {
            if (modelo.getNumber() != ant && modelo.getNumber() > 0) {
               boxMod.removeAllItems();

               Estimulo est = new Estimulo();

               est.setCodigo(modelo.getNumber());

               est.select();

               if (!XStringUtil.isVazio(est.getAudio())) {
                  boxMod.addItem(Estimulo.DSC_AUDIO);
               }
               if (!XStringUtil.isVazio(est.getFigura())) {
                  boxMod.addItem(Estimulo.DSC_DESENHO);
               }
               if (!XStringUtil.isVazio(est.getPalavra())) {
                  boxMod.addItem(Estimulo.DSC_PALAVRA);
               }
               boxMod.addItem(Estimulo.DSC_COR);

               ant = modelo.getNumber();
            }
         }

      }

      private class BBB implements FocusListener {
         int ant = -1;

         public void focusGained(FocusEvent e) {
         }

         public void focusLost(FocusEvent e) {
            if (escolha.getNumber() != ant && escolha.getNumber() > 0) {

               boxEsc.removeAllItems();

               Estimulo est = new Estimulo();

               est.setCodigo(escolha.getNumber());

               est.select();

               if (!XStringUtil.isVazio(est.getFigura())) {
                  boxEsc.addItem(Estimulo.DSC_DESENHO);
               }
               if (!XStringUtil.isVazio(est.getPalavra())) {
                  boxEsc.addItem(Estimulo.DSC_PALAVRA);
               }

               boxEsc.addItem(Estimulo.DSC_COR);
               boxEsc.addItem(Estimulo.DSC_NENHUM);

               ant = escolha.getNumber();
            }
         }

      }

      public Vector<Object> getLinha() {

         dialog.setVisible(true);

         return retorno;
      }

      private ItemListener change = new ItemListener() {

                                     public void itemStateChanged(ItemEvent e) {

                                        if (boxEsc.getSelectedIndex() >= 0 && boxEsc.getSelectedItem().toString().equals(Estimulo.DSC_NENHUM)) {
                                           escolha.setEnabled(false);
                                        }
                                        else {
                                           escolha.setEnabled(true);
                                        }
                                     }
                                  };

      private Action       action = new AbstractAction() {

                                     public void actionPerformed(ActionEvent e) {
                                        if (e.getSource().equals(add)) {

                                           if (modelo.getNumber() <= 0) {
                                              JOptionPane.showMessageDialog(dialog, "Campo modelo é obrigatório!!", "Erro", JOptionPane.ERROR_MESSAGE);
                                              modelo.requestFocus();
                                              return;
                                           }

                                           if (escolha.getNumber() <= 0 && !boxEsc.getSelectedItem().toString().equals(Estimulo.DSC_NENHUM)) {
                                              JOptionPane.showMessageDialog(dialog, "Campo escolha é obrigatório!! \n  Caso não tenha escolha selecione o tipo NENHUM", "Erro", JOptionPane.ERROR_MESSAGE);
                                              escolha.requestFocus();
                                              return;
                                           }

                                           if (boxMod.getSelectedIndex() < 0) {
                                              JOptionPane.showMessageDialog(dialog, "Campo tipo modelo é obrigatório!!", "Erro", JOptionPane.ERROR_MESSAGE);
                                              boxMod.requestFocus();
                                              return;
                                           }

                                           if (boxEsc.getSelectedIndex() < 0) {
                                              JOptionPane.showMessageDialog(dialog, "Campo tipo escolha é obrigatório!!", "Erro", JOptionPane.ERROR_MESSAGE);
                                              boxEsc.requestFocus();
                                              return;
                                           }

                                           retorno = new Vector<Object>();

                                           mod.reset();
                                           esc.reset();

                                           mod.setCodigo(modelo.getNumber());
                                           if (!mod.select()) {
                                              JOptionPane.showMessageDialog(dialog, "Modelo não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
                                              modelo.requestFocus();
                                              return;
                                           }

                                           retorno.add(mod.getCodigo());
                                           retorno.add(mod.getNome());
                                           retorno.add(boxMod.getSelectedItem());

                                           if (escolha.getNumber() <= 0) {
                                              retorno.add(null);
                                              retorno.add(null);
                                           }
                                           else {
                                              esc.setCodigo(escolha.getNumber());
                                              if (!esc.select()) {
                                                 JOptionPane.showMessageDialog(dialog, "Escolha não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
                                                 escolha.requestFocus();
                                                 return;
                                              }
                                              retorno.add(esc.getCodigo());
                                              retorno.add(esc.getNome());

                                           }
                                           retorno.add(boxEsc.getSelectedItem());

                                        }
                                        else {
                                           retorno = null;
                                        }
                                        dialog.setVisible(false);
                                     }

                                  };

   }

   private class ValidPAcerto extends InputVerifier {
      public boolean verify(JComponent input) {

         String t = acerto.getText();

         if (t.trim().length() > 0) {
            t = t.replaceAll("[.]", "");
            t = t.replaceAll("[,]", ".");

            double val = Double.parseDouble(t);

            if (val > 100) {
               acerto.setText("100,00");
            }
            else if (val < 0) {
               acerto.setText("0");
            }
         }

         return true;
      }
   }

   public void okClick() {

      Vector<Object> data;
      for (int i = 0; i < tableAdd.length(); i++) {
         data = tableAdd.getRow(i);

         if (data.get(3) == null && estiVez.getSelectedIndex() > 0) {
            if (JOptionPane.showConfirmDialog(FrmCadTentativas.this, "A quantidade de estmulos por vez será alterado para 0" + "você tem modelos sem escolha na sua lista" + "Deseja continuar?", "Confirmar...", JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION) {
               return;
            }
            estiVez.setSelectedIndex(0);
            break;
         }

         if (data.get(3) != null && estiVez.getSelectedIndex() == 0) {
            if (JOptionPane.showConfirmDialog(FrmCadTentativas.this, "Todas as escolhas serão zeradas devido \n" + "a quantidade de estimulos por vez igual a 0 \n" + "Deseja continuar?", "Confirmar...", JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION) {
               return;
            }
            break;
         }

      }

      if (grade.getSelectedIndex() < 0 || Integer.parseInt(grade.getSelectedItem().toString()) < tableAdd.length()) {
         JOptionPane.showMessageDialog(FrmCadTentativas.this, "A quantidade de estimulos escolhidos deve ser menor ou igual a grade !!!", "Erro", JOptionPane.ERROR_MESSAGE);
         grade.requestFocus();
         return;
      }

      if (estiVez.getSelectedIndex() == -1 || Integer.parseInt(estiVez.getSelectedItem().toString()) > tableAdd.length()) {
         JOptionPane.showMessageDialog(FrmCadTentativas.this, "A quantidade de estimulos escolhidos deve ser maior ou igual a quantidade de estimulos por vez !!!", "Erro", JOptionPane.ERROR_MESSAGE);
         estiVez.requestFocus();
         return;
      }
      super.okClick();
   }

   public boolean setDadosBean() {
      try {

         subFases.reset();

         subFases.setCodigo(codigo.getNumber());
         subFases.setNome(nome.getText());
         subFases.setSigla(sigla.getText());
         subFases.setMinimo(minimo.getNumber());
         subFases.setAcerto(acerto.getNumber());
         subFases.setEntrModEsc(entrModEsc.getNumber());
         
         if (grade.getSelectedIndex() >= 0)
            subFases.setGrade(Integer.parseInt(grade.getSelectedItem().toString()));
         
         if (estiVez.getSelectedIndex() >= 0)
            subFases.setEstiVez(Integer.parseInt(estiVez.getSelectedItem().toString()));

         subFases.setRespObs(respObs.isSelected());

         Reforco reforco = new Reforco();
         reforco.setCodigo(acada.getNumber());
         subFases.setAcada(reforco);

         Estimulo mo;
         Estimulo es;
         Vector<Object> data;
         HashMap<Estimulo, Estimulo> modeloEscolha = new HashMap<Estimulo, Estimulo>();

         for (int i = 0; i < tableAdd.length(); i++) {
            mo = new Estimulo();
            es = new Estimulo();
            data = tableAdd.getRow(i);

            mo.setCodigo(Integer.parseInt(data.get(0).toString()));
            mo.setNome(data.get(1).toString());
            mo.setTipo(data.get(2).toString());

            if (data.get(3) != null) {
               es.setCodigo(Integer.parseInt(data.get(3).toString()));
               es.setNome(data.get(4).toString());
               es.setTipo(data.get(5).toString());
            }
            else {
               es = null;
            }

            modeloEscolha.put(mo, es);
         }

         subFases.setModeloEscolha(modeloEscolha);

         return true;

      } catch (Exception e) {
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         subFases.reset();

         subFases.setCodigo(codigo.getNumber());

         if (subFases.select()) {

            nome.setText(subFases.getNome());
            sigla.setText(subFases.getSigla());
            acada.setNumber(subFases.getAcada().getCodigo());
            minimo.setNumber(subFases.getMinimo());
            acerto.setNumber(subFases.getAcerto());
            entrModEsc.setNumber(subFases.getEntrModEsc());
            estiVez.setSelectedItem(subFases.getEstiVez());
            grade.setSelectedItem(subFases.getGrade());
            respObs.setSelected(subFases.isRespObs());

            String cod1 = null;
            String cod2 = null;

            String des1 = null;
            String des2 = null;

            String tip1 = null;
            String tip2 = null;

            //percorre o map  
            for (Iterator<Estimulo> it = subFases.getModeloEscolha().keySet().iterator(); it.hasNext();) {
               Estimulo modelo = it.next();
               Estimulo escolha = subFases.getModeloEscolha().get(modelo);

               modelo.select();

               cod1 = String.valueOf(modelo.getCodigo());
               des1 = modelo.getNome();
               tip1 = modelo.getTipoDescricao();

               if (escolha != null) {
                  escolha.select();

                  cod2 = String.valueOf(escolha.getCodigo());
                  des2 = escolha.getNome();
                  tip2 = escolha.getTipoDescricao();

               }
               else {
                  cod2 = null;
                  des2 = null;
                  tip2 = Estimulo.DSC_NENHUM;
               }

               tableAdd.addRow(cod1, des1, tip1, cod2, des2, tip2);

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

         JOptionPane.showMessageDialog(null, "Registro não encontrado", "Anotações", JOptionPane.ERROR_MESSAGE);

         controlView.setControl(CAMPO_NORMAL, false, true);
         codigo.requestFocus();

         return false;
      }

   }
}
