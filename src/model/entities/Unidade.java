package model.entities;

import java.io.Serializable;

import model.services.UnidadeService;

public class Unidade implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idUnidade;
	private String nomeUnidade;

	public Unidade() {
	}

	public Unidade(Integer idUnidade, String nomeUnidade) {
		this.idUnidade = idUnidade;
		this.nomeUnidade = nomeUnidade;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idUnidade;
		result = prime * result + ((nomeUnidade == null) ? 0 : nomeUnidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unidade other = (Unidade) obj;
		if (idUnidade != other.idUnidade)
			return false;
		if (nomeUnidade == null) {
			if (other.nomeUnidade != null)
				return false;
		} else if (!nomeUnidade.equals(other.nomeUnidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unidade [id Unidade=" + idUnidade + ", Unidade=" + nomeUnidade + "]";
	}
	
	public String cbUnidade(int id) {

		Unidade unidade = new Unidade();
		unidade = new UnidadeService().buscarPeloId(id);

		String contatenado = unidade.getIdUnidade() + " - " + unidade.getNomeUnidade();

		return contatenado;

	}
	
	public String nomeUnidade(int id) {

		Unidade unidade = new Unidade();
		unidade = new UnidadeService().buscarPeloId(id);

		String contatenado = unidade.getNomeUnidade();

		return contatenado;

	}
	
	public Integer codUnidade(String nome) {

		String cod = nome.substring(0, nome.indexOf("-")).trim();

		return Integer.valueOf(cod);
	}

}
