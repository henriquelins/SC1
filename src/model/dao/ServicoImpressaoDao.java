package model.dao;

import java.util.List;

import model.entities.ServicoImpressao;

public interface ServicoImpressaoDao {
	
	void inserir(ServicoImpressao clienteServico);
	void atualizar(ServicoImpressao clienteServico);
	void excluirPeloId(Integer id);
	ServicoImpressao buscarPeloId(Integer id);
	ServicoImpressao buscarPeloIdCliente(Integer id_cliente_servico);
	List<ServicoImpressao> buscarTodos();
	List<ServicoImpressao> buscarPeloNomeFantasia(String nomeFantasia);
	List<ServicoImpressao> buscarServicosDoCliente(Integer idCliente);

}
