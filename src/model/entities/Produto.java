package model.entities;

import java.io.Serializable;

import model.services.ProdutoService;

public class Produto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idProduto;
	private String nomeProduto;

	public Produto() {
	}

	public Produto(Integer idProduto, String nomeProduto) {
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProduto;
		result = prime * result + ((nomeProduto == null) ? 0 : nomeProduto.hashCode());
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
		Produto other = (Produto) obj;
		if (idProduto != other.idProduto)
			return false;
		if (nomeProduto == null) {
			if (other.nomeProduto != null)
				return false;
		} else if (!nomeProduto.equals(other.nomeProduto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id Produto=" + idProduto + ", Produto=" + nomeProduto + "]";
	}
	
	
	public String apenasNomeProduto(Integer id) {

	Produto produto = new Produto();
	produto = new ProdutoService().buscarPeloId(id);

	String nome = produto.getNomeProduto();

		return nome;

	}
	
	public String nomeProduto(Integer id) {

		Produto produto = new Produto();
		produto = new ProdutoService().buscarPeloId(id);

	String nome = id + " - " + produto.getNomeProduto();

		return nome;

	}
	
	public Integer codProduto(String nome) {

		String cod = nome.substring(0, nome.indexOf("-")).trim();

		return Integer.valueOf(cod);
	}

}
