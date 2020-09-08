package model.entities;

import java.io.Serializable;

public class ServicoImpressao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idServicoImpressao;
	private Integer idCliente;
	private Integer idConta;
	private Integer idProdutoDoServico;
	private String nomeDoServico;
	private String observacoesServico;
	private Integer limiteMinimo;
	private Double valorUnitario;
	

	public ServicoImpressao() {
	}


	public ServicoImpressao(Integer idServicoImpressao, Integer idCliente, Integer idConta, Integer idProdutoDoServico,
			String nomeDoServico, String observacoesServico, Integer limiteMinimo, Double valorUnitario) {
		super();
		this.idServicoImpressao = idServicoImpressao;
		this.idCliente = idCliente;
		this.idConta = idConta;
		this.idProdutoDoServico = idProdutoDoServico;
		this.nomeDoServico = nomeDoServico;
		this.observacoesServico = observacoesServico;
		this.limiteMinimo = limiteMinimo;
		this.valorUnitario = valorUnitario;
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


	public Integer getIdConta() {
		return idConta;
	}


	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}


	public Integer getIdProdutoDoServico() {
		return idProdutoDoServico;
	}


	public void setIdProdutoDoServico(Integer idProdutoDoServico) {
		this.idProdutoDoServico = idProdutoDoServico;
	}


	public String getNomeDoServico() {
		return nomeDoServico;
	}


	public void setNomeDoServico(String nomeDoServico) {
		this.nomeDoServico = nomeDoServico;
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


	@Override
	public String toString() {
		return "ServicoImpressao [idServicoImpressao=" + idServicoImpressao + ", idCliente=" + idCliente + ", idConta="
				+ idConta + ", idProdutoDoServico=" + idProdutoDoServico + ", nomeDoServico=" + nomeDoServico
				+ ", observacoesServico=" + observacoesServico + ", limiteMinimo=" + limiteMinimo + ", valorUnitario="
				+ valorUnitario + "]";
	}
	
	
	

}
