package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Filme;
import model.Genero;

public class GeneroRepository {

    private final List<Genero> generos = new ArrayList<>();
    private int proximoId = 1;

    public void salvar(Genero genero) {
        genero.setId(proximoId++);
        generos.add(genero);
    }

    public boolean existeNome(String nome, Integer idIgnorado) {

        for (Genero genero : generos) {

            if (genero.getNome().equalsIgnoreCase(nome.trim())
                    && genero.getId() != idIgnorado) {
                return true;
            }

        }

        return false;
    }
    
    public boolean existeNome(String nome) {

        for (Genero genero : generos) {

            if (genero.getNome().equalsIgnoreCase(nome.trim())) {
                return true;
            }

        }

        return false;
    }
    
    public Genero buscaPorId(Integer id) {
    	for (Genero genero : generos) {
	        if (genero.getId() == id) {
	            return genero;
	        }
	    }
	    throw new RuntimeException("Gênero não encontrado.");
	}
    
    
    public void atualizar(Genero genero) {
        Optional<Genero> encontrado = generos.stream()
        		.filter(g -> g.getId() == genero.getId())
                .findFirst();

        if (encontrado.isPresent()) {
            encontrado.get().setNome(genero.getNome());
        } else {
            throw new RuntimeException("Gênero não encontrado para atualização.");
        }
    }

    public void excluirPorId(Integer id) {
        boolean removido = generos.removeIf(g -> g.getId() == id);
        if (!removido) {
            throw new RuntimeException("Gênero não encontrado para exclusão.");
        }
    }

    public List<Genero> listarTodos() {
        return new ArrayList<>(generos);
    }
}