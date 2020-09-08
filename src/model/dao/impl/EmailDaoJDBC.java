package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import email.Email;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.dao.EmailDao;

public class EmailDaoJDBC implements EmailDao {

	// connection variável

	private Connection conn;

	public EmailDaoJDBC(Connection connection) {

		this.conn = connection;

	}

	@Override
	public void inserir(Email email) {

		PreparedStatement st = null;
		
		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO email (nome_email, descricao_email, imagem_assinatura_email, nome_servidor, host_name, smtp_port, authentication) VALUES (?, ?, ?, ?, ?, ?, ?)");

			st.setString(1, email.getNomeEmail());
			st.setString(2, email.getDescricaoEmail());
			st.setBytes(3, email.getImagemAssinaturaEmail());
			st.setString(4, email.getNomeServidor());
			st.setString(5, email.getHostName());
			st.setInt(6, email.getSmtpPort());
			st.setString(7, email.getAuthentication());
		
			st.executeUpdate();

			Alerts.showAlert("Cadastro de email", "Novo email", "E-mail salvo com sucesso!", AlertType.INFORMATION);

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
	public void atualizar(Email email) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE email SET nome_email = ?, descricao_email = ?, imagem_assinatura_email = ?, nome_servidor = ?, host_name = ?, smtp_port = ?, authentication = ?"
					+ "  WHERE id_email = ?");
			
			st.setString(1, email.getNomeEmail());
			st.setString(2, email.getDescricaoEmail());
			st.setBytes(3, email.getImagemAssinaturaEmail());
			st.setString(4, email.getNomeServidor());
			st.setString(5, email.getHostName());
			st.setInt(6, email.getSmtpPort());
			st.setString(7, email.getAuthentication());
			st.setInt(8, email.getIdEmail());

			st.executeUpdate();

			Alerts.showAlert("Cadastro de email", "Editar email", "E-mail editado com sucesso!", AlertType.INFORMATION);

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
	public void excluir(int idEmail) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("DELETE FROM email WHERE id_email = ?");

			st.setInt(1, idEmail);

			st.executeUpdate();

			Alerts.showAlert("Cadastro de email", "Excluir email", "E-mail excluído com sucesso!",
					AlertType.INFORMATION);

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public Email buscarEmail() {

		PreparedStatement st = null;
		ResultSet rs = null;
		Email email = new Email();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM email");
			rs = st.executeQuery();

			while (rs.next()) {

				email = instantiateEmail(rs);

			}

			conn.commit();

			return email;

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
	public List<Email> listaEmail() {

		PreparedStatement st = null;
		ResultSet rs = null;
		Email email = new Email();
		List<Email> listaEmail = new ArrayList<Email>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM email ORDER BY id_email");
			rs = st.executeQuery();

			while (rs.next()) {

				email = instantiateEmail(rs);
				listaEmail.add(email);

			}

			conn.commit();

			return listaEmail;

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

	private Email instantiateEmail(ResultSet rs) throws SQLException {

		Email email = new Email();

		email.setIdEmail(rs.getInt("id_email"));
		email.setNomeEmail(rs.getString("nome_email"));
		email.setDescricaoEmail(rs.getString("descricao_email"));
		email.setImagemAssinaturaEmail(rs.getBytes("imagem_assinatura_email"));
		email.setNomeServidor(rs.getString("nome_servidor"));
		email.setHostName(rs.getString("host_name"));
		email.setSmtpPort(rs.getInt("smtp_port"));
		email.setAuthentication(rs.getString("authentication"));

		return email;

	}


}
