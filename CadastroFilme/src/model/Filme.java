package model;

import java.util.List;

public class Filme {
	private Integer id;
    private String titulo;
    private Genero genero;
    private Integer duracao;
    private List<Atores> Atores;
    
    
	public List<Atores> getAtores() {
		return Atores;
	}

	public void setAtores(List<Atores> atores) {
		Atores = atores;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	public Integer getDuracao() {
		return duracao;
	}
	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
     
    
    
}
