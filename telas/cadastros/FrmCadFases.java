package telas.cadastros;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lib.beans.Fase;
import lib.beans.SubFase;
import lib.campos.CFases;
import lib.campos.CSubFases;
import lib.telas.FrmCadastros;
import sql.querys.XSelect;
import swing.XComboBox;
import swing.XPanel;
import swing.XTableCampo;
import swing.XTextField;
import util.XTrataException;

public class FrmCadFases extends FrmCadastros {

	private final CFases codigo = new CFases(1, 20, 15, "Código");
	private final XTextField nome = new XTextField(2, 20, 50, "Nome");
	private final XTextField sigla = new XTextField(3, 20, 5, "Sigla");
	private final XPanel panel = new XPanel();
	private final XComboBox<Integer> grade = new XComboBox<Integer>(4, 20, 10, "Grade");
	private final XTableCampo tableAdd = new XTableCampo(5, 2, 70, 8, "Tentativas", new CSubFases(0, 0, 0, "") {

		public String getWhere() {
			return " SUB_GRADE = ? ";
		}

		public ArrayList<Object> getWhereParm() {
			ArrayList<Object> x = new ArrayList<Object>();
			x.add(grade.getSelectedItem());
			return x;
		}

		public XSelect getSelect() {
			XSelect s = super.getSelect();
			s.addCampo("sub_grade as grade");
			return s;
		}

	}) {
		public Vector<Object> getLinhaAdd() {
			repaint();

			if (grade.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "Deve-se ser selecionado uma grade", "Fases", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			return super.getLinhaAdd();
		}
	};

	private final Fase fase = new Fase();

	public FrmCadFases() {
		super("Cadastro de Fases");

		initComponentes();
	}

	public void initComponentes() {

		panel.add(codigo);
		panel.add(nome);
		panel.add(sigla);
		panel.add(tableAdd);
		panel.add(grade);

		sigla.setTamanho(4);
		nome.setTamanho(50);

		codigo.setObrigatorio(true);
		nome.setObrigatorio(true);
		sigla.setObrigatorio(true);
		tableAdd.setObrigatorio(true);
		grade.setObrigatorio(true);

		controlView.add(CAMPO_CHAVE, codigo);
		controlView.add(CAMPO_NORMAL, nome);
		controlView.add(CAMPO_NORMAL, sigla);
		controlView.add(CAMPO_NORMAL, tableAdd);
		controlView.add(CAMPO_NORMAL, grade);

		addPainel("Principal", panel);

		grade.addItem(4);
		grade.addItem(6);

		grade.addItemListener(change);

		setBean(fase);

		codigo.addInputVerifier(new VerifierCodigo());

		cancelarClick();
	}

	public boolean setDadosBean() {
		try {
			fase.reset();

			fase.setCodigo(codigo.getNumber());
			fase.setSigla(sigla.getText());
			fase.setNome(nome.getText());

			SubFase subFases;
			Vector<Object> data;
			ArrayList<SubFase> list = new ArrayList<SubFase>();

			for (int i = 0; i < tableAdd.length(); i++) {
				subFases = new SubFase();
				data = tableAdd.getRow(i);

				subFases.setCodigo(Integer.parseInt(data.get(0).toString()));
				subFases.setNome(data.get(1).toString());

				list.add(subFases);
			}

			fase.setSubFases(list);

			return true;

		} catch (Exception e) {
			XTrataException.printStackTrace(e);
			return false;
		}
	}

	private ItemListener change = new ItemListener() {

		public void itemStateChanged(ItemEvent e) {

			if (grade.getSelectedIndex() >= 0) {
				ArrayList<Integer> exc = new ArrayList<Integer>();

				for (int i = 0; i < tableAdd.getRowCount(); i++) {
					if (!tableAdd.getRow(i).get(2).equals(grade.getSelectedItem())) {
						exc.add(i);
					}
				}
				int m = 0;
				for (Integer i : exc) {
					tableAdd.removeRow(i - m);
					m++;
				}
			}

		}
	};

	private class VerifierCodigo extends InputVerifier {

		public boolean verify(JComponent arg0) {

			controlView.setControl(CAMPO_NORMAL, false, true);

			fase.reset();

			fase.setCodigo(codigo.getNumber());

			if (fase.select()) {

				codigo.setNumber(fase.getCodigo());
				sigla.setText(fase.getSigla());
				nome.setText(fase.getNome());
				grade.setSelectedItem(fase.getGrade());

				ArrayList<SubFase> list = fase.getSubFases();

				for (SubFase sub : list) {
					sub.select();
					tableAdd.addRow(String.valueOf(sub.getCodigo()), sub.getSigla(), sub.getNome(), String.valueOf(sub.getGrade()));
				}

				if (OPE == ALT) {
					controlView.setControl(CAMPO_NORMAL, true, false);

					grade.setEnabled(false);
				} else {
					controlView.setControl(CAMPO_NORMAL, false, false);
				}

				return true;
			}

			JOptionPane.showMessageDialog(null, "Registro não encontrado", "Fases", JOptionPane.ERROR_MESSAGE);

			controlView.setControl(CAMPO_NORMAL, false, true);
			codigo.requestFocus();

			return false;

		}

	}

}
