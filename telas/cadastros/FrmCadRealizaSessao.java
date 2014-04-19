package telas.cadastros;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Estimulo;
import lib.beans.ItemRealizaSessao;
import lib.beans.RealizaSessao;
import lib.campos.CEstimulos;
import lib.campos.CRealizaSessao;
import lib.telas.FrmCadastros;
import swing.XButton;
import swing.XComboBox;
import swing.XDialog;
import swing.XPanel;
import swing.XTableAdd;
import swing.XTextArea;
import swing.XTextField;
import util.XStringUtil;
import util.XTrataException;

public class FrmCadRealizaSessao extends FrmCadastros {

   private final XPanel         panel         = new XPanel();
   private final XPanel         panel2        = new XPanel();

   private final CRealizaSessao codigo        = new CRealizaSessao(1, 15, 15, "Código");
   private final XTextField     sigla         = new XTextField(2, 15, 4, "Sigla");
   private final XTextField     nome          = new XTextField(3, 15, 50, "Nome");
   private final XTextArea      descricao     = new XTextArea(4, 5, 65, 10, "Descrição");
   private final TableRespostas respostas     = new TableRespostas(1, 1, 77, 16, "Respostas");

   private RealizaSessao        realizaSessao = new RealizaSessao();

   public FrmCadRealizaSessao() {
      super("Alteração de Realiza Sessão");

      initComponentes();
   }

