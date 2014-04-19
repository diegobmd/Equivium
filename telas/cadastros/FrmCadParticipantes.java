package telas.cadastros;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Participante;
import lib.campos.CEscolaridade;
import lib.campos.CEstado;
import lib.campos.CParticipantes;
import lib.campos.CSexo;
import lib.telas.FrmCadastros;
import swing.XCEPField;
import swing.XDateField;
import swing.XFoneField;
import swing.XNumberField;
import swing.XPanel;
import swing.XTextField;
import util.XTrataException;

public class FrmCadParticipantes extends FrmCadastros {

   private final XPanel         panel1       = new XPanel();
   private final XPanel         panel2       = new XPanel();

   // principal
   private final CParticipantes codigo       = new CParticipantes(1, 20, 15, "Código");
   private final XTextField     nome         = new XTextField(2, 20, 50, "Nome");
   private final XTextField     projeto      = new XTextField(3, 20, 20, "Projeto");
   private final XTextField     iniciais     = new XTextField(4, 20, 5, "Iniciais");
   private final CSexo          sexo         = new CSexo(5, 20, 20, "Sexo");
   private final XDateField     nascimento   = new XDateField(6, 20, 20, "Dt. nascimento");
   private final CEscolaridade  escolaridade = new CEscolaridade(7, 20, 20, "Escolaridade");
   // contato
   private final XTextField     mae          = new XTextField(1, 20, 50, "Nome mãe");
   private final CEscolaridade  escMae       = new CEscolaridade(2, 20, 20, "Escolaridade");
   private final XTextField     pai          = new XTextField(3, 20, 50, "Nome pai");
   private final CEscolaridade  escPai       = new CEscolaridade(4, 20, 20, "Escolaridade");
   private final XTextField     endereco     = new XTextField(5, 20, 50, "Endereço");
   private final XTextField     complemento  = new XTextField(6, 20, 20, "Comp.");
   private final XNumberField   numero       = new XNumberField(7, 20, 20, "Número");
   private final CEstado        estado       = new CEstado(8, 20, 15, "Estado");
   private final XCEPField      cep          = new XCEPField(9, 20, 20, "CEP");
   private final XFoneField     telefone     = new XFoneField(10, 20, 20, "Telefone");

   private final Participante   participante = new Participante();

   public FrmCadParticipantes() {
      super("Cadastro de Participantes");
      initComponentes();
   }

   public void initComponentes() {
      panel1.add(codigo);
      panel1.add(nome);
      panel1.add(projeto);
      panel1.add(iniciais);
      panel1.add(sexo);
      panel1.add(nascimento);
      panel1.add(escolaridade);

      panel2.add(mae);
      panel2.add(escMae);
      panel2.add(pai);
      panel2.add(escPai);
      panel2.add(endereco);
      panel2.add(complemento);
      panel2.add(numero);
      panel2.add(estado);
      panel2.add(cep);
      panel2.add(telefone);

      codigo.setObrigatorio(true);
      nome.setObrigatorio(true);
      projeto.setObrigatorio(true);
      iniciais.setObrigatorio(true);
      sexo.setObrigatorio(true);
      nascimento.setObrigatorio(true);
      escolaridade.setObrigatorio(true);
      mae.setObrigatorio(true);
      escMae.setObrigatorio(true);
      pai.setObrigatorio(true);
      escPai.setObrigatorio(true);
      endereco.setObrigatorio(true);
      numero.setObrigatorio(true);
      estado.setObrigatorio(true);
      cep.setObrigatorio(true);
      telefone.setObrigatorio(true);

      nome.setTamanho(50);
      projeto.setTamanho(20);
      iniciais.setTamanho(3);
      mae.setTamanho(50);
      pai.setTamanho(50);
      endereco.setTamanho(50);
      complemento.setTamanho(20);

      controlView.add(CAMPO_CHAVE, codigo);
      controlView.add(CAMPO_NORMAL, nome);
      controlView.add(CAMPO_NORMAL, projeto);
      controlView.add(CAMPO_NORMAL, iniciais);
      controlView.add(CAMPO_NORMAL, sexo);
      controlView.add(CAMPO_NORMAL, nascimento);
      controlView.add(CAMPO_NORMAL, escolaridade);

      controlView.add(CAMPO_NORMAL, mae);
      controlView.add(CAMPO_NORMAL, escMae);
      controlView.add(CAMPO_NORMAL, pai);
      controlView.add(CAMPO_NORMAL, escPai);
      controlView.add(CAMPO_NORMAL, endereco);
      controlView.add(CAMPO_NORMAL, complemento);
      controlView.add(CAMPO_NORMAL, numero);
      controlView.add(CAMPO_NORMAL, estado);
      controlView.add(CAMPO_NORMAL, cep);
      controlView.add(CAMPO_NORMAL, telefone);

      addPainel("Principal", panel1);
      addPainel("Contato", panel2);

      setBean(participante);

      codigo.addInputVerifier(new VerifierCodigo());

      cancelarClick();
   }

   public boolean setDadosBean() {
      try {
         participante.reset();

         participante.setCodigo(codigo.getNumber());
         participante.setNome(nome.getText());
         participante.setProjeto(projeto.getText());
         participante.setIniciais(iniciais.getText());
         participante.setSexo(sexo.getSelectedCodigo());
         participante.setEscolaridade(escolaridade.getSelectedCodigo());
         participante.setMae(mae.getText());
         participante.setEscMae(escMae.getSelectedCodigo());
         participante.setPai(pai.getText());
         participante.setEscPai(escPai.getSelectedCodigo());
         participante.setEndereco(endereco.getText());
         participante.setComplemento(complemento.getText());
         participante.setNumero(numero.getNumber());
         participante.setEstado(estado.getSelectedCodigo());
         participante.setCep(cep.getCep());
         participante.setTelefone(telefone.getFone());
         participante.setNascimento(nascimento.getDate());

         return true;

      } catch (Exception e) {
         XTrataException.printStackTrace(e);
         return false;
      }
   }

   private class VerifierCodigo extends InputVerifier {

      public boolean verify(JComponent arg0) {

         controlView.setControl(CAMPO_NORMAL, false, true);

         participante.reset();

         participante.setCodigo(codigo.getNumber());

         if (participante.select()) {

            nome.setText(participante.getNome());
            projeto.setText(participante.getProjeto());
            iniciais.setText(participante.getIniciais());
            sexo.setSelectedCodigo(participante.getSexo());
            nascimento.setDate(participante.getNascimento());
            escolaridade.setSelectedCodigo(participante.getEscolaridade());

            mae.setText(participante.getMae());
            escMae.setSelectedCodigo(participante.getEscMae());
            pai.setText(participante.getPai());
            escPai.setSelectedCodigo(participante.getEscPai());
            endereco.setText(participante.getEndereco());
            complemento.setText(participante.getComplemento());
            numero.setNumber(participante.getNumero());
            estado.setSelectedCodigo(participante.getEstado());
            cep.setCep(participante.getCep());
            telefone.setFone(participante.getTelefone());

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
