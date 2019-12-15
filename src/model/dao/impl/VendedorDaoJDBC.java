package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public VendedorDaoJDBC(Connection conn) {

		this.conn = conn;

	}

	@Override
	public void inserir(Vendedor vendedor) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO vendedor (nome_vendedor) VALUES (?)");

			st.setString(1, vendedor.getNomeVendedor().toUpperCase());

			int linhas = st.executeUpdate();

			if (linhas == 0) {

				throw new DbException("Erro ao inserir o vendedor!");
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
	public void atualizar(Vendedor vendedor) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE vendedor SET nome_vendedor = ? WHERE id_vendedor = ?");

			st.setString(1, vendedor.getNomeVendedor().toUpperCase());
			st.setInt(2, vendedor.getIdVendedor());

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

			st = conn.prepareStatement("DELETE FROM vendedor WHERE id_vendedor = ?");

			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Erro ao excluir o vendedor!");

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
	public List<Vendedor> buscarTodos() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Vendedor> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM vendedor order by id_vendedor asc");
			rs = st.executeQuery();

			while (rs.next()) {

				Vendedor vendedor = instantiateVendedor(rs);
				list.add(vendedor);

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

	private Vendedor instantiateVendedor(ResultSet rs) throws SQLException {

		Vendedor vendedor = new Vendedor();

		vendedor.setIdVendedor(rs.getInt("id_vendedor"));
		vendedor.setNomeVendedor(rs.getString("nome_vendedor"));

		return vendedor;
	}

	@Override
	public Vendedor buscarPeloId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Vendedor vendedor = new Vendedor();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM vendedor where id_vendedor = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				vendedor = instantiateVendedor(rs);

			}

			conn.commit();

			return vendedor;

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
