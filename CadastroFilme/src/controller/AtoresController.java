package controller;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Atores;
import repository.AtoresRepository;
import repository.FilmeRepository;
import view.AtorView;


public class AtoresController {

    private AtorView view;
    private AtoresRepository repository;
    private FilmeController filmeController;
    private FilmeRepository filmeRepository;

    public AtoresController(AtorView view,
    		AtoresRepository repository,
    		FilmeController filmeController,
    		FilmeRepository filmeRepository) {
        this.view = view;
        this.repository = repository;
        this.filmeController = filmeController;
        this.filmeRepository = filmeRepository;

        configurarEventos();
    }

    public void configurarEventos() {

        view.getSalvarButton().addActionListener(e -> {
            try {
                salvarAtor();
            } catch (IllegalArgumentException ex) {
                view.mostrarErro(ex.getMessage());
            } catch (Exception ex) {
                view.mostrarErro("Erro inesperado ao salvar ator.");
            }
        });

        view.getNovoButton().addActionListener(e -> novoAtor());

        view.getExcluirButton().addActionListener(e -> {
            try {
                excluirAtor();
            } catch (IllegalArgumentException ex) {
                view.mostrarErro(ex.getMessage());
            } catch (Exception ex) {
                view.mostrarErro(
                    "Erro inesperado ao excluir ator."
                );
            }
        });

        view.getAtoresTable().getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        carregarAtorSelecionado();
                    }
                });
    }

    public void carregarAtorSelecionado() {

        int linhaSelecionada = view.getAtoresTable().getSelectedRow();

        if (linhaSelecionada < 0) {
            return;
        }

        int linhaModelo = view.getAtoresTable().convertRowIndexToModel(linhaSelecionada);

        Integer id = (Integer) view.getAtoresTableModel().getValueAt(linhaModelo, 0);
        String nome = (String) view.getAtoresTableModel().getValueAt(linhaModelo, 1);

        view.getIDtextField().setText(id != null ? String.valueOf(id) : "");
        view.getNomeTextField().setText(nome != null ? nome : "");
    }

    public void salvarAtor() {

        Atores ator = new Atores();

        String idTexto = view.getIDtextField().getText().trim();
        String nome = view.getNomeTextField().getText().trim();

        if (nome.isBlank()) {
            throw new IllegalArgumentException("Preencha o nome do ator.");
        }

        ator.setNome(nome);

        if (idTexto.isEmpty()) {
        	
        	if (repository.existeNome(nome)) {
                throw new IllegalArgumentException("Já existe um ator com esse nome.");
            }
        	
            repository.adicionar(ator);
        } else {
        	
        	int id = Integer.parseInt(idTexto);
        	
        	 if (repository.existeNome(nome, id)) {
        	        throw new IllegalArgumentException("Já existe um ator com esse nome.");
        	    }
        	
            ator.setId(id);
            repository.atualizar(ator);
        }

        limparCampos();
        montarTabela();
        filmeController.atualizarListaAtores();
    }

    
    public void limparCampos() {
        view.getNomeTextField().setText("");
        view.getIDtextField().setText("");
    }

    public void montarTabela() {

        view.getAtoresTableModel().setRowCount(0);

        for (Atores ator : repository.listarTodos()) {

            view.getAtoresTableModel().addRow(
                    new Object[] {
                            ator.getId(),
                            ator.getNome()
                    });
        }
    }

    public void novoAtor() {
        limparCampos();
        view.getAtoresTable().clearSelection();
    }

    public void excluirAtor() {

        int linhaSelecionada = view.getAtoresTable().getSelectedRow();

        if (linhaSelecionada < 0) {
            throw new IllegalArgumentException(
                "Selecione um ator para excluir."
            );
        }

        int linhaModelo = view.getAtoresTable().convertRowIndexToModel(linhaSelecionada);

        Integer id = (Integer) view.getAtoresTableModel().getValueAt(linhaModelo, 0);

        if (id == null) {
            throw new IllegalArgumentException(
                "ID inválido."
            );
        }

        int opcao = JOptionPane.showConfirmDialog(
                view,
                "Tem certeza que deseja excluir este ator?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        Atores ator = repository.buscarPorId(id);
        
        if(filmeRepository.AtorUtil(ator)) {
        	throw new IllegalArgumentException("Não é possível excluir esse ator pois ele está em um filme.");
        }
        
        repository.excluirPorId(id);

        limparCampos();
        filmeController.atualizarListaAtores();
        view.getAtoresTable().clearSelection();
        montarTabela();

        JOptionPane.showMessageDialog(view, "Ator excluído com sucesso!");
    }
}
