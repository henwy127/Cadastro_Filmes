package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AtorView extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField nomeTextField;
    private JTextField idTextField;

    private JButton novoButton;
    private JButton salvarButton;
    private JButton excluirButton;

    private JTable atoresTable;
    private DefaultTableModel atoresTableModel;

    private static final Color DARK_BG = new Color(30, 30, 30);
    private static final Color DARK_PANEL = new Color(45, 45, 45);
    private static final Color DARK_FIELD = new Color(55, 55, 55);
    private static final Color DARK_TEXT = new Color(235, 235, 235);
    private static final Color DARK_ACCENT = new Color(70, 130, 180);
    private static final Color DARK_DANGER = new Color(150, 60, 60);

    public AtorView() {

        setLayout(null);
        setBackground(DARK_BG);

        JLabel tituloPaginaLabel = new JLabel("Cadastro de Atores");
        tituloPaginaLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        tituloPaginaLabel.setBounds(150, 20, 240, 30);
        tituloPaginaLabel.setForeground(DARK_TEXT);
        add(tituloPaginaLabel);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(70, 90, 70, 25);
        idLabel.setForeground(DARK_TEXT);
        add(idLabel);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(70, 130, 70, 25);
        nomeLabel.setForeground(DARK_TEXT);
        add(nomeLabel);

        idTextField = new JTextField();
        idTextField.setBounds(145, 92, 260, 25);
        idTextField.setEditable(false);
        idTextField.setEnabled(false);
        idTextField.setBackground(DARK_FIELD);
        idTextField.setForeground(DARK_TEXT);
        idTextField.setDisabledTextColor(DARK_TEXT);
        add(idTextField);

        nomeTextField = new JTextField();
        nomeTextField.setBounds(145, 132, 260, 25);
        nomeTextField.setBackground(DARK_FIELD);
        nomeTextField.setForeground(DARK_TEXT);
        nomeTextField.setCaretColor(DARK_TEXT);
        add(nomeTextField);

        novoButton = new JButton("Novo");
        novoButton.setBounds(85, 190, 100, 30);
        novoButton.setBackground(DARK_ACCENT);
        novoButton.setForeground(Color.WHITE);
        add(novoButton);

        salvarButton = new JButton("Salvar");
        salvarButton.setBounds(205, 190, 100, 30);
        salvarButton.setBackground(DARK_ACCENT);
        salvarButton.setForeground(Color.WHITE);
        add(salvarButton);

        excluirButton = new JButton("Excluir");
        excluirButton.setBounds(325, 190, 100, 30);
        excluirButton.setBackground(DARK_DANGER);
        excluirButton.setForeground(Color.WHITE);
        add(excluirButton);

        atoresTableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        atoresTable = new JTable(atoresTableModel);
        atoresTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        atoresTable.setBounds(30, 245, 455, 105);
        atoresTable.setBackground(DARK_PANEL);
        atoresTable.setForeground(DARK_TEXT);
        atoresTable.setSelectionBackground(DARK_ACCENT);
        atoresTable.setSelectionForeground(Color.WHITE);
        atoresTable.setGridColor(Color.DARK_GRAY);
        atoresTable.getTableHeader().setBackground(DARK_FIELD);
        atoresTable.getTableHeader().setForeground(DARK_TEXT);

        add(atoresTable);
    }

    public void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro no cadastro de ator",
                JOptionPane.ERROR_MESSAGE);
    }

    public JTextField getNomeTextField() {
        return nomeTextField;
    }

    public JTextField getIDtextField() {
        return idTextField;
    }

    public JButton getNovoButton() {
        return novoButton;
    }

    public JButton getSalvarButton() {
        return salvarButton;
    }

    public JButton getExcluirButton() {
        return excluirButton;
    }

    public JTable getAtoresTable() {
        return atoresTable;
    }

    public DefaultTableModel getAtoresTableModel() {
        return atoresTableModel;
    }
}