package controller;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Filme;
import repository.FilmeRepository;
import view.FilmeView;


public class FilmeController {
	private FilmeView view;
	private FilmeRepository repository;

	public FilmeController(FilmeView view, FilmeRepository repository) {
		super();
		this.view = view;
		this.repository = repository;
		
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

public void carregarFilmeSelecionado() {
	int LinhaSelecionada = view.getFilmestable().getSelectedRow();
	
	if (LinhaSelecionada < 0) {
        return;
    }

	 int linhaModelo = view.getFilmestable().convertRowIndexToModel(LinhaSelecionada);
	    Integer id = (Integer) view.getFilmesTableModel().getValueAt(linhaModelo, 0);
	    String titulo = (String) view.getFilmesTableModel().getValueAt(linhaModelo, 1);
	    String genero = (String) view.getFilmesTableModel().getValueAt(linhaModelo, 2);
	    Integer duracao = (Integer) view.getFilmesTableModel().getValueAt(linhaModelo, 3);
	
	    view.getTextField().setText(id != null ? String.valueOf(id) : "");
	    view.getTituloTextField().setText(titulo != null ? titulo : "");
	    view.getGeneroTextField().setText(genero != null ? genero : "");
	    view.getDuracaoSpinner().setValue(duracao != null ? duracao : 0);
}
	
	public void salvarFilme(){
		Filme filme = new Filme();
		
		String idTexto = view.getTextField().getText().trim();
		String titulo = view.getTituloTextField().getText();
        String genero = view.getGeneroTextField().getText();
        
        if(titulo == null || titulo.isBlank()) {
        	throw new IllegalArgumentException("Preencha o título corretamente.");
        }
        
        if(genero == null || genero.isBlank()) {
        	throw new IllegalArgumentException("Preencha o gênero corretamente.");
        }
        
		filme.setTitulo(titulo);
		filme.setGenero(genero);
		filme.setDuracao((Integer)view.getDuracaoSpinner().getValue());
		
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
		this.view.getGeneroTextField().setText("");
		this.view.getDuracaoSpinner().setValue(0);
		this.view.getIDtextField().setText(null);
	}
	
	public void montarTabela() {
		view.getFilmesTableModel().setRowCount(0);
		
		for(Filme f : repository.listarTodos()) {
			view.getFilmesTableModel().addRow(
					new Object[] {f.getId(), f.getTitulo(), f.getGenero(), f.getDuracao()}
					
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
