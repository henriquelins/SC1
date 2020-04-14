package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LogSegurancaDao;
import model.entities.LogSeguranca;

public class LogSegurancaDaoJDBC implements LogSegurancaDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public LogSegurancaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	// método inserir

	@Override
	public void inserir(LogSeguranca logSeguranca) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO logseguranca (logado, descricao, dataLog, horaLog) VALUES (?, ?, ?, ?)");

			st.setString(1, logSeguranca.getLogado());
			st.setString(2, logSeguranca.getDescricao());
			st.setDate(3, logSeguranca.getDataLog());
			st.setTime(4, logSeguranca.getHoraLog());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Erro ao inserir o cliente! Nenhuma linha afetada no processo!");

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

	// método buscar todos

	@Override
	public List<LogSeguranca> buscarTodos() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<LogSeguranca> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM logseguranca ORDER BY id_logseguranca ASC");

			rs = st.executeQuery();

			while (rs.next()) {

				LogSeguranca logSeguranca = instantiateLogSeguranca(rs);
				list.add(logSeguranca);

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

	private LogSeguranca instantiateLogSeguranca(ResultSet rs) throws SQLException {

		LogSeguranca logSeguranca = new LogSeguranca();

		logSeguranca.setId_logSeguranca(rs.getInt("id_logseguranca"));
		logSeguranca.setLogado(rs.getString("logado"));
		logSeguranca.setDescricao(rs.getString("descricao"));
		logSeguranca.setDataLog(rs.getDate("datalog"));
		logSeguranca.setHoraLog(rs.getTime("horalog"));

		return logSeguranca;

	}

}
