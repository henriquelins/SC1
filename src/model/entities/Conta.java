package model.entities;

import java.io.Serializable;

public class Conta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idConta;
	private String cnpj;
	private int saldo;

	public Conta() {
	}

	public Conta(int idConta, String cnpj, int saldo) {

		this.idConta = idConta;
		this.cnpj = cnpj;
		this.saldo = saldo;
	}

	public int getIdConta() {
		return idConta;
	}

	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + idConta;
		result = prime * result + saldo;
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
		Conta other = (Conta) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (idConta != other.idConta)
			return false;
		if (saldo != other.saldo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conta [idConta=" + idConta + ", cnpj=" + cnpj + ", saldo=" + saldo + "]";
	}

}
