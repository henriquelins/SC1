package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ContaDao;
import model.entities.Conta;

public class ContaDaoJDBC implements ContaDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public ContaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int inserir(Conta conta) {

		PreparedStatement st = null;
		int id_conta = 0;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO conta (cnpj, saldo, tipo) VALUES (?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, conta.getCnpj());
			st.setInt(2, conta.getSaldo());
			st.setBoolean(3,conta.isTipo());

			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();

			rs.next();

			id_conta = rs.getInt(1);

			DB.closeResultSet(rs);

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

		return id_conta;

	}

	@Override
	public void atualizar(Conta conta) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE conta SET cnpj = ?, saldo = ?, tipo = ? WHERE id_conta = ?");

			st.setString(1, conta.getCnpj());
			st.setInt(2, conta.getSaldo());
			st.setBoolean(3, conta.isTipo());
			st.setInt(4, conta.getIdConta());

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

	// método atualizar saldo

	@Override
	public void atualizarSaldo(int saldoAtualizado, int id_conta) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE conta SET saldo = ? WHERE id_conta = ?");

			st.setInt(1, saldoAtualizado);
			st.setInt(2, id_conta);

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

	@Override
	public Conta buscarContaCnjp(String cnpj) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Conta conta = new Conta();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM conta where cnpj = ?");
			st.setString(1, cnpj);
			rs = st.executeQuery();

			while (rs.next()) {
				
				conta = instantiateConta(rs);
				
			}

			conn.commit();

			return conta;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}
	
	
	@Override
	public Conta buscarContaId(int idConta) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Conta conta = new Conta();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM conta WHERE id_conta = ?");
			st.setInt(1, idConta);
			rs = st.executeQuery();

			while (rs.next()) {
				
				conta = instantiateConta(rs);
				
			}

			conn.commit();

			return conta;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Conta> listaContas() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Conta conta = new Conta();
		List <Conta> listaConta = new ArrayList<Conta>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM conta");
			rs = st.executeQuery();

			while (rs.next()) {

				conta = instantiateConta(rs);
				listaConta.add(conta);
				
			}

			conn.commit();

			return listaConta;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}

	@Override
	public List<Conta> listaCnpj(String cnpj) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Conta conta = new Conta();
		List <Conta> listaConta = new ArrayList<Conta>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM conta WERE cnpj = ? ORDER BY id_conta");
			st.setString(1, cnpj);
			rs = st.executeQuery();

			while (rs.next()) {

				conta = instantiateConta(rs);
				listaConta.add(conta);
				
			}

			conn.commit();

			return listaConta;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação rolled back. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar rollback. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}
	
	private Conta instantiateConta(ResultSet rs) throws SQLException {

		Conta conta = new Conta();
		
		conta.setIdConta(rs.getInt("id_conta"));
		conta.setCnpj(rs.getString("cnpj"));
		conta.setSaldo(rs.getInt("saldo"));
		conta.setTipo(rs.getBoolean("tipo"));
		
		return conta;

	}

}
