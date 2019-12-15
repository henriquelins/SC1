package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {

	// connection variável
	
	private Connection conn;
	
	// método para criar a conexão

	public UsuarioDaoJDBC(Connection conn) {

		this.conn = conn;

	}
	
	// método inserir

	@Override
	public void insert(Usuario usuario) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO usuario " + "(nome_usuario, login, senha, acesso) " + "VALUES " + "(?, ?, ?, ?)");

			st.setString(1, usuario.getNome().toUpperCase());
			st.setString(2, usuario.getLogin());
			st.setString(3, usuario.getSenha());
			st.setInt(4, usuario.getAcesso());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				new DbException("Erro ao inserir o usuário");

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
	
	// método atualizar
	
	@Override
	public void update(Usuario usuario) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE usuario SET nome_usuario = ?, login = ?, senha = ?, acesso = ? WHERE id_usuario = ?");

			st.setString(1, usuario.getNome().toUpperCase());
			st.setString(2, usuario.getLogin());
			st.setString(3, usuario.getSenha());
			st.setInt(4, usuario.getAcesso());
			st.setInt(5, usuario.getIdUsuario());

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
	
	// método deletar pelo id

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");

			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Erro ao deletar o usuário");

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
	
	// método buscar pelo id

	@Override
	public Usuario findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {

				Usuario usuario = instantiateUsuario(rs);
				return usuario;

			}

			conn.commit();

			return null;

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
	
	// instanciar classe

	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(rs.getInt("id_usuario"));
		usuario.setNome(rs.getString("nome_usuario"));
		usuario.setLogin(rs.getString("login"));
		usuario.setSenha(rs.getString("senha"));
		usuario.setAcesso(rs.getInt("acesso"));
		return usuario;

	}
	
	// método listar todos

	@Override
	public List<Usuario> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM usuario ORDER BY id_usuario");

			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();

			while (rs.next()) {

				Usuario usuario = instantiateUsuario(rs);
				list.add(usuario);

			}

			conn.commit();

			return list;

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
	
	// método login

	@Override
	public Usuario login(Usuario usuario) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Usuario logado = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM usuario WHERE login = ? AND senha = ?");
			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());
			rs = st.executeQuery();

			while (rs.next()) {

				logado = instantiateUsuario(rs);

			}

			conn.commit();

			return logado;

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

}
