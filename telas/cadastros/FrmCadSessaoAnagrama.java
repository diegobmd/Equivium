package telas.cadastros;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Anagrama;
import lib.beans.Reforco;
import lib.beans.SessaoAnagrama;
import lib.campos.CAnagramas;
import lib.campos.CReforcos;
import lib.campos.CSessoesAnagrama;
import lib.telas.FrmCadastros;
import swing.XPanel;
import swing.XTableCampo;
import swing.XTextField;
import util.XTrataException;

public class FrmCadSessaoAnagrama extends FrmCadastros {

	private final XPanel panel = new XPanel();
	private final CSessoesAnagrama codigo = new CSessoesAnagrama(1, 20, 15, "Código");
	private final XTextField nome = new XTextField(2, 20, 50, "Nome");
	private final XTextField sigla = new XTextField(3, 20, 4, "Sigla");
	private final CReforcos aofim = new CReforcos(4, 20, 15, "Reforço ao final");
	private final XTableCampo tableAdd = new XTableCampo(5, 2, 70, 9, "Anagramas", new CAnagramas(0, 0, 0, ""));

	private final SessaoAnagrama sessao = new SessaoAnagrama();

	public FrmCadSessaoAnagrama() {
		super("Cadastro de Sessões de Anagramas");

		initComponentes();
	}

	public void initComponentes() {
		panel.add(codigo);
		panel.add(nome);
		panel.add(sigla);
		panel.add(tableAdd);
		panel.add(aofim);

		codigo.setObrigatorio(true);
		nome.setObrigatorio(true);
		sigla.setObrigatorio(true);
		tableAdd.setObrigatorio(true);

		controlView.add(CAMPO_CHAVE, codigo);
		controlView.add(CAMPO_NORMAL, nome);
		controlView.add(CAMPO_NORMAL, sigla);
		controlView.add(CAMPO_NORMAL, aofim);
		controlView.add(CAMPO_NORMAL, tableAdd);

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

			Anagrama fases;
			Vector<Object> data;
			ArrayList<Anagrama> list = new ArrayList<Anagrama>();

			for (int i = 0; i < tableAdd.length(); i++) {
				fases = new Anagrama();
				data = tableAdd.getRow(i);

				fases.setCodigo(Integer.parseInt(data.get(0).toString()));
				fases.setNome(data.get(1).toString());

				list.add(fases);
			}

			sessao.setAnagramas(list);

			return true;

		} catch (Exception e) {
			XTrataException.printStackTrace(e);
			return false;
		}
	}

	private class VerifierCodigo extends InputVerifier {

		public boolean verify(JComponent arg0) {

			controlView.setControl(CAMPO_NORMAL, false, true);

			sessao.reset();

			sessao.setCodigo(codigo.getNumber());

			if (sessao.select()) {

				nome.setText(sessao.getNome());
				sigla.setText(sessao.getSigla());
				aofim.setNumber(sessao.getAoFim().getCodigo());

				ArrayList<Anagrama> list = sessao.getAnagramas();

				for (Anagrama fas : list) {
					tableAdd.addRow(String.valueOf(fas.getCodigo()), fas.getSigla(), fas.getNome());
				}

				if (OPE == ALT) {
					controlView.setControl(CAMPO_NORMAL, true, false);

				} else {
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
