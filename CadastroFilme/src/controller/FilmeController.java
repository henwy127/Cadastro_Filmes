package controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Atores;
import model.Filme;
import model.Genero;
import repository.AtoresRepository;
import repository.FilmeRepository;
import repository.GeneroRepository;
import view.FilmeView;


public class FilmeController {
	private FilmeView view;
	private FilmeRepository repository;
	private GeneroRepository generoRepository;
	private AtoresRepository atoresRepository;

	public FilmeController(FilmeView view,
			FilmeRepository repository,
			GeneroRepository generoRepository,
			AtoresRepository atoresRepository) {
		super();
		this.view = view;
		this.repository = repository;
		this.generoRepository = generoRepository;
		this.atoresRepository = atoresRepository;
		
		carregarAtores();
		carregarGeneros();
		configurarEventos();
	}
	
	public void configurarEventos() {
		this.view.
		getSalvarButton().addActionListener(e -> {
			try {
		        salvarFilme();
		    } catch (IllegalArgumentException ex) {
		        view.mostrarErro(ex.getMessage());
		    } catch (Exception ex) {
		        view.mostrarErro("Erro inesperado ao salvar filme.");
		    }
		});
		
		this.view.
		getNovoButton().addActionListener(e -> novoFilme());
		
		this.view.
		getExcluirButton().addActionListener(e -> excluirFilme());
		
		this.view.getFilmestable().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent e) {
						carregarFilmeSelecionado();
						
					}
				});
		
	}

	private void carregarGeneros() {
	    view.getGeneroComboBox().removeAllItems();

	    for (Genero genero : generoRepository.listarTodos()) {
	        view.getGeneroComboBox().addItem(genero);
	    }
	}
	
	private void carregarAtores() {

	    view.getAtoresListModel().clear();

	    for (Atores ator : atoresRepository.listarTodos()) {
	        view.getAtoresListModel().addElement(ator);
	    }
	}
	
	public void atualizarComboGeneros() {
        carregarGeneros();
    }
	
	public void atualizarListaAtores() {
	    carregarAtores();
	}
	
	public void carregarFilmeSelecionado() {

	    int linhaSelecionada = view.getFilmestable().getSelectedRow();

	    if(linhaSelecionada < 0)
	        return;


	    int linhaModelo =
	        view.getFilmestable().convertRowIndexToModel(linhaSelecionada);

	    

	        Integer id = (Integer)view.getFilmesTableModel().getValueAt(linhaModelo,0);
	        String titulo = (String) view.getFilmesTableModel().getValueAt(linhaModelo, 1);
	        Genero genero = (Genero) view.getFilmesTableModel().getValueAt(linhaModelo, 2);
	        Integer duracao = (Integer) view.getFilmesTableModel().getValueAt(linhaModelo, 3);

	        view.getIDtextField().setText(id != null ? String.valueOf(id) : "");
	        view.getTituloTextField().setText(titulo != null ? titulo : "");
	        view.getGeneroComboBox().setSelectedItem(genero);
	        view.getDuracaoSpinner().setValue(duracao != null ? duracao : 0);
	        
	    Filme filme = repository.buscarPorId(id);

	    view.getTituloTextField()
	        .setText(filme.getTitulo());

	    view.getGeneroComboBox()
	        .setSelectedItem(filme.getGenero());

	    view.getDuracaoSpinner()
	        .setValue(filme.getDuracao());

	    view.getAtoresList().clearSelection();
	    for(Atores ator : filme.getAtores()){

	        int indice =
	            view.getAtoresListModel()
	                .indexOf(ator);

	        if(indice >= 0){
	            view.getAtoresList()
	                .addSelectionInterval(indice, indice);
	        }
	    }
	}
	
	public void salvarFilme(){
		Filme filme = new Filme();
		
		String idTexto = view.getTextField().getText().trim();
		String titulo = view.getTituloTextField().getText();
		Genero genero = (Genero) view.getGeneroComboBox().getSelectedItem();
		
		 List<Atores> atoresSelecionados =
		            view.getAtoresList().getSelectedValuesList();
		
		if(atoresSelecionados.isEmpty()) {
	        throw new IllegalArgumentException(
	            "O filme deve possuir pelo menos um ator."
	        );
	    }
        
        if(titulo == null || titulo.isBlank()) {
        	throw new IllegalArgumentException("Preencha o título corretamente.");
        }
        
        if(genero == null) {
        	throw new IllegalArgumentException("Selecione um gênero para o filme.");
        }
        
		filme.setTitulo(titulo);
		filme.setGenero(genero);
		filme.setDuracao((Integer)view.getDuracaoSpinner().getValue());
		
		 filme.setAtores(
			        view.getAtoresList().getSelectedValuesList()
			    );
		
		if (idTexto.isEmpty()) {
		    repository.adicionar(filme);
		} else {
		    filme.setId(Integer.parseInt(idTexto));
		    repository.atualizar(filme);
		}
		
		limparCampos();
		
		montarTabela();
		
	}
	
	public void limparCampos() {
		this.view.getTituloTextField().setText("");
		this.view.getGeneroComboBox().setSelectedIndex(0);
		this.view.getDuracaoSpinner().setValue(0);
		this.view.getIDtextField().setText(null);
		this.view.getAtoresList().clearSelection();
	}
	
	public void montarTabela() {
		view.getFilmesTableModel().setRowCount(0);
		
		for(Filme f : repository.listarTodos()) {

		    view.getFilmesTableModel().addRow(
		        new Object[] {
		            f.getId(),
		            f.getTitulo(),
		            f.getGenero(),
		            f.getDuracao(),
		            f.getAtores().size()
		        }
		    );
		}
	}
	
	public void novoFilme() {
		 limparCampos();
		 view.getFilmestable().clearSelection();
	}
	
	public void excluirFilme() {
	    int linhaSelecionada = view.getFilmestable().getSelectedRow();

	    if (linhaSelecionada < 0) {
	        view.mostrarErro("Selecione um filme na tabela para excluir.");
	        return;
	    }

	    int linhaModelo = view.getFilmestable().convertRowIndexToModel(linhaSelecionada);
	    Integer id = (Integer) view.getFilmesTableModel().getValueAt(linhaModelo, 0);

	    if (id == null) {
	        view.mostrarErro("ID do filme inválido para exclusão.");
	        return;
	    }

	    int opcao = JOptionPane.showConfirmDialog(
	        view,
	        "Tem certeza que deseja excluir este filme?",
	        "Confirmar exclusão",
	        JOptionPane.YES_NO_OPTION,
	        JOptionPane.WARNING_MESSAGE
	    );

	    if (opcao != JOptionPane.YES_OPTION) {
	        return;
	    }

	    repository.excluirPorId(id);

	    limparCampos();
	    view.getTextField().setText("");
	    view.getFilmestable().clearSelection();
	    montarTabela();

	    JOptionPane.showMessageDialog(view, "Filme excluído com sucesso!");
	}
}
