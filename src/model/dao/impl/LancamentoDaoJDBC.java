package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LancamentoDao;
import model.entities.Lancamento;
import model.entities.ServicoImpressao;

public class LancamentoDaoJDBC implements LancamentoDao {
	
	// connection variável
	
	private Connection conn;
	
	// método para criar a conexão

	public LancamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	// método inserir
	
	@Override
	public void inserir(Lancamento lancamento, ServicoImpressao clienteServico) {

		PreparedStatement st = null;
		
		ContaDaoJDBC contaDao = new ContaDaoJDBC(conn);

		try {

			conn.setAutoCommit(false);
			
			contaDao.atualizarSaldo(lancamento.getSaldoAtual(), clienteServico.getConta().getIdConta());

			st = conn.prepareStatement(
					"INSERT INTO lancamento (id_servico_impressao, data_do_lancamento, quantidade_do_pedido, saldo_anterior, saldo_atual,"
							+ " tipo_do_lancamento, observacoes_lancamento)" + " VALUES (?, ?, ? ,?, ?, ?, ?)");

			st.setInt(1, lancamento.getIdServicoImpressao());
			st.setDate(2, lancamento.getDataDoLancamento());
			st.setInt(3, lancamento.getQuantidadeDoPedido());
			st.setInt(4, lancamento.getSaldoAnterior());
			st.setInt(5, lancamento.getSaldoAtual());
			st.setString(6, lancamento.getTipoDoLancamento().toUpperCase());
			st.setString(7, lancamento.getObservacoesLancamento().toUpperCase());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Error ao fazer o lançamento! Nenhuma linha afetada no processo!");

			} else {

				new ContaDaoJDBC(conn).atualizarSaldo(lancamento.getSaldoAtual(),
						lancamento.getIdServicoImpressao());

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
	public void atualizar(Lancamento lancamento, ServicoImpressao clienteServico) {

		PreparedStatement st = null;
		
		ContaDaoJDBC contaDao = new ContaDaoJDBC(conn);

		try {

			conn.setAutoCommit(false);
			
			contaDao.atualizarSaldo(lancamento.getSaldoAtual(), clienteServico.getConta().getIdConta());
			
			st = conn.prepareStatement(
					"UPDATE lancamento SET id_servico_impressao = ?, data_do_lancamento = ?, quantidade_do_pedido = ?, saldo_anterior = ?, saldo_atual = ?,"
							+ " tipo_do_lancamento = ?, observacoes_lancamento = ? WHERE id_lancamento = ?");

			st.setInt(1, lancamento.getIdServicoImpressao());
			st.setDate(2, lancamento.getDataDoLancamento());
			st.setInt(3, lancamento.getQuantidadeDoPedido());
			st.setInt(4, lancamento.getSaldoAnterior());
			st.setInt(5, lancamento.getSaldoAtual());
			st.setString(6, lancamento.getTipoDoLancamento().toUpperCase());
			st.setString(7, lancamento.getObservacoesLancamento().toUpperCase());
			st.setInt(8, lancamento.getIdLancamento());

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
	
	// método buscar todos
	
	@Override
	public List<Lancamento> buscarTodos(Integer idServicoImpressao) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM lancamento WHERE id_servico_impressao = ? ORDER BY id_servico_impressao");

			st.setInt(1, idServicoImpressao);
			rs = st.executeQuery();

			List<Lancamento> lista = new ArrayList<>();

			if (rs.next()) {

				Lancamento lancamento = instantiateLancamento(rs);

				lista.add(lancamento);

			}

			conn.commit();

			return lista;

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
	
	// método buscar lançamentos por data

	@Override
	public List<Lancamento> verLancamentos(Date dataInicial, Date dataFinal, int idServicoImpressao) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM lancamento WHERE data_do_lancamento between ? and ? and id_servico_impressao = ? order by id_lancamento");

			st.setDate(1, dataInicial);
			st.setDate(2, dataFinal);
			st.setInt(3, idServicoImpressao);
			rs = st.executeQuery();

			List<Lancamento> lista = new ArrayList<>();

			while (rs.next()) {

				Lancamento lancamento = instantiateLancamento(rs);
				lista.add(lancamento);

			}

			conn.commit();

			return lista;

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
	
	private Lancamento instantiateLancamento(ResultSet rs) throws SQLException {

		Lancamento lancamento = new Lancamento();

		lancamento.setIdLancamento(rs.getInt("id_lancamento"));
		lancamento.setIdServicoImpressao(rs.getInt("id_servico_impressao"));
		lancamento.setDataDoLancamento(rs.getDate("data_do_lancamento"));
		lancamento.setQuantidadeDoPedido(rs.getInt("quantidade_do_pedido"));
		lancamento.setSaldoAnterior(rs.getInt("saldo_anterior"));
		lancamento.setSaldoAtual(rs.getInt("saldo_atual"));
		lancamento.setTipoDoLancamento(rs.getString("tipo_do_lancamento"));
		lancamento.setObservacoesLancamento(rs.getString("observacoes_lancamento"));

		return lancamento;
	}

	
}
