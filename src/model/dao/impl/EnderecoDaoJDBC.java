package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import model.dao.EnderecoDao;
import model.entities.Cliente;

public class EnderecoDaoJDBC implements EnderecoDao {
	
	// connection variável
	
	private Connection conn;
	
	// método para criar a conexão

	public EnderecoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	// método inserir

	@Override
	public void inserir(Cliente cliente) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO endereco (logradouro, bairro, cidade, uf, cep, id_cliente) "
					+ "VALUES (?, ?, ? ,?, ?, ?)");

			st.setString(1, cliente.getEndereco().getLogradouro().toUpperCase());
			st.setString(2, cliente.getEndereco().getBairro().toUpperCase());
			st.setString(3, cliente.getEndereco().getCidade().toUpperCase());
			st.setString(4, cliente.getEndereco().getUf().toUpperCase());
			st.setString(5, cliente.getEndereco().getCep());
			st.setInt(6, cliente.getIdCliente());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {

				throw new DbException("Error ao inserir o endereço do cliente! Nenhuma linha afetada no processo!");

			} 

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());

			}

		} finally {

			DB.closeStatement(st);
		}

	}
	
	@Override
	public void atualizar(Cliente cliente) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE endereco SET logradouro = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, id_cliente = ? WHERE id_endereco = ?");

			st.setString(1, cliente.getEndereco().getLogradouro().toUpperCase());
			st.setString(2, cliente.getEndereco().getBairro().toUpperCase());
			st.setString(3, cliente.getEndereco().getCidade().toUpperCase());
			st.setString(4, cliente.getEndereco().getUf().toUpperCase());
			st.setString(5, cliente.getEndereco().getCep());
			st.setInt(6, cliente.getIdCliente());
			st.setInt(7, cliente.getEndereco().getIdEndereco());

			st.executeUpdate();

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());

			}

		} finally {

			DB.closeStatement(st);
		}

	}


}
