package core;

import javax.swing.SwingUtilities;

import controller.FilmeController;
import controller.GeneroController;
import repository.FilmeRepository;
import repository.GeneroRepository;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();

            FilmeRepository filmeRepository = new FilmeRepository();
            new FilmeController(mainView.getFilmeView(), filmeRepository);

            GeneroRepository generoRepository = new GeneroRepository();
            new GeneroController(mainView.getGeneroView(), generoRepository);

            mainView.setVisible(true);
        });
    }
}