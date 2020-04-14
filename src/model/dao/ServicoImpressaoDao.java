package model.dao;

import java.util.List;

import model.entities.ServicoImpressao;

public interface ServicoImpressaoDao {
	
	void inserir(ServicoImpressao clienteServico);
	void atualizar(ServicoImpressao clienteServico);
	ServicoImpressao buscarPeloIdCliente(Integer id_cliente_servico);
	List<ServicoImpressao> buscarServicosDoCliente(Integer idCliente);
	String buscarServicosDoClientePeloCnpj(String servicoCNPJ);
	void excluir(Integer idServicoImpressao);

}
