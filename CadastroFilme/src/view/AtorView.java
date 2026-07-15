package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AtorView extends JPanel {
    private static final long serialVersionUID = 1L;

    public AtorView() {
        setLayout(null);
        setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Cadastro de Atores");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(235, 235, 235));
        lblTitulo.setBounds(160, 40, 250, 30);
        add(lblTitulo);
    }
}