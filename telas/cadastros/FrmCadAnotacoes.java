package telas.cadastros;

import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Anotacao;
import lib.campos.CAnotacoes;
import lib.campos.CParticipantes;
import lib.telas.FrmCadastros;
import swing.XDateField;
import swing.XPanel;
import swing.XTextArea;
import swing.XTextField;
import util.XTrataException;

public class FrmCadAnotacoes extends FrmCadastros {

   private final XPanel         panel        = new XPanel();
   private final CAnotacoes     codigo       = new CAnotacoes(1, 15, 15, "Código");
   private final CParticipantes participante = new CParticipantes(2, 15, 15, "Participante");
   private final XDateField     data         = new XDateField(3, 15, 10, "Data");
   private final XTextField     nome         = new XTextField(4, 15, 50, "Nome");
   private final XTextArea      descricao    = new XTextArea(5, 5, 65, 10, "Descrição");

   private final Anotacao       anotacoes    = new Anotacao();

   public FrmCadAnotacoes() {
      super("Cadastro de Anotações");

      initComponentes();
   }

   public void initComponentes() {

      panel.add(codigo);
      panel.add(data);
      panel.add(participante);
      panel.add(nome);
      panel.add(descricao.painel);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, participante);
      controlView.add(CAMPO_NORMAL, data);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, descricao);

      nome.setTamanho(50);
      descricao.setTamanho(255);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      descricao.setObrigatorio(true);

      addPainel("Principal", panel);

      codigo.addInputVerifier(new VerifierCodigo());

      setBean(anotacoes);

      cancelarClick();
   }

   public void incluirClick() {
      super.incluirClick();

      data.setDate(new Date());
   }

   public boolean setDadosBean() {
      try {

         anotacoes.setCodigo(codigo.getNumber());
         anotacoes.setData(data.getDate());
         anotacoes.setDescricao(descricao.getText());
         anotacoes.setNome(nome.getText());
         anotacoes.setCodigoParticipante(participante.getNumber());

         return true;

      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Erro durante setagem de dados", "Anotações", JOptionPane.ERROR_MESSAGE);
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent comp) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         anotacoes.reset();

         anotacoes.setCodigo(codigo.getNumber());

         if (anotacoes.select()) {

            descricao.setText(anotacoes.getDescricao());
            nome.setText(anotacoes.getNome());
            participante.setNumber(anotacoes.getCodigoParticipante());
            data.setDate(anotacoes.getData());

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
