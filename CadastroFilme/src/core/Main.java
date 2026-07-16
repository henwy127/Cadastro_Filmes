package core;

import javax.swing.SwingUtilities;

import controller.AtoresController;
import controller.FilmeController;
import controller.GeneroController;
import repository.AtoresRepository;
import repository.FilmeRepository;
import repository.GeneroRepository;
import view.FilmeView;
import view.GeneroView;
import view.MainView;
import view.AtorView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	MainView mainView = new MainView();
        	
            GeneroRepository generoRepository = new GeneroRepository();
            FilmeRepository filmeRepository = new FilmeRepository();
            AtoresRepository atoresRepository = new AtoresRepository();
            
            FilmeController filmeController =
                    new FilmeController(
                            mainView.getFilmeView(),
                            filmeRepository,
                            generoRepository,
                            atoresRepository);
            
            AtoresController atoresController =
            	    new AtoresController(
            	        mainView.getAtorView(),
            	        atoresRepository,
            	        filmeController,
            	        filmeRepository
            	    );

  
            GeneroController generoController =
                    new GeneroController(
                            mainView.getGeneroView(),
                            generoRepository,
                            filmeController,
                            filmeRepository);

            mainView.setVisible(true);
        });
    }
}