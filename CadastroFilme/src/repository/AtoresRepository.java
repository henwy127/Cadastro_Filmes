package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Atores;
import model.Filme;



public class AtoresRepository {
      private final List<Atores> atores = new ArrayList<>();
      private Integer proximoId = 1;
      
      public void adicionar(Atores ator) {
    	  ator.setId(proximoId);
    	  proximoId++;
    	  
    	  atores.add(ator);
      }
      
      public List<Atores> listarTodos(){
    	  return atores;
      }
      
      public boolean existeNome(String nome, Integer idIgnorado) {

    	    for (Atores ator : atores) {

    	        if (ator.getNome().equalsIgnoreCase(nome.trim())
    	                && ator.getId() != idIgnorado) {
    	            return true;
    	        }

    	    }

    	    return false;
    	}
      
      public boolean existeNome(String nome) {

    	    for (Atores ator : atores) {

    	        if (ator.getNome().equalsIgnoreCase(nome.trim())) {
    	            return true;
    	        }

    	    }

    	    return false;
    	}
      
      public Atores buscarPorId(Integer id) {

  	    for (Atores ator : atores) {
  	        if (ator.getId() == id) {
  	            return ator;
  	        }
  	    }

  	    throw new RuntimeException("Ator não encontrado.");
  	}
    
      
      public void atualizar(Atores ator) {
          Optional<Atores> encontrado = atores.stream()
        		  .filter(a -> a.getId() == ator.getId())
                  .findFirst();

          if (encontrado.isPresent()) {
              Atores existente = encontrado.get();
              existente.setNome(ator.getNome());
              
          } else {
              throw new RuntimeException("Ator não encontrado para atualização.");
          }
      }
      
      public void excluirPorId(Integer id) {
          boolean removido = atores.removeIf(a -> a.getId() == id);
          if (!removido) {
              throw new RuntimeException("Ator não encontrado para exclusão.");
          }
      }
}
