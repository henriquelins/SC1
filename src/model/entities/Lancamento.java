package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Lancamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idLancamento;
	private int idServicoImpressao;
	private Date dataDoLancamento;
	private int quantidadeDoPedido;
	private int saldoAnterior;
	private int saldoAtual;
	private String tipoDoLancamento;
	private String observacoesLancamento;

	public Lancamento() {
	}

	public Lancamento(int idLancamento, int idServicoImpressao, Date dataDoLancamento, int quantidadeDoPedido,
			int saldoAnterior, int saldoAtual, String tipoDoLancamento, String observacoesLancamento) {

		this.idLancamento = idLancamento;
		this.idServicoImpressao = idServicoImpressao;
		this.dataDoLancamento = dataDoLancamento;
		this.quantidadeDoPedido = quantidadeDoPedido;
		this.saldoAnterior = saldoAnterior;
		this.saldoAtual = saldoAtual;
		this.tipoDoLancamento = tipoDoLancamento;
		this.observacoesLancamento = observacoesLancamento;
	}

	public int getIdLancamento() {
		return idLancamento;
	}

	public void setIdLancamento(int idLancamento) {
		this.idLancamento = idLancamento;
	}

	public int getIdServicoImpressao() {
		return idServicoImpressao;
	}

	public void setIdServicoImpressao(int idServicoImpressao) {
		this.idServicoImpressao = idServicoImpressao;
	}

	public Date getDataDoLancamento() {
		return dataDoLancamento;
	}

	public void setDataDoLancamento(Date dataDoLancamento) {
		this.dataDoLancamento = dataDoLancamento;
	}

	public int getQuantidadeDoPedido() {
		return quantidadeDoPedido;
	}

	public void setQuantidadeDoPedido(int quantidadeDoPedido) {
		this.quantidadeDoPedido = quantidadeDoPedido;
	}

	public int getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(int saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public int getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(int saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public String getTipoDoLancamento() {
		return tipoDoLancamento;
	}

	public void setTipoDoLancamento(String tipoDoLancamento) {
		this.tipoDoLancamento = tipoDoLancamento;
	}

	public String getObservacoesLancamento() {
		return observacoesLancamento;
	}

	public void setObservacoesLancamento(String observacoesLancamento) {
		this.observacoesLancamento = observacoesLancamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDoLancamento == null) ? 0 : dataDoLancamento.hashCode());
		result = prime * result + idServicoImpressao;
		result = prime * result + idLancamento;
		result = prime * result + ((observacoesLancamento == null) ? 0 : observacoesLancamento.hashCode());
		result = prime * result + quantidadeDoPedido;
		result = prime * result + saldoAnterior;
		result = prime * result + saldoAtual;
		result = prime * result + ((tipoDoLancamento == null) ? 0 : tipoDoLancamento.hashCode());
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
		Lancamento other = (Lancamento) obj;
		if (dataDoLancamento == null) {
			if (other.dataDoLancamento != null)
				return false;
		} else if (!dataDoLancamento.equals(other.dataDoLancamento))
			return false;
		if (idServicoImpressao != other.idServicoImpressao)
			return false;
		if (idLancamento != other.idLancamento)
			return false;
		if (observacoesLancamento == null) {
			if (other.observacoesLancamento != null)
				return false;
		} else if (!observacoesLancamento.equals(other.observacoesLancamento))
			return false;
		if (quantidadeDoPedido != other.quantidadeDoPedido)
			return false;
		if (saldoAnterior != other.saldoAnterior)
			return false;
		if (saldoAtual != other.saldoAtual)
			return false;
		if (tipoDoLancamento == null) {
			if (other.tipoDoLancamento != null)
				return false;
		} else if (!tipoDoLancamento.equals(other.tipoDoLancamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lancamento [idLancamento=" + idLancamento + ", idServicoImpressao=" + idServicoImpressao
				+ ", dataDoLancamento=" + dataDoLancamento + ", quantidadeDoPedido=" + quantidadeDoPedido
				+ ", saldoAnterior=" + saldoAnterior + ", saldoAtual=" + saldoAtual + ", tipoDoLancamento="
				+ tipoDoLancamento + ", observacoesLancamento=" + observacoesLancamento + "]";
	}

}
