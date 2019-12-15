package model.entities;

import java.io.Serializable;

public class Contato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int idContato;
	public String contatoCliente;
	public String emailCliente;
	public String foneCelular;
	public String foneFixo;

	public Contato() {
	}

	public Contato(int idContato, String contatoCliente, String emailCliente, String foneCelular, String foneFixo) {

		this.idContato = idContato;
		this.contatoCliente = contatoCliente;
		this.emailCliente = emailCliente;
		this.foneCelular = foneCelular;
		this.foneFixo = foneFixo;

	}

	public int getIdContato() {
		return idContato;
	}

	public void setIdContato(int idContato) {
		this.idContato = idContato;
	}

	public String getContatoCliente() {
		return contatoCliente;
	}

	public void setContatoCliente(String contatoCliente) {
		this.contatoCliente = contatoCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getFoneCelular() {
		return foneCelular;
	}

	public void setFoneCelular(String foneCelular) {
		this.foneCelular = foneCelular;
	}

	public String getFoneFixo() {
		return foneFixo;
	}

	public void setFoneFixo(String foneFixo) {
		this.foneFixo = foneFixo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contatoCliente == null) ? 0 : contatoCliente.hashCode());
		result = prime * result + ((emailCliente == null) ? 0 : emailCliente.hashCode());
		result = prime * result + ((foneCelular == null) ? 0 : foneCelular.hashCode());
		result = prime * result + ((foneFixo == null) ? 0 : foneFixo.hashCode());
		result = prime * result + idContato;
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
		Contato other = (Contato) obj;
		if (contatoCliente == null) {
			if (other.contatoCliente != null)
				return false;
		} else if (!contatoCliente.equals(other.contatoCliente))
			return false;
		if (emailCliente == null) {
			if (other.emailCliente != null)
				return false;
		} else if (!emailCliente.equals(other.emailCliente))
			return false;
		if (foneCelular == null) {
			if (other.foneCelular != null)
				return false;
		} else if (!foneCelular.equals(other.foneCelular))
			return false;
		if (foneFixo == null) {
			if (other.foneFixo != null)
				return false;
		} else if (!foneFixo.equals(other.foneFixo))
			return false;
		if (idContato != other.idContato)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contato [idContato=" + idContato + ", contatoCliente=" + contatoCliente + ", emailCliente="
				+ emailCliente + ", foneCelular=" + foneCelular + ", foneFixo=" + foneFixo + "]";
	}

}