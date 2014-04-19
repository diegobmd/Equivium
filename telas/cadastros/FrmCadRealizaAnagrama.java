package telas.cadastros;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.RealizaAnagrama;
import lib.campos.CRealizaAnagrama;
import lib.telas.FrmCadastros;
import swing.XPanel;
import swing.XTextArea;
import swing.XTextField;
import util.XTrataException;

public class FrmCadRealizaAnagrama extends FrmCadastros {

   private final XPanel         panel         = new XPanel();
   private final CRealizaAnagrama codigo        = new CRealizaAnagrama(1, 15, 15, "Código");
   private final XTextField     sigla         = new XTextField(2, 15, 4, "Sigla");
   private final XTextField     nome          = new XTextField(3, 15, 50, "Nome");
   private final XTextArea      descricao     = new XTextArea(4, 5, 65, 10, "Descrição");

   private RealizaAnagrama        realizaSessao = new RealizaAnagrama();

   public FrmCadRealizaAnagrama() {
      super("Alteração de Realiza Sessão de Anagramas");

      initComponentes();
   }

   public void initComponentes() {
      panel.add(codigo);
      panel.add(nome);
      panel.add(sigla);
      panel.add(descricao.painel);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      sigla.setObrigatorio(true);
      descricao.setObrigatorio(true);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, sigla);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, descricao);

      setBean(realizaSessao);

      addPainel("Principal", panel);

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

            return true;
         }

         JOptionPane.showMessageDialog(null, "Registro não encontrado", "Realiza Sessão Anagrama", JOptionPane.ERROR_MESSAGE);

         controlView.setControl(CAMPO_NORMAL, false, true);
         codigo.requestFocus();

         return false;

      }

   }
}
