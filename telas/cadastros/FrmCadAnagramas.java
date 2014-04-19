package telas.cadastros;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Anagrama;
import lib.beans.Estimulo;
import lib.beans.Reforco;
import lib.campos.CAnagramas;
import lib.campos.CEstimulos;
import lib.campos.CReforcos;
import lib.telas.FrmCadastros;
import swing.XButton;
import swing.XComboBox;
import swing.XDecimalField;
import swing.XDialog;
import swing.XInError;
import swing.XNumberField;
import swing.XPanel;
import swing.XTableAdd;
import swing.XTableCampo;
import swing.XTextField;
import util.XTrataException;

public class FrmCadAnagramas extends FrmCadastros {

   // aba1
   private final CAnagramas    codigo   = new CAnagramas(1, 20, 15, "Código");
   private final XTextField    nome     = new XTextField(2, 20, 50, "Nome");
   private final XTextField    sigla    = new XTextField(3, 20, 5, "Sigla");
   private final CReforcos     acada    = new CReforcos(4, 20, 15, "Reforço a cada acerto");
   private final XNumberField  minimo   = new XNumberField(5, 20, 10, "Mínimo de tentativas");
   private final XDecimalField acerto   = new XDecimalField(6, 20, 10, "% de acerto");
   private final XNumberField  qdtExtra = new XNumberField(8, 20, 10, "Quantidade blocos extra");

   private final XPanel        panel    = new XPanel();
   private final XPanel        panel2   = new XPanel();

   // aba2
   private final XTableCampo   addExtra = new XTableCampo(7, 2, 70, 8, "Estimulos extra", new CEstimulos(0, 0, 0, ""));
   private final XTableAdd     tableAdd = new XTableAdd(1, 1, 70, 8, "Estímulos") {
                                           public Vector<Object> getLinhaAdd() {

                                              if (tableAdd.length() == 6) {
                                                 return null;
                                              }

                                              return new DialogEscolha().getLinha();
                                           }
                                        };

   private final Anagrama      anagrama = new Anagrama();

   public FrmCadAnagramas() {
      super("Cadastro de anagramas");

      initComponentes();
   }

   public void initComponentes() {

      panel.add(codigo);
      panel.add(nome);
      panel.add(acada);
      panel.add(minimo);
      panel.add(acerto);
      panel.add(sigla);
      panel.add(qdtExtra);

      panel2.add(addExtra);
      panel2.add(tableAdd);

      qdtExtra.setTamanho(5);
      nome.setTamanho(50);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      minimo.setObrigatorio(true);
      qdtExtra.setObrigatorio(true);
      tableAdd.setObrigatorio(true);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, sigla);
      controlView.add(CAMPO_NORMAL, acada);
      controlView.add(CAMPO_NORMAL, minimo);
      controlView.add(CAMPO_NORMAL, acerto);
      controlView.add(CAMPO_NORMAL, qdtExtra);
      controlView.add(CAMPO_NORMAL, tableAdd);
      controlView.add(CAMPO_NORMAL, addExtra);

      addPainel("Principal", panel);
      addPainel("Configurações", panel2);

      sigla.setTamanho(4);

      tableAdd.addColuna("CODIGO");
      tableAdd.addColuna("SIGLA");
      tableAdd.addColuna("ESTIMULO");
      tableAdd.addColuna("TIPO");

      tableAdd.setTamanho(0, 10);
      tableAdd.setTamanho(1, 10);
      tableAdd.setTamanho(2, 22);
      tableAdd.setTamanho(3, 15);

      acerto.addInputVerifier(new ValidPAcerto());

      codigo.addInputVerifier(new VerifierCodigo());

      setBean(anagrama);

