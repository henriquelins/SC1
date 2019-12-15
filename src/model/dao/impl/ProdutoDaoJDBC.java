package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public ProdutoDaoJDBC(Connection conn) {

		this.conn = conn;

	}

	@Override
	public void inserir(Produto produto) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO produto (nome_produto) VALUES (?)");

			st.setString(1, produto.getNomeProduto().toUpperCase());

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
	public void atualizar(Produto produto) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE produto SET nome_produto = ? WHERE id_produto = ?");

			st.setString(1, produto.getNomeProduto().toUpperCase());
			st.setInt(2, produto.getIdProduto());

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

			st = conn.prepareStatement("DELETE FROM produto WHERE id_produto = ?");

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
	public List<Produto> buscarTodos() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Produto> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto order by id_produto asc");
			rs = st.executeQuery();

			while (rs.next()) {

				Produto produto = instantiateProduto(rs);
				list.add(produto);

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

	@Override
	public Produto buscarPeloId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Produto produto = new Produto();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto where id_produto = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				produto = instantiateProduto(rs);

			}

			conn.commit();

			return produto;

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

	private Produto instantiateProduto(ResultSet rs) throws SQLException {

		Produto produto = new Produto();

		produto.setIdProduto(rs.getInt("id_produto"));
		produto.setNomeProduto(rs.getString("nome_produto"));

		return produto;
	}
}
