package telas.cadastros;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Pesquisador;
import lib.campos.CPesquisadores;
import lib.telas.FrmCadastros;
import swing.XInError;
import swing.XPanel;
import swing.XPasswordField;
import swing.XTextField;
import util.XTrataException;

public class FrmCadPesquisadores extends FrmCadastros {

	private final CPesquisadores codigo = new CPesquisadores(1, 20, 15, "Código");
	private final XTextField nome = new XTextField(2, 20, 50, "Nome");
	private final XTextField login = new XTextField(3, 20, 20, "Login");
	private final XPasswordField senha = new XPasswordField(4, 20, 15, "Senha");
	private final XPasswordField csenha = new XPasswordField(5, 20, 15, "Confimação senha");

	private final XPanel panel = new XPanel();

	private final Pesquisador pesquisador = new Pesquisador();

	public FrmCadPesquisadores() {
		super("Cadastro de Pesquisadores");
		initComponentes();
	}

	public void initComponentes() {

		panel.add(codigo);
		panel.add(nome);
		panel.add(login);
		panel.add(senha);
		panel.add(csenha);

		codigo.setObrigatorio(true);
		nome.setObrigatorio(true);
		login.setObrigatorio(true);
		senha.setObrigatorio(true);
		csenha.setObrigatorio(true);

		controlView.add(CAMPO_CHAVE, codigo);
		controlView.add(CAMPO_NORMAL, nome);
		controlView.add(CAMPO_NORMAL, login);
		controlView.add(CAMPO_NORMAL, senha);
		controlView.add(CAMPO_NORMAL, csenha);

		nome.setTamanho(50);
		login.setTamanho(20);
		senha.setTamanho(20);
		csenha.setTamanho(50);

		addPainel("Principal", panel);

		setBean(pesquisador);

		codigo.addInputVerifier(new VerifierCodigo());

		cancelarClick();
	}

	public boolean setDadosBean() {
		try {
			pesquisador.reset();

			pesquisador.setCodigo(codigo.getNumber());
			pesquisador.setNome(nome.getText());
			pesquisador.setLogin(login.getText());
			pesquisador.setSenha(senha.getText());

			return true;

		} catch (Exception e) {
			XTrataException.printStackTrace(e);
		}
		return false;
	}

	public void okClick() {
		if (!senha.getText().equals(csenha.getText())) {
			JOptionPane.showMessageDialog(null, "Senhas incorretas", "Pesquisadores", JOptionPane.ERROR_MESSAGE);
			XInError.setInError(senha, true);
			XInError.setInError(csenha, true);
			return;
		}
		super.okClick();
	}

	private class VerifierCodigo extends InputVerifier {

		public boolean verify(JComponent arg0) {

			controlView.setControl(CAMPO_NORMAL, false, true);

			pesquisador.reset();

			pesquisador.setCodigo(codigo.getNumber());

			if (pesquisador.select()) {

				nome.setText(pesquisador.getNome());
				login.setText(pesquisador.getLogin());
				senha.setText(pesquisador.getSenha());
				csenha.setText(pesquisador.getSenha());

				if (OPE == ALT) {
					controlView.setControl(CAMPO_NORMAL, true, false);
				} else {
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
