package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UnidadeDao;
import model.entities.Unidade;

public class UnidadeDaoJDBC implements UnidadeDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public UnidadeDaoJDBC(Connection conn) {

		this.conn = conn;

	}

	@Override
	public void inserir(Unidade unidade) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO unidade (nome_unidade) VALUES (?)");

			st.setString(1, unidade.getNomeUnidade().toUpperCase());

			int linhas = st.executeUpdate();

			if (linhas == 0) {

				throw new DbException("Erro ao inserir a unidade de atendimento!");
			}

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação não concluída. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar concluir a transação. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public void atualizar(Unidade unidade) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE unidade SET nome_unidade = ? WHERE id_unidade = ?");

			st.setString(1, unidade.getNomeUnidade().toUpperCase());
			st.setInt(2, unidade.getIdUnidade());

			st.executeUpdate();

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação não concluída. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar concluir a transação. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public void excluir(Integer id) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("DELETE FROM unidade WHERE id_unidade = ?");

			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Erro ao excluir a unidade de atendimento!");

			}

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação não concluída. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar concluir a transação. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public List<Unidade> buscarTodos() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Unidade> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM unidade order by id_unidade asc");
			rs = st.executeQuery();

			while (rs.next()) {

				Unidade unidade = instantiateUnidade(rs);
				list.add(unidade);

			}

			conn.commit();

			return list;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação não concluída. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar concluir a transação. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	private Unidade instantiateUnidade(ResultSet rs) throws SQLException {

		Unidade unidade = new Unidade();

		unidade.setIdUnidade(rs.getInt("id_unidade"));
		unidade.setNomeUnidade(rs.getString("nome_unidade"));

		return unidade;
	}

	@Override
	public Unidade buscarPeloId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Unidade unidade = new Unidade();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM unidade where id_unidade = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				unidade = instantiateUnidade(rs);

			}

			conn.commit();

			return unidade;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transação não concluída. Causada por: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Erro ao tentar concluir a transação. Causada por: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

}
