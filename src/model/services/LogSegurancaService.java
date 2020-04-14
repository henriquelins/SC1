package model.services;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.LogSegurancaDao;
import model.entities.LogSeguranca;

public class LogSegurancaService {

	private LogSegurancaDao dao = DaoFactory.createLogSegurancaDao();

	public void novoLogSeguranca(String logado, String descricao) {

		LogSeguranca logSeguranca = new LogSeguranca();
		logSeguranca.setLogado(logado);

		logSeguranca.setDescricao(descricao);

		Date dataLog = new Date(System.currentTimeMillis());
		logSeguranca.setDataLog(dataLog);

		Time hotaLog = new Time(System.currentTimeMillis());
		logSeguranca.setHoraLog(hotaLog);

		dao.inserir(logSeguranca);

	}

	public  List<LogSeguranca> logBuscarTodos() {

		return dao.buscarTodos();

	}

}
