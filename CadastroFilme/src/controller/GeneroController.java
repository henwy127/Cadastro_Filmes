package controller;

import javax.swing.JOptionPane;

import model.Genero;
import repository.GeneroRepository;
import view.GeneroView;

public class GeneroController {

    private final GeneroView view;
    private final GeneroRepository repository;

    public GeneroController(GeneroView view, GeneroRepository repository) {
        this.view = view;
        this.repository = repository;

        configurarEventos();
        montarTabela();
        novoGenero();
    }

    private void configurarEventos() {
        view.getNovoButton().addActionListener(e -> novoGenero());
        view.getSalvarButton().addActionListener(e -> salvarGenero());
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
            view.mostrarErro("O nome do gênero é obrigatório.");
            return;
        }

        try {
            if (idTexto.isEmpty()) {
                Genero novo = new Genero();
                novo.setNome(nome);
                repository.salvar(novo);
            } else {
                Integer id = Integer.parseInt(idTexto);
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
            repository.excluirPorId(id);
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