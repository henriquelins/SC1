package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCliente;
	private String nomeFantasia;
	private String razaoSocial;
	private String cnpjCliente;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private Date dataInicioCliente;
	private String detalhesDoCliente;
	private boolean entregaNoCliente;
	private Integer cod_vendedor;
	private Integer unidadeDeAtendimento;
	private Contato contato;
	private Endereco endereco;

	public Cliente() {
	}

	public Cliente(Integer idCliente, String nomeFantasia, String razaoSocial, String cnpjCliente,
			String inscricaoEstadual, String inscricaoMunicipal, Date dataInicioCliente, String detalhesDoCliente,
			boolean entregaNoCliente, Integer cod_vendedor, Integer unidadeDeAtendimento, Contato contato, Endereco endereco) {
		this.idCliente = idCliente;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpjCliente = cnpjCliente;
		this.inscricaoEstadual = inscricaoEstadual;
		this.inscricaoMunicipal = inscricaoMunicipal;
		this.dataInicioCliente = dataInicioCliente;
		this.detalhesDoCliente = detalhesDoCliente;
		this.entregaNoCliente = entregaNoCliente;
		this.cod_vendedor = cod_vendedor;
		this.unidadeDeAtendimento = unidadeDeAtendimento;
		this.contato = contato;
		this.endereco = endereco;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpjCliente() {
		return cnpjCliente;
	}

	public void setCnpjCliente(String cnpjCliente) {
		this.cnpjCliente = cnpjCliente;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public Date getDataInicioCliente() {
		return dataInicioCliente;
	}

	public void setDataInicioCliente(Date dataInicioCliente) {
		this.dataInicioCliente = dataInicioCliente;
	}

	public String getDetalhesDoCliente() {
		return detalhesDoCliente;
	}

	public void setDetalhesDoCliente(String detalhesDoCliente) {
		this.detalhesDoCliente = detalhesDoCliente;
	}

	public boolean isEntregaNoCliente() {
		return entregaNoCliente;
	}

	public void setEntregaNoCliente(boolean entregaNoCliente) {
		this.entregaNoCliente = entregaNoCliente;
	}

	public int getCod_vendedor() {
		return cod_vendedor;
	}

	public void setCod_vendedor(Integer cod_vendedor) {
		this.cod_vendedor = cod_vendedor;
	}

	public int getUnidadeDeAtendimento() {
		return unidadeDeAtendimento;
	}

	public void setUnidadeDeAtendimento(Integer unidadeDeAtendimento) {
		this.unidadeDeAtendimento = unidadeDeAtendimento;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpjCliente == null) ? 0 : cnpjCliente.hashCode());
		result = prime * result + cod_vendedor;
		result = prime * result + ((contato == null) ? 0 : contato.hashCode());
		result = prime * result + ((dataInicioCliente == null) ? 0 : dataInicioCliente.hashCode());
		result = prime * result + ((detalhesDoCliente == null) ? 0 : detalhesDoCliente.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + (entregaNoCliente ? 1231 : 1237);
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((inscricaoEstadual == null) ? 0 : inscricaoEstadual.hashCode());
		result = prime * result + ((inscricaoMunicipal == null) ? 0 : inscricaoMunicipal.hashCode());
		result = prime * result + ((nomeFantasia == null) ? 0 : nomeFantasia.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + unidadeDeAtendimento;
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
		Cliente other = (Cliente) obj;
		if (cnpjCliente == null) {
			if (other.cnpjCliente != null)
				return false;
		} else if (!cnpjCliente.equals(other.cnpjCliente))
			return false;
		if (cod_vendedor != other.cod_vendedor)
			return false;
		if (contato == null) {
			if (other.contato != null)
				return false;
		} else if (!contato.equals(other.contato))
			return false;
		if (dataInicioCliente == null) {
			if (other.dataInicioCliente != null)
				return false;
		} else if (!dataInicioCliente.equals(other.dataInicioCliente))
			return false;
		if (detalhesDoCliente == null) {
			if (other.detalhesDoCliente != null)
				return false;
		} else if (!detalhesDoCliente.equals(other.detalhesDoCliente))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (entregaNoCliente != other.entregaNoCliente)
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (inscricaoEstadual == null) {
			if (other.inscricaoEstadual != null)
				return false;
		} else if (!inscricaoEstadual.equals(other.inscricaoEstadual))
			return false;
		if (inscricaoMunicipal == null) {
			if (other.inscricaoMunicipal != null)
				return false;
		} else if (!inscricaoMunicipal.equals(other.inscricaoMunicipal))
			return false;
		if (nomeFantasia == null) {
			if (other.nomeFantasia != null)
				return false;
		} else if (!nomeFantasia.equals(other.nomeFantasia))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (unidadeDeAtendimento != other.unidadeDeAtendimento)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nomeFantasia=" + nomeFantasia + ", razaoSocial=" + razaoSocial
				+ ", cnpjCliente=" + cnpjCliente + ", inscricaoEstadual=" + inscricaoEstadual + ", inscricaoMunicipal="
				+ inscricaoMunicipal + ", dataInicioCliente=" + dataInicioCliente + ", detalhesDoCliente="
				+ detalhesDoCliente + ", entregaNoCliente=" + entregaNoCliente + ", cod_vendedor=" + cod_vendedor
				+ ", unidadeDeAtendimento=" + unidadeDeAtendimento + ", contato=" + contato + ", endereco=" + endereco
				+ "]";
	}
	
	public String apresentacaoBasicaCliente() {
		return  nomeFantasia + " - CNPJ: " + cnpjCliente + " - Cliente Desde: " + dataInicioCliente +  "  - Vendedor: " + cod_vendedor
				+ " - Atendimento: " + unidadeDeAtendimento;
	}
}
