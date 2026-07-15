package repository;

import java.util.ArrayList;
import java.util.*;

import model.Filme;

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
      
      public void atualizar(Filme filme) {
          Optional<Filme> encontrado = filmes.stream()
        		  .filter(f -> f.getId() == filme.getId())
                  .findFirst();

          if (encontrado.isPresent()) {
              Filme existente = encontrado.get();
              existente.setTitulo(filme.getTitulo());
              existente.setGenero(filme.getGenero());
              existente.setDuracao(filme.getDuracao());
          } else {
              throw new RuntimeException("Filme não encontrado para atualização.");
          }
      }
      
      public void excluirPorId(Integer id) {
    	    filmes.removeIf(f -> f.getId().equals(id));
    	}
}
 