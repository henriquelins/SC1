package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ServicoImpressaoDao;
import model.entities.Conta;
import model.entities.ServicoImpressao;

public class ServicoImpressaoDaoJDBC implements ServicoImpressaoDao {

	// connection variável

	private Connection conn;

	// método para criar a conexão

	public ServicoImpressaoDaoJDBC(Connection conn) {

		this.conn = conn;

	}

	// método inserir

	@Override
	public void inserir(ServicoImpressao servicoImpressao) {

		PreparedStatement st = null;

		ContaDaoJDBC contaDao = new ContaDaoJDBC(conn);

		try {

			conn.setAutoCommit(false);

			if (servicoImpressao.getConta().getIdConta() == 0) {

				servicoImpressao.getConta().setIdConta(contaDao.inserir(servicoImpressao));

			}

			st = conn.prepareStatement(
					"INSERT INTO servico_impressao (id_cliente, id_conta, nome_servico, produto_servico,"
							+ " observacoes_servico, limite_minimo, valor_unitario)" + " VALUES (?, ?, ?, ? ,?, ?, ?)");

			st.setInt(1, servicoImpressao.getIdCliente());
			st.setInt(2, servicoImpressao.getConta().getIdConta());
			st.setString(3, servicoImpressao.getNomeDoServico().toUpperCase());
			st.setInt(4, servicoImpressao.getProdutoDoServico());
			st.setString(5, servicoImpressao.getObservacoesServico().toUpperCase());
			st.setInt(6, servicoImpressao.getLimiteMinimo());
			st.setDouble(7, servicoImpressao.getValorUnitario());

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

	// método atualizar

	@Override
	public void atualizar(ServicoImpressao servicoImpressao) {

		PreparedStatement st = null;

		ContaDaoJDBC contaDao = new ContaDaoJDBC(conn);

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE servico_impressao SET id_cliente = ?, id_conta = ?, nome_servico = ?, produto_servico = ?, "
							+ "observacoes_servico = ?, limite_minimo = ?, valor_unitario = ? "
							+ "WHERE id_servico_impressao = ?");

			st.setInt(1, servicoImpressao.getIdCliente());
			st.setInt(2, servicoImpressao.getConta().getIdConta());
			st.setString(3, servicoImpressao.getNomeDoServico().toUpperCase());
			st.setInt(4, servicoImpressao.getProdutoDoServico());
			st.setString(5, servicoImpressao.getObservacoesServico().toUpperCase());
			st.setInt(6, servicoImpressao.getLimiteMinimo());
			st.setDouble(7, servicoImpressao.getValorUnitario());
			st.setInt(8, servicoImpressao.getIdServicoImpressao());

			contaDao.atualizar(servicoImpressao);

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

	// método buscar cliente serviço pelo id cliente servico

	@Override
	public ServicoImpressao buscarPeloIdCliente(Integer id_servico_impressao) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM servico_impressao as si inner join conta as co on si.id_conta = co.id_conta WHERE si.id_servico_impressao = ? ORDER BY id_servico_impressao");

			st.setInt(1, id_servico_impressao);
			rs = st.executeQuery();

			if (rs.next()) {

				ServicoImpressao servicoImpressao = instantiateServicoImpressao(rs);
				Conta conta = instantiateConta(rs);

				servicoImpressao.setConta(conta);

				return servicoImpressao;

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

	// método listar serviços do cliente pelo id cliente

	@Override
	public List<ServicoImpressao> buscarServicosDoCliente(Integer idCliente) {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<ServicoImpressao> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM  servico_impressao as si inner join conta as co on si.id_conta = co.id_conta WHERE si.id_cliente = ? ORDER BY co.cnpj");
			st.setInt(1, idCliente);
			rs = st.executeQuery();

			while (rs.next()) {

				ServicoImpressao servicoImpressao = instantiateServicoImpressao(rs);
				list.add(servicoImpressao);

			}

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
			DB.closeResultSet(rs);

		}

		return list;

	}

	// instanciar classe

	private ServicoImpressao instantiateServicoImpressao(ResultSet rs) throws SQLException {

		ServicoImpressao servicoImpressao = new ServicoImpressao();
		Conta conta = new Conta();

		servicoImpressao.setIdServicoImpressao(rs.getInt("id_servico_impressao"));
		servicoImpressao.setIdCliente(rs.getInt("id_cliente"));
		servicoImpressao.setNomeDoServico(rs.getString("nome_servico"));
		servicoImpressao.setProdutoDoServico(rs.getInt("produto_servico"));
		servicoImpressao.setObservacoesServico(rs.getString("observacoes_servico"));
		servicoImpressao.setLimiteMinimo(rs.getInt("limite_minimo"));
		servicoImpressao.setValorUnitario(rs.getDouble("valor_unitario"));

		conta.setIdConta(rs.getInt("id_conta"));
		conta.setCnpj(rs.getString("cnpj"));
		conta.setSaldo(rs.getInt("saldo"));
		conta.setTipo(rs.getBoolean("tipo"));

		servicoImpressao.setConta(conta);

		return servicoImpressao;
	}

	private Conta instantiateConta(ResultSet rs) throws SQLException {

		Conta conta = new Conta();

		conta.setIdConta(rs.getInt("id_conta"));
		conta.setCnpj(rs.getString("cnpj"));
		conta.setSaldo(rs.getInt("saldo"));
		conta.setTipo(rs.getBoolean("tipo"));

		return conta;
	}

	@Override
	public String buscarServicosDoClientePeloCnpj(String servicoCNPJ) {

		PreparedStatement st = null;
		ResultSet rs = null;
		String nome = "";

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM  cliente as cl inner join servico_impressao as si on cl.id_cliente = si.id_cliente inner join conta as co on si.id_conta = co.id_conta WHERE co.cnpj = ?");
			st.setString(1, servicoCNPJ);
			rs = st.executeQuery();

			while (rs.next()) {

				nome =  rs.getString("nome_fantasia");

				conn.commit();

			}

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());

			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

		return nome;

	}

}