      cancelarClick();
   }

   private class DialogEscolha {

      private XDialog                 dialog  = new XDialog(420, 200, "Selecione o estimulo");
      private final CEstimulos        modelo  = new CEstimulos(2, 31, 15, null);
      private final XComboBox<String> boxMod  = new XComboBox<String>(2, 15, 15, "Estímulo");

      private final XButton           add     = new XButton(5, 7, 15, "Adicionar");
      private final XButton           can     = new XButton(5, 31, 15, "Cancelar");

      Estimulo                        mod     = new Estimulo();

      private Vector<Object>          retorno = null;

      public DialogEscolha() {
         initComponentes();
      }

      private void initComponentes() {

         dialog.add(modelo);
         dialog.add(boxMod);
         dialog.add(add);
         dialog.add(can);

         add.addActionListener(action);
         can.addActionListener(action);

         boxMod.addItem(Estimulo.DSC_PALAVRA);
         boxMod.addItem(Estimulo.DSC_DESENHO);
         boxMod.addItem(Estimulo.DSC_AUDIO);
      }

      public Vector<Object> getLinha() {
         dialog.setVisible(true);
         return retorno;
      }

      private Action action = new AbstractAction() {

                               public void actionPerformed(ActionEvent e) {
                                  if (e.getSource().equals(add)) {

                                     if (modelo.getNumber() <= 0) {
                                        JOptionPane.showMessageDialog(dialog, "Campo modelo é obrigatório!!", "Erro", JOptionPane.ERROR_MESSAGE);
                                        modelo.requestFocus();
                                        XInError.setInError(boxMod, true);
                                        return;
                                     }

                                     if (boxMod.getSelectedIndex() < 0) {
                                        JOptionPane.showMessageDialog(dialog, "Campo tipo é obrigatório!!", "Erro", JOptionPane.ERROR_MESSAGE);
                                        boxMod.requestFocus();
                                        XInError.setInError(boxMod, true);
                                        return;
                                     }

                                     retorno = new Vector<Object>();

                                     mod.reset();

                                     mod.setCodigo(modelo.getNumber());

                                     if (!mod.select()) {
                                        JOptionPane.showMessageDialog(dialog, "Modelo não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
                                        modelo.requestFocus();
                                        return;
                                     }

                                     retorno.add(mod.getCodigo());
                                     retorno.add(mod.getSigla());
                                     retorno.add(mod.getNome());
                                     retorno.add(boxMod.getSelectedItem());

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

   public boolean setDadosBean() {
      try {

         anagrama.reset();

         anagrama.setCodigo(codigo.getNumber());
         anagrama.setNome(nome.getText());
         anagrama.setSigla(sigla.getText());
         anagrama.setPorcAcerto(acerto.getNumber());
         anagrama.setMinimo(minimo.getNumber());
         anagrama.setQtdExtra(qdtExtra.getNumber());

         Reforco reforco = new Reforco();
         reforco.setCodigo(acada.getNumber());
         anagrama.setReforco(reforco);

         Estimulo es;
         Vector<Object> data;
         ArrayList<Estimulo> estimulos = new ArrayList<Estimulo>();

         for (int i = 0; i < tableAdd.length(); i++) {

            es = new Estimulo();
            data = tableAdd.getRow(i);

            es.setCodigo(Integer.parseInt(data.get(0).toString()));
            es.setSigla(data.get(1).toString());
            es.setNome(data.get(2).toString());
            es.setTipo(data.get(3).toString());

            estimulos.add(es);
         }

         anagrama.setEstimulos(estimulos);

         estimulos = new ArrayList<Estimulo>();

         for (int i = 0; i < addExtra.length(); i++) {

            es = new Estimulo();
            data = addExtra.getRow(i);

            es.setCodigo(Integer.parseInt(data.get(0).toString()));
            es.setSigla(data.get(1).toString());
            es.setNome(data.get(2).toString());

            estimulos.add(es);
         }

         anagrama.setEstExtras(estimulos);

         return true;

      } catch (Exception e) {
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         anagrama.reset();

         anagrama.setCodigo(codigo.getNumber());

         if (anagrama.select()) {

            nome.setText(anagrama.getNome());
            sigla.setText(anagrama.getSigla());
            acada.setNumber(anagrama.getReforco().getCodigo());
            minimo.setNumber(anagrama.getMinimo());
            acerto.setNumber(anagrama.getPorcAcerto());
            qdtExtra.setNumber(anagrama.getQtdExtra());

            for (Estimulo e : anagrama.getEstimulos()) {
               e.select();

               tableAdd.addRow(String.valueOf(e.getCodigo()), e.getSigla(), e.getNome(), e.getTipoDescricao());
            }

            for (Estimulo e : anagrama.getEstExtras()) {
               e.select();

               addExtra.addRow(String.valueOf(e.getCodigo()), e.getSigla(), e.getNome());
            }

            if (OPE == ALT) {
               controlView.setControl(CAMPO_NORMAL, true, false);

            }
            else {
               controlView.setControl(CAMPO_NORMAL, false, false);
            }

            return true;
         }

         JOptionPane.showMessageDialog(null, "Registro não encontrado", "Anotações", JOptionPane.ERROR_MESSAGE);

         controlView.setControl(CAMPO_NORMAL, false, true);
         codigo.requestFocus();

         XInError.setInError(codigo, true);

         return false;
      }

   }
}
