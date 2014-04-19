package telas.cadastros;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Estimulo;
import lib.campos.CEstimulos;
import lib.telas.FrmCadastros;
import swing.XColorField;
import swing.XDecimalField;
import swing.XFileField;
import swing.XPanel;
import swing.XTextField;
import util.XTrataException;

public class FrmCadEstimulos extends FrmCadastros {

   private final CEstimulos    codigo    = new CEstimulos(1, 20, 15, "Código");
   private final XTextField    sigla     = new XTextField(2, 20, 4, "Sigla");
   private final XTextField    nome      = new XTextField(3, 20, 50, "Nome");
   private final XFileField    aAudio    = new XFileField(4, 20, 50, "Arquivo de áudio", ".mp3");
   private final XFileField    aImagem   = new XFileField(5, 20, 50, "Arquivo de imagem", ".jpeg", ".jpg");
   private final XTextField    palavra   = new XTextField(6, 20, 50, "Formação do componente");
   private final XDecimalField intervalo = new XDecimalField(7, 20, 7, "Intervalo entre os comp (seg)");
   private final XColorField   corFonte  = new XColorField(8, 20, 10, "Cor da fonte");
   private final XColorField   corFundo  = new XColorField(9, 20, 10, "Cor de fundo");

   private final XPanel        panel     = new XPanel();

   private final Estimulo      estimulos = new Estimulo();

   public FrmCadEstimulos() {
      super("Cadastro de Estímulos");
      initComponentes();
   }

   public void initComponentes() {

      panel.add(codigo);
      panel.add(sigla);
      panel.add(nome);
      panel.add(aAudio);
      panel.add(aImagem);
      panel.add(palavra);
      panel.add(intervalo);
      panel.add(corFonte);
      panel.add(corFundo);

      codigo.setObrigatorio(true);

      sigla.setTamanho(4);
      sigla.setObrigatorio(true);

      nome.setTamanho(50);
      nome.setObrigatorio(true);

      aAudio.setTamanho(255);
//      aAudio.setObrigatorio(true);

      aAudio.setMsgNaoPermitido("Audio não permitido : somente são permitidos arquivos do tipo MP3");

      aImagem.setTamanho(255);
//      aImagem.setObrigatorio(true);

      aImagem.setMsgNaoPermitido("Imagem não permitada : somente são permitidos arquivos do tipo JPG ou JPEG");

      palavra.setTamanho(20);
//      palavra.setObrigatorio(true);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, sigla);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, aAudio);
      controlView.add(CAMPO_NORMAL, aImagem);
      controlView.add(CAMPO_NORMAL, palavra);
      controlView.add(CAMPO_NORMAL, intervalo);
      controlView.add(CAMPO_NORMAL, corFonte);
      controlView.add(CAMPO_NORMAL, corFundo);

      addPainel("Principal", panel);

      setBean(estimulos);

      codigo.addInputVerifier(new VerifierCodigo());

      cancelarClick();
   }

   public void okClick() {
      if (corFonte.getRGB() == corFundo.getRGB()) {
         JOptionPane.showMessageDialog(this, "Cor de fundo e cor da fonte não podem ser iguais!", "Estimulos", JOptionPane.ERROR_MESSAGE);
         return;
      }
      super.okClick();
   }

   public boolean setDadosBean() {
      try {
         estimulos.reset();

         estimulos.setCodigo(codigo.getNumber());
         estimulos.setSigla(sigla.getText());
         estimulos.setNome(nome.getText());
         estimulos.setAudio(aAudio.getText());
         estimulos.setFigura(aImagem.getText());
         estimulos.setPalavra(palavra.getText());
         estimulos.setIntervalo(intervalo.getNumber());
         estimulos.setCorFundo(corFundo.getRGB());
         estimulos.setCorFonte(corFonte.getRGB());

         return true;

      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Erro durante setagem de dados", "Estímulos", JOptionPane.ERROR_MESSAGE);
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         estimulos.reset();

         estimulos.setCodigo(codigo.getNumber());

         if (estimulos.select()) {

            sigla.setText(estimulos.getSigla());
            nome.setText(estimulos.getNome());
            aAudio.setText(estimulos.getAudio());
            aImagem.setText(estimulos.getFigura());
            palavra.setText(estimulos.getPalavra());
            intervalo.setNumber(estimulos.getIntervalo());
            corFundo.setRGB(estimulos.getCorFundo());
            corFonte.setRGB(estimulos.getCorFonte());

            if (OPE == ALT) {
               controlView.setControl(CAMPO_NORMAL, true, false);
            }
            else {
               controlView.setControl(CAMPO_NORMAL, false, false);
            }

            return true;
         }

         JOptionPane.showMessageDialog(null, "Registro não encontrado", "Estímulos", JOptionPane.ERROR_MESSAGE);

         controlView.setControl(CAMPO_NORMAL, false, true);
         codigo.requestFocus();

         return false;
      }

   }

}
