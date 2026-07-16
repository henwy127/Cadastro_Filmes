package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Point initialClick;

    private FilmeView filmeView;
    private GeneroView generoView;
    private AtorView atorView;

    public MainView() {
        setUndecorated(true); 
        setSize(560, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Color DARK_BG = new Color(30, 30, 30);
        Color BAR_BG = new Color(20, 20, 20);
        Color TEXT = new Color(235, 235, 235);
        Color BTN_HOVER_CLOSE = new Color(180, 50, 50);
        Color BTN_HOVER_MIN = new Color(70, 130, 180);

        // =========================
        // Barra de título customizada
        // =========================
        JPanel titleBar = new JPanel(null);
        titleBar.setBackground(BAR_BG);
        titleBar.setBounds(0, 0, 560, 32);

        JLabel title = new JLabel("Cadastro de Filmes");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 13));
        title.setBounds(12, 0, 250, 32);
        titleBar.add(title);

        JButton btnMin = new JButton("—");
        btnMin.setFocusPainted(false);
        btnMin.setBorderPainted(false);
        btnMin.setContentAreaFilled(false);
        btnMin.setForeground(TEXT);
        btnMin.setBounds(480, 0, 40, 32);
        btnMin.setHorizontalAlignment(SwingConstants.CENTER);
        btnMin.addActionListener(e -> setState(JFrame.ICONIFIED));
        btnMin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnMin.setOpaque(true);
                btnMin.setBackground(BTN_HOVER_MIN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnMin.setOpaque(false);
                btnMin.setBackground(null);
            }
        });
        titleBar.add(btnMin);

        JButton btnClose = new JButton("X");
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setForeground(TEXT);
        btnClose.setBounds(520, 0, 40, 32);
        btnClose.setHorizontalAlignment(SwingConstants.CENTER);
        btnClose.addActionListener(e -> dispose());
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setOpaque(true);
                btnClose.setBackground(BTN_HOVER_CLOSE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setOpaque(false);
                btnClose.setBackground(null);
            }
        });
        titleBar.add(btnClose);

        // Arrastar janela pela barra
        MouseAdapter dragListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                setLocation(thisX + xMoved, thisY + yMoved);
            }
        };
        titleBar.addMouseListener(dragListener);
        titleBar.addMouseMotionListener(dragListener);
        add(titleBar);

        // =========================
        // Conteúdo principal
        // =========================
        JPanel body = new JPanel(null);
        body.setBackground(DARK_BG);
        body.setBounds(0, 32, 560, 568);

        JTabbedPane abas = new JTabbedPane();
        abas.setBounds(10, 10, 540, 548);
        abas.setBackground(new Color(45, 45, 45));
        abas.setForeground(new Color(235, 235, 235));

        filmeView = new FilmeView();
        generoView = new GeneroView();
        atorView = new AtorView();

        abas.addTab("Filmes", filmeView);
        abas.addTab("Gêneros", generoView);
        abas.addTab("Atores", atorView);

        body.add(abas);
        add(body);
    }

    public FilmeView getFilmeView() {
        return filmeView;
    }

    public GeneroView getGeneroView() {
        return generoView;
    }

    public AtorView getAtorView() {
        return atorView;
    }

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));  //Não funciona completamente, somente a parte visual
    }
}