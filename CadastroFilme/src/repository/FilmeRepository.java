package repository;

import java.util.ArrayList;
import java.util.*;

import model.Filme;
import model.Genero;
import model.Atores;

public class FilmeRepository {
      private final List<Filme> filmes = new ArrayList<>();
      private Integer proximoId = 1;
      
      public void adicionar(Filme filme) {
    	  filme.setId(proximoId);
    	  proximoId++;
    	  
    	  filmes.add(filme);
      }
      public List<Filme> listarTodos(){
    	  return filmes;
      }
      
      public boolean GeneroUtil(Genero genero) {
      	return filmes.stream()
      			.anyMatch(f-> f.getGenero().getId() == genero.getId());
      }
      
      public boolean AtorUtil(Atores ator) {

    	    return filmes.stream()
    	            .anyMatch(f -> 
    	                f.getAtores().stream()
    	                .anyMatch(a -> a.getId() == ator.getId())
    	            );
    	}
      
      public Filme buscarPorId(Integer id) {

    	    for (Filme filme : filmes) {
    	        if (filme.getId() == id) {
    	            return filme;
    	        }
    	    }

    	    throw new RuntimeException("Filme não encontrado.");
    	}
      
      public void atualizar(Filme filme) {
          Optional<Filme> encontrado = filmes.stream()
        		  .filter(f -> f.getId() == filme.getId())
                  .findFirst();

          if (encontrado.isPresent()) {
              Filme existente = encontrado.get();
              existente.setTitulo(filme.getTitulo());
              existente.setGenero(filme.getGenero());
              existente.setDuracao(filme.getDuracao());
              existente.setAtores(filme.getAtores());
          } else {
              throw new RuntimeException("Filme não encontrado para atualização.");
          }
      }
      
      public void excluirPorId(Integer id) {
    	    filmes.removeIf(f -> f.getId().equals(id));
    	}
}
 