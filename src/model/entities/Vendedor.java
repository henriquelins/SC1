package model.entities;

import java.io.Serializable;

import model.services.VendedorService;

public class Vendedor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idVendedor;
	private String nomeVendedor;
	private String fone;
	private String email;
	
	public Vendedor() {
	}

	public Vendedor(Integer idVendedor, String nomeVendedor, String fone, String email) {
		super();
		this.idVendedor = idVendedor;
		this.nomeVendedor = nomeVendedor;
		this.fone = fone;
		this.email = email;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fone == null) ? 0 : fone.hashCode());
		result = prime * result + ((idVendedor == null) ? 0 : idVendedor.hashCode());
		result = prime * result + ((nomeVendedor == null) ? 0 : nomeVendedor.hashCode());
		return result;
	}
	
	

	@Override
	public String toString() {
		return "Vendedor [idVendedor=" + idVendedor + ", nomeVendedor=" + nomeVendedor + ", fone=" + fone + ", email="
				+ email + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendedor other = (Vendedor) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fone == null) {
			if (other.fone != null)
				return false;
		} else if (!fone.equals(other.fone))
			return false;
		if (idVendedor == null) {
			if (other.idVendedor != null)
				return false;
		} else if (!idVendedor.equals(other.idVendedor))
			return false;
		if (nomeVendedor == null) {
			if (other.nomeVendedor != null)
				return false;
		} else if (!nomeVendedor.equals(other.nomeVendedor))
			return false;
		return true;
	}

	public String cbVendedor(int id) {

		Vendedor vendedor = new Vendedor();
		vendedor = new VendedorService().buscarPeloId(id);

		//String contatenado = vendedor.getIdVendedor() + " - " + vendedor.getNomeVendedor();
		
		String contatenado = vendedor.getIdVendedor() + " - " + vendedor.getNomeVendedor();

		return contatenado;

	}
	
	public String nomeVendedor(int id) {

		Vendedor vendedor = new Vendedor();
		vendedor = new VendedorService().buscarPeloId(id);

		String contatenado = vendedor.getNomeVendedor();

		return contatenado;

	}
	
	public Integer codVendedor(String nome) {

		String cod = nome.substring(0, nome.indexOf("-")).trim();

		return Integer.valueOf(cod);
	}

}
