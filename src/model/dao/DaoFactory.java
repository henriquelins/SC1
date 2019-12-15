package model.dao;

import db.DB;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.ContaDaoJDBC;
import model.dao.impl.ContatoDaoJDBC;
import model.dao.impl.EnderecoDaoJDBC;
import model.dao.impl.LancamentoDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.ServicoImpressaoDaoJDBC;
import model.dao.impl.UnidadeDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

	public static UsuarioDao createUsuarioDao() {

		return new UsuarioDaoJDBC(DB.getConnection());

	}

	public static ClienteDao createClienteDao() {

		return new ClienteDaoJDBC(DB.getConnection());

	}

	public static ServicoImpressaoDao createServicoImpressaoDao() {

		return new ServicoImpressaoDaoJDBC(DB.getConnection());

	}

	public static ContatoDao createContatoDao() {

		return new ContatoDaoJDBC(DB.getConnection());

	}

	public static EnderecoDao createEnderecoDao() {

		return new EnderecoDaoJDBC(DB.getConnection());

	}

	public static LancamentoDao createLancamentoDao() {

		return new LancamentoDaoJDBC(DB.getConnection());

	}

	public static UnidadeDao createUnidadeDao() {

		return new UnidadeDaoJDBC(DB.getConnection());

	}

	public static ProdutoDao createProdutoDao() {

		return new ProdutoDaoJDBC(DB.getConnection());

	}

	public static VendedorDao createVendedorDao() {

		return new VendedorDaoJDBC(DB.getConnection());

	}

	public static ContaDao createContaDao() {
		// TODO Auto-generated method stub
		return new ContaDaoJDBC(DB.getConnection());
	}

}
