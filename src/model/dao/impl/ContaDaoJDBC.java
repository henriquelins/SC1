package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ContaDao;
import model.entities.Conta;
import model.entities.ServicoImpressao;

public class ContaDaoJDBC implements ContaDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public ContaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int inserir(ServicoImpressao servicoImpressao) {

		PreparedStatement st = null;
		int id_conta = 0;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO conta (cnpj, saldo) VALUES (?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, servicoImpressao.getConta().getCnpj());
			st.setInt(2, servicoImpressao.getConta().getSaldo());

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
	public void atualizar(ServicoImpressao servicoImpressao) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE conta SET cnpj = ?, saldo = ? WHERE id_conta = ?");

			st.setString(1, servicoImpressao.getConta().getCnpj());
			st.setInt(2, servicoImpressao.getConta().getSaldo());
			st.setInt(3, servicoImpressao.getConta().getIdConta());

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
	public void atualizarSaldo(int saldoAtual, int id_conta) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE conta SET saldo = ? WHERE id_conta = ?");

			st.setInt(1, saldoAtual);
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
	public int buscarConta(String cnpj) {

		PreparedStatement st = null;
		ResultSet rs = null;
		int conta = 0;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM conta where cnpj = ?");
			st.setString(1, cnpj);
			rs = st.executeQuery();

			while (rs.next()) {

				conta = rs.getInt("id_conta");
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
	public List<Conta> buscarPeloCnpj(String cnpj) {
		// TODO Auto-generated method stub
		return null;
	}

}
