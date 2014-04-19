package telas.cadastros;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Reforco;
import lib.campos.CReforcos;
import lib.telas.FrmCadastros;
import swing.XFileField;
import swing.XPanel;
import swing.XTextField;
import util.XTrataException;

public class FrmCadReforco extends FrmCadastros {

   private final CReforcos  codigo  = new CReforcos(1, 15, 15, "Código");
   private final XTextField nome    = new XTextField(2, 15, 50, "Nome");
   private final XFileField aAudio  = new XFileField(3, 15, 50, "Audio", ".mp3");
   private final XPanel     panel   = new XPanel();

   private final Reforco    reforco = new Reforco();

   public FrmCadReforco() {
      super("Cadastro de Reforço");
      initComponentes();
   }

   public void initComponentes() {
      panel.add(codigo);
      panel.add(nome);
      panel.add(aAudio);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, aAudio);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      aAudio.setObrigatorio(true);

      nome.setTamanho(50);
      aAudio.setTamanho(255);

      codigo.addInputVerifier(new VerifierCodigo());

      setBean(reforco);

      addPainel("Principal", panel);

      cancelarClick();
   }

   public boolean setDadosBean() {
      try {
         reforco.reset();

         reforco.setCodigo(codigo.getNumber());
         reforco.setNome(nome.getText());
         reforco.setAudio(aAudio.getText());

         return true;

      } catch (Exception e) {
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         reforco.reset();

         reforco.setCodigo(codigo.getNumber());

         if (reforco.select()) {

            nome.setText(reforco.getNome());
            aAudio.setText(reforco.getAudio());

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

         return false;

      }

   }

}
