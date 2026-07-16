package controller;

import javax.swing.JOptionPane;

import model.Genero;
import repository.FilmeRepository;
import repository.GeneroRepository;
import view.GeneroView;

public class GeneroController {

    private final GeneroView view;
    private final GeneroRepository repository;
    private FilmeController filmeController;
    private FilmeRepository filmeRepository;
    

    public GeneroController(
            GeneroView view,
            GeneroRepository repository,
            FilmeController filmeController,
            FilmeRepository filmeRepository) {

        this.view = view;
        this.repository = repository;
        this.filmeController = filmeController;
        this.filmeRepository = filmeRepository;

        configurarEventos();
        montarTabela();
        novoGenero();
    }

    private void configurarEventos() {
        view.getNovoButton().addActionListener(e -> novoGenero());
        view.getSalvarButton().addActionListener(e -> {
            try {
                salvarGenero();
            } catch (IllegalArgumentException ex) {
                view.mostrarErro(ex.getMessage());
            } catch (Exception ex) {
                view.mostrarErro("Erro inesperado ao salvar gênero.");
            }
        });
        view.getExcluirButton().addActionListener(e -> excluirGenero());

        view.getGenerosTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            carregarGeneroSelecionado();
        });
    }

    private void limparCampos() {
        view.getIdTextField().setText("");
        view.getNomeTextField().setText("");
    }
 
    public void novoGenero() {
        limparCampos();
        view.getGenerosTable().clearSelection();
        view.getNomeTextField().requestFocus();
    }

    public void salvarGenero() {
        String idTexto = view.getIdTextField().getText().trim();
        String nome = view.getNomeTextField().getText().trim();

        if (nome.isEmpty()) {
        	throw new IllegalArgumentException("O nome do gênero é obrigatório.");
        }

        try {
            if (idTexto.isEmpty()) {
            	
            	if (repository.existeNome(nome)) {
            		throw new IllegalArgumentException("Já existe um gênero com esse nome.");
                }
            	
                Genero novo = new Genero();
                novo.setNome(nome);
                repository.salvar(novo);
                
                filmeController.atualizarComboGeneros();
            } else {
            	
                Integer id = Integer.parseInt(idTexto);
                
                if (repository.existeNome(nome, id)) {
                	throw new IllegalArgumentException("Já existe um gênero com esse nome.");
                }
                
                Genero existente = new Genero();
                existente.setId(id);
                existente.setNome(nome);
                repository.atualizar(existente);
            }

            montarTabela();
            novoGenero();
            JOptionPane.showMessageDialog(view, "Gênero salvo com sucesso!");
        } catch (NumberFormatException ex) {
            view.mostrarErro("ID inválido.");
        } catch (Exception ex) {
            view.mostrarErro("Erro ao salvar gênero: " + ex.getMessage());
        }
    }

    public void excluirGenero() {
        int linhaSelecionada = view.getGenerosTable().getSelectedRow();

        if (linhaSelecionada < 0) {
            view.mostrarErro("É necessário um gênero selecionado para exclusão.");
            return;
        }

        int linhaModelo = view.getGenerosTable().convertRowIndexToModel(linhaSelecionada);
        Integer id = (Integer) view.getGenerosTableModel().getValueAt(linhaModelo, 0);

        if (id == null) {
            view.mostrarErro("ID inválido para exclusão.");
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(
            view,
            "Tem certeza que deseja excluir o gênero de ID " + id + "?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (opcao != JOptionPane.YES_OPTION) return;

        try {
        	
        	Genero genero = repository.buscaPorId(id);
        	
        	if(filmeRepository.GeneroUtil(genero)) {
        		throw new IllegalArgumentException("Não é possível excluir esse gênero pois ele está sendo usado por um filme.");
        	}
        	
            repository.excluirPorId(id);
            filmeController.atualizarComboGeneros();
            montarTabela();
            novoGenero();
            JOptionPane.showMessageDialog(view, "Gênero excluído com sucesso!");
        } catch (Exception ex) {
            view.mostrarErro("Erro ao excluir gênero: " + ex.getMessage());
        }
    }

    public void carregarGeneroSelecionado() {
        int linhaSelecionada = view.getGenerosTable().getSelectedRow();
        if (linhaSelecionada < 0) return;

        int linhaModelo = view.getGenerosTable().convertRowIndexToModel(linhaSelecionada);

        Integer id = (Integer) view.getGenerosTableModel().getValueAt(linhaModelo, 0);
        String nome = (String) view.getGenerosTableModel().getValueAt(linhaModelo, 1);

        view.getIdTextField().setText(id != null ? String.valueOf(id) : "");
        view.getNomeTextField().setText(nome != null ? nome : "");
    }

    public void montarTabela() {
        view.getGenerosTableModel().setRowCount(0);

        for (Genero g : repository.listarTodos()) {
            view.getGenerosTableModel().addRow(new Object[] {
                g.getId(),
                g.getNome()
            });
        }
    }
}