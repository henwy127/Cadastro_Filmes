package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.table.DefaultTableModel;

import model.Genero;
import model.Atores;

public class FilmeView extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField tituloTextField;
    private JComboBox<Genero> generoComboBox;
    private JTable filmestable;
    private JButton novoButton;
    private JButton salvarButton;
    private JButton excluirButton;
    private JSpinner duracaoSpinner;
    private DefaultTableModel filmesTableModel;
    private JTextField IDtextField;
    private JList<Atores> atoresList;
    private DefaultListModel<Atores> atoresListModel;

    private static final Color DARK_BG = new Color(30, 30, 30);
    private static final Color DARK_PANEL = new Color(45, 45, 45);
    private static final Color DARK_FIELD = new Color(55, 55, 55);
    private static final Color DARK_TEXT = new Color(235, 235, 235);
    private static final Color DARK_ACCENT = new Color(70, 130, 180);
    private static final Color DARK_DANGER = new Color(150, 60, 60);

    public FilmeView() {
        setLayout(null);
        setBackground(DARK_BG);

        JLabel tituloPaginaLabel = new JLabel("Cadastro de Filme");
        tituloPaginaLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        tituloPaginaLabel.setBounds(155, 20, 220, 30);
        tituloPaginaLabel.setForeground(DARK_TEXT);
        add(tituloPaginaLabel);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(70, 80, 70, 25);
        idLabel.setForeground(DARK_TEXT);
        add(idLabel);

        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(70, 115, 70, 25);
        tituloLabel.setForeground(DARK_TEXT);
        add(tituloLabel);

        JLabel generoLabel = new JLabel("Gênero:");
        generoLabel.setBounds(70, 150, 70, 25);
        generoLabel.setForeground(DARK_TEXT);
        add(generoLabel);

        JLabel duracaoLabel = new JLabel("Duração:");
        duracaoLabel.setBounds(70, 185, 70, 25);
        duracaoLabel.setForeground(DARK_TEXT);
        add(duracaoLabel);

        IDtextField = new JTextField();
        IDtextField.setBounds(145, 82, 260, 25);
        IDtextField.setEnabled(false);
        IDtextField.setEditable(false);
        IDtextField.setBackground(DARK_FIELD);
        IDtextField.setForeground(DARK_TEXT);
        IDtextField.setDisabledTextColor(DARK_TEXT);
        add(IDtextField);

        tituloTextField = new JTextField();
        tituloTextField.setBounds(145, 117, 260, 25);
        tituloTextField.setBackground(DARK_FIELD);
        tituloTextField.setForeground(DARK_TEXT);
        tituloTextField.setCaretColor(DARK_TEXT);
        add(tituloTextField);

        generoComboBox = new JComboBox<>();

        generoComboBox.setBounds(145, 152, 260, 25);
        generoComboBox.setBackground(DARK_FIELD);
        generoComboBox.setForeground(DARK_TEXT);

        generoComboBox.setUI(new BasicComboBoxUI() {

            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();

                button.setBackground(new Color(230, 230, 230)); // branco suave
                button.setForeground(Color.DARK_GRAY);
                button.setBorder(null);

                return button;
            }
        });

        add(generoComboBox);
        
        duracaoSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        duracaoSpinner.setBounds(145, 187, 100, 25);
        duracaoSpinner.setBackground(DARK_FIELD);
        duracaoSpinner.setForeground(DARK_TEXT);
        add(duracaoSpinner);

        if (duracaoSpinner.getEditor() instanceof DefaultEditor editor) {
            editor.getTextField().setBackground(DARK_FIELD);
            editor.getTextField().setForeground(DARK_TEXT);
            editor.getTextField().setCaretColor(DARK_TEXT);
        }

        JLabel atoresLabel = new JLabel("Atores:");
        atoresLabel.setBounds(70, 220, 70, 25);
        atoresLabel.setForeground(DARK_TEXT);
        add(atoresLabel);

        atoresListModel = new DefaultListModel<>();

        atoresList = new JList<>(atoresListModel);
        atoresList.setBackground(DARK_FIELD);
        atoresList.setForeground(DARK_TEXT);
        atoresList.setSelectionBackground(DARK_ACCENT);
        atoresList.setSelectionForeground(Color.WHITE);
        atoresList.setVisibleRowCount(5);
        atoresList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        
        JScrollPane scrollAtores = new JScrollPane(atoresList);
        scrollAtores.setBounds(145, 222, 260, 90);
        add(scrollAtores);
        
        novoButton = new JButton("Novo");
        novoButton.setBounds(85, 235, 100, 30);
        novoButton.setBackground(DARK_ACCENT);
        novoButton.setForeground(Color.WHITE);
        add(novoButton);

        salvarButton = new JButton("Salvar");
        salvarButton.setBounds(205, 235, 100, 30);
        salvarButton.setBackground(DARK_ACCENT);
        salvarButton.setForeground(Color.WHITE);
        add(salvarButton);

        excluirButton = new JButton("Excluir");
        excluirButton.setBounds(325, 235, 100, 30);
        excluirButton.setBackground(DARK_DANGER);
        excluirButton.setForeground(Color.WHITE);
        add(excluirButton);

        novoButton.setBounds(85, 330, 100, 30);
        salvarButton.setBounds(205, 330, 100, 30);
        excluirButton.setBounds(325, 330, 100, 30);
        
        filmesTableModel = new DefaultTableModel(
        	    new Object[] { "ID", "Título", "Gênero", "Duração", "Qtd. Atores" }, 0) {
        	
        	@Override
            public boolean isCellEditable(int row, int column) {
        		 return false;
            }
        };
        
        
        
        filmestable = new JTable(filmesTableModel);
        filmestable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        filmestable.setBounds(30, 380, 455, 120);
        filmestable.setBackground(DARK_PANEL);
        filmestable.setForeground(DARK_TEXT);
        filmestable.setSelectionBackground(DARK_ACCENT);
        filmestable.setSelectionForeground(Color.WHITE);
        add(filmestable);
    }

    public JList<Atores> getAtoresList() {
		return atoresList;
	}

	public void setAtoresList(JList<Atores> atoresList) {
		this.atoresList = atoresList;
	}

	public DefaultListModel<Atores> getAtoresListModel() {
		return atoresListModel;
	}

	public void setAtoresListModel(DefaultListModel<Atores> atoresListModel) {
		this.atoresListModel = atoresListModel;
	}

	public void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro no cadastro de filme", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getNovoButton() { return novoButton; }
    public JButton getSalvarButton() { return salvarButton; }
    public JButton getExcluirButton() { return excluirButton; }
    public JTextField getTituloTextField() { return tituloTextField; }
    public JComboBox<Genero> getGeneroComboBox() { return generoComboBox; }
    public JSpinner getDuracaoSpinner() { return duracaoSpinner; }
    public JTable getFilmestable() { return filmestable; }
    public DefaultTableModel getFilmesTableModel() { return filmesTableModel; }
    public JTextField getTextField() { return IDtextField; }

	public JTextField getIDtextField() {
		return IDtextField;
	}

	public void setIDtextField(JTextField iDtextField) {
		IDtextField = iDtextField;
	}
    
}