   public void initComponentes() {
      panel.add(codigo);
      panel.add(nome);
      panel.add(sigla);
      panel.add(descricao.painel);

      panel2.add(respostas);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      sigla.setObrigatorio(true);
      descricao.setObrigatorio(true);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, sigla);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, descricao);
      controlView.add(CAMPO_NORMAL, respostas);

      respostas.addColuna("TENTATIVA");
      respostas.addColuna("ORDEM");
      respostas.addColuna("COD");
      respostas.addColuna("MODELO");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHIDO");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("CORRETO");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHA 1");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHA 2");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHA 3");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHA 4");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHA 5");
      respostas.addColuna("TIPO");
      respostas.addColuna("COD");
      respostas.addColuna("ESCOLHA 6");
      respostas.addColuna("TIPO");

      respostas.setTamanho(0, 8);
      respostas.setTamanho(1, 8);
      respostas.setTamanho(2, 0);
      respostas.setTamanho(3, 10);
      respostas.setTamanho(4, 8);
      respostas.setTamanho(5, 0);
      respostas.setTamanho(6, 10);
      respostas.setTamanho(7, 8);
      respostas.setTamanho(8, 0);
      respostas.setTamanho(9, 10);
      respostas.setTamanho(10, 8);
      respostas.setTamanho(11, 0);
      respostas.setTamanho(12, 10);
      respostas.setTamanho(13, 8);
      respostas.setTamanho(14, 0);
      respostas.setTamanho(15, 10);
      respostas.setTamanho(16, 8);
      respostas.setTamanho(17, 0);
      respostas.setTamanho(18, 10);
      respostas.setTamanho(19, 8);
      respostas.setTamanho(20, 0);
      respostas.setTamanho(21, 10);
      respostas.setTamanho(22, 8);
      respostas.setTamanho(23, 0);
      respostas.setTamanho(24, 10);
      respostas.setTamanho(25, 8);
      respostas.setTamanho(26, 0);
      respostas.setTamanho(27, 10);
      respostas.setTamanho(28, 8);

      setBean(realizaSessao);

      addPainel("Principal", panel);
      addPainel("Respostas", panel2);

      codigo.addInputVerifier(new VerifierCodigo());

      cancelarClick();

      removeButton("Incluir");
      removeSubMenu("Incluir");
   }

   public boolean setDadosBean() {
      try {
         realizaSessao.reset();

         realizaSessao.setCodigo(codigo.getNumber());
         realizaSessao.setNome(nome.getText());
         realizaSessao.setSigla(sigla.getText());
         realizaSessao.setDescricao(descricao.getText());

         return true;

      } catch (Exception e) {
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         realizaSessao.reset();

         realizaSessao.setCodigo(codigo.getNumber());

         if (realizaSessao.select()) {

            nome.setText(realizaSessao.getNome());
            sigla.setText(realizaSessao.getSigla());
            descricao.setText(realizaSessao.getDescricao());

            if (OPE == ALT) {
               controlView.setControl(CAMPO_NORMAL, true, false);
            }
            else {
               controlView.setControl(CAMPO_NORMAL, false, false);
            }

            for (ItemRealizaSessao item : realizaSessao.getItens()) {
               ArrayList<Object> linha = new ArrayList<Object>();

               linha.add(item.getTentativa());
               linha.add(item.getOrdemExec());

               linha.add(item.getModelo().getCodigo());
               linha.add(item.getModelo().getNome());
               linha.add(item.getModelo().getTipoDescricao());
               linha.add(item.getEscolhido().getCodigo());
               linha.add(item.getEscolhido().getNome());
               linha.add(item.getEscolhido().getTipoDescricao());
               linha.add(item.getEscCorreto().getCodigo());
               linha.add(item.getEscCorreto().getNome());
               linha.add(item.getEscCorreto().getTipoDescricao());

               Estimulo estimulo;

               for (int i = 1; i <= 6; i++) {

                  estimulo = item.getEscolha(i);

                  if (estimulo != null) {
                     linha.add(estimulo.getCodigo());
                     linha.add(estimulo.getNome());
                     linha.add(estimulo.getTipoDescricao());
                  }
                  else {
                     linha.add(null);
                     linha.add(null);
                     linha.add(null);
                  }
               }

               respostas.addRow(linha);
            }

            return true;
         }

         JOptionPane.showMessageDialog(null, "Registro não encontrado", "Realiza Sessão", JOptionPane.ERROR_MESSAGE);

         controlView.setControl(CAMPO_NORMAL, false, true);
         codigo.requestFocus();

         return false;

      }
   }

   private class TableRespostas extends XTableAdd {

      private final DialogEscolha dEscolha = new DialogEscolha();

      public TableRespostas(int linha, int coluna, int largura, int altura, String label) {
         super(linha, coluna, largura, altura, label, false, true, false, false);
      }

      public void updateTable() {
         if (getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(FrmCadRealizaSessao.this, "É necessário selecionar uma linha da tabela!!!", "Erro", JOptionPane.ERROR_MESSAGE);

            return;
         }
         dEscolha.mostrar();
      }

   }

   private class DialogEscolha extends XDialog {

      private final CEstimulos        escolha = new CEstimulos(3, 15, 15, "Estímulo escolhido");

      private final XComboBox<String> boxEsc  = new XComboBox<String>(3, 31, 15, null);

      private final XButton           add     = new XButton(5, 7, 15, "OK");
      private final XButton           can     = new XButton(5, 31, 15, "Cancelar");

      //Estimulo                        esc     = new Estimulo();

      public DialogEscolha() {
         super(420, 200, "Selecione o estimulo escolhido");
         initComponentes();
      }

      public void mostrar() {
         if(respostas.getRow(respostas.getSelectedRow()).get(6) != null){
            escolha.setNumber(Integer.parseInt(respostas.getRow(respostas.getSelectedRow()).get(5).toString()));
            escolha.requestFocus();
            escolha.transferFocus();
            
            boxEsc.setSelectedItem(respostas.getRow(respostas.getSelectedRow()).get(7).toString());
         }else{
            boxEsc.setSelectedItem(Estimulo.DSC_NENHUM);
         }
         
         setVisible(true);
      }

      private void initComponentes() {

         add(escolha);

         add(boxEsc);

         add(add);
         add(can);

         add.addActionListener(action);
         can.addActionListener(action);

         boxEsc.addItem(Estimulo.DSC_DESENHO);
         boxEsc.addItem(Estimulo.DSC_PALAVRA);
         boxEsc.addItem(Estimulo.DSC_COR);
         boxEsc.addItem(Estimulo.DSC_NENHUM);

         boxEsc.setSelectedIndex(3);
         escolha.setEnabled(false);

         boxEsc.addItemListener(change);

         escolha.addFocusListener(new BBB());
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

                                           if (escolha.getNumber() <= 0 && !boxEsc.getSelectedItem().toString().equals(Estimulo.DSC_NENHUM)) {
                                              JOptionPane.showMessageDialog(DialogEscolha.this, "Campo escolha é obrigatório!! \n  Caso não tenha escolha selecione o tipo NENHUM", "Erro", JOptionPane.ERROR_MESSAGE);
                                              escolha.requestFocus();
                                              return;
                                           }

                                           if (boxEsc.getSelectedIndex() < 0) {
                                              JOptionPane.showMessageDialog(DialogEscolha.this, "Campo tipo escolha é obrigatório!!", "Erro", JOptionPane.ERROR_MESSAGE);
                                              boxEsc.requestFocus();
                                              return;
                                           }

                                           int lcl = respostas.getSelectedRow();
                                           
                                           Estimulo escolhido = new Estimulo();

                                           if (escolha.getNumber() <= 0) {
                                              
                                              realizaSessao.setItemEscolhido(lcl, escolhido);
                                              respostas.setValueAt(0, lcl, 5);
                                              respostas.setValueAt(null, lcl, 6);
                                              respostas.setValueAt(null, lcl, 7);                                              
                                           }
                                           else {

                                              

                                              escolhido.setCodigo(escolha.getNumber());
                                              escolhido.setTipo(boxEsc.getSelectedItem().toString());

                                              escolhido.select();

                                              respostas.setValueAt(escolhido.getCodigo(), lcl, 5);
                                              respostas.setValueAt(escolhido.getNome(), lcl, 6);
                                              respostas.setValueAt(escolhido.getTipoDescricao(), lcl, 7);
                                              
                                              realizaSessao.setItemEscolhido(lcl, escolhido);
                                           }

                                        }
                                        setVisible(false);
                                     }

                                  };

   }

}
