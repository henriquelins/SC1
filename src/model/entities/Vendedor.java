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

	public Vendedor() {
	}

	public Vendedor(Integer idVendedor, String nomeVendedor) {
		this.idVendedor = idVendedor;
		this.nomeVendedor = nomeVendedor;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idVendedor;
		result = prime * result + ((nomeVendedor == null) ? 0 : nomeVendedor.hashCode());
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
		Vendedor other = (Vendedor) obj;
		if (idVendedor != other.idVendedor)
			return false;
		if (nomeVendedor == null) {
			if (other.nomeVendedor != null)
				return false;
		} else if (!nomeVendedor.equals(other.nomeVendedor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vendedor [id Vendedor=" + idVendedor + ", Vendedor=" + nomeVendedor + "]";
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
