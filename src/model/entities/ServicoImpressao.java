package model.entities;

import java.io.Serializable;

public class ServicoImpressao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idServicoImpressao;
	private Integer idCliente;
	private String nomeDoServico;
	private Integer produtoDoServico;
	private String observacoesServico;
	private Integer limiteMinimo;
	private Double valorUnitario;
	private Conta conta;

	public ServicoImpressao() {
	}

	public ServicoImpressao(Integer idServicoImpressao, Integer idCliente, String nomeDoServico,
			Integer produtoDoServico, String observacoesServico, Integer limiteMinimo, Double valorUnitario,
			Conta conta) {
		this.idServicoImpressao = idServicoImpressao;
		this.idCliente = idCliente;
		this.nomeDoServico = nomeDoServico;
		this.produtoDoServico = produtoDoServico;
		this.observacoesServico = observacoesServico;
		this.limiteMinimo = limiteMinimo;
		this.valorUnitario = valorUnitario;
		this.conta = conta;
	}

	public Integer getIdServicoImpressao() {
		return idServicoImpressao;
	}

	public void setIdServicoImpressao(Integer idServicoImpressao) {
		this.idServicoImpressao = idServicoImpressao;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeDoServico() {
		return nomeDoServico;
	}

	public void setNomeDoServico(String nomeDoServico) {
		this.nomeDoServico = nomeDoServico;
	}

	public Integer getProdutoDoServico() {
		return produtoDoServico;
	}

	public void setProdutoDoServico(Integer produtoDoServico) {
		this.produtoDoServico = produtoDoServico;
	}

	public String getObservacoesServico() {
		return observacoesServico;
	}

	public void setObservacoesServico(String observacoesServico) {
		this.observacoesServico = observacoesServico;
	}

	public Integer getLimiteMinimo() {
		return limiteMinimo;
	}

	public void setLimiteMinimo(Integer limiteMinimo) {
		this.limiteMinimo = limiteMinimo;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conta == null) ? 0 : conta.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((idServicoImpressao == null) ? 0 : idServicoImpressao.hashCode());
		result = prime * result + ((limiteMinimo == null) ? 0 : limiteMinimo.hashCode());
		result = prime * result + ((nomeDoServico == null) ? 0 : nomeDoServico.hashCode());
		result = prime * result + ((observacoesServico == null) ? 0 : observacoesServico.hashCode());
		result = prime * result + ((produtoDoServico == null) ? 0 : produtoDoServico.hashCode());
		result = prime * result + ((valorUnitario == null) ? 0 : valorUnitario.hashCode());
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
		ServicoImpressao other = (ServicoImpressao) obj;
		if (conta == null) {
			if (other.conta != null)
				return false;
		} else if (!conta.equals(other.conta))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (idServicoImpressao == null) {
			if (other.idServicoImpressao != null)
				return false;
		} else if (!idServicoImpressao.equals(other.idServicoImpressao))
			return false;
		if (limiteMinimo == null) {
			if (other.limiteMinimo != null)
				return false;
		} else if (!limiteMinimo.equals(other.limiteMinimo))
			return false;
		if (nomeDoServico == null) {
			if (other.nomeDoServico != null)
				return false;
		} else if (!nomeDoServico.equals(other.nomeDoServico))
			return false;
		if (observacoesServico == null) {
			if (other.observacoesServico != null)
				return false;
		} else if (!observacoesServico.equals(other.observacoesServico))
			return false;
		if (produtoDoServico == null) {
			if (other.produtoDoServico != null)
				return false;
		} else if (!produtoDoServico.equals(other.produtoDoServico))
			return false;
		if (valorUnitario == null) {
			if (other.valorUnitario != null)
				return false;
		} else if (!valorUnitario.equals(other.valorUnitario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServicoImpressao [idServicoImpressao=" + idServicoImpressao + ", idCliente=" + idCliente
				+ ", nomeDoServico=" + nomeDoServico + ", produtoDoServico=" + produtoDoServico
				+ ", observacoesServico=" + observacoesServico + ", limiteMinimo=" + limiteMinimo + ", valorUnitario="
				+ valorUnitario + ", conta=" + conta + "]";
	}

}
