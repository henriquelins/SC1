package model.entities;

import java.io.Serializable;

public class ProdutoDoServico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_produtoDoServico;
	private String nomeDoProduto;
	private String codProdutoDoServico;
	private String setorProdutoDoServico;
	private String descricaorodutoDoServico;
	private double valorUnitarioDoProduto;
	private int quantidadeDoProduto;

	public ProdutoDoServico() {
	}

	public ProdutoDoServico(int id_produtoDoServico, String nomeDoProduto, String codProdutoDoServico,
			String setorProdutoDoServico, String descricaorodutoDoServico, double valorUnitarioDoProduto,
			int quantidadeDoProduto) {
		this.id_produtoDoServico = id_produtoDoServico;
		this.nomeDoProduto = nomeDoProduto;
		this.codProdutoDoServico = codProdutoDoServico;
		this.setorProdutoDoServico = setorProdutoDoServico;
		this.descricaorodutoDoServico = descricaorodutoDoServico;
		this.valorUnitarioDoProduto = valorUnitarioDoProduto;
		this.quantidadeDoProduto = quantidadeDoProduto;
	}

	public int getId_produtoDoServico() {
		return id_produtoDoServico;
	}

	public void setId_produtoDoServico(int id_produtoDoServico) {
		this.id_produtoDoServico = id_produtoDoServico;
	}

	public String getNomeDoProduto() {
		return nomeDoProduto;
	}

	public void setNomeDoProduto(String nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
	}

	public String getCodProdutoDoServico() {
		return codProdutoDoServico;
	}

	public void setCodProdutoDoServico(String codProdutoDoServico) {
		this.codProdutoDoServico = codProdutoDoServico;
	}

	public String getSetorProdutoDoServico() {
		return setorProdutoDoServico;
	}

	public void setSetorProdutoDoServico(String setorProdutoDoServico) {
		this.setorProdutoDoServico = setorProdutoDoServico;
	}

	public String getDescricaorodutoDoServico() {
		return descricaorodutoDoServico;
	}

	public void setDescricaorodutoDoServico(String descricaorodutoDoServico) {
		this.descricaorodutoDoServico = descricaorodutoDoServico;
	}

	public double getValorUnitarioDoProduto() {
		return valorUnitarioDoProduto;
	}

	public void setValorUnitarioDoProduto(double valorUnitarioDoProduto) {
		this.valorUnitarioDoProduto = valorUnitarioDoProduto;
	}

	public int getQuantidadeDoProduto() {
		return quantidadeDoProduto;
	}

	public void setQuantidadeDoProduto(int quantidadeDoProduto) {
		this.quantidadeDoProduto = quantidadeDoProduto;
	}

	@Override
	public String toString() {
		return "\n  NOME DO PRODUTO:  " + nomeDoProduto + "\n\n" + "  CÓDIGO DO PRODUTO:  " + codProdutoDoServico + "\n\n"
				+ "  SETOR:  " + setorProdutoDoServico + "\n\n" + "  DESCRIÇÃO:  " + descricaorodutoDoServico + "\n\n"
				+ "  VALOR UNITÁRIO:  " + valorUnitarioDoProduto + "\n\n" 
				+ "  QUANTIDADE: " + quantidadeDoProduto;
	}

}
