package model.services;

import java.util.List;

import email.Email;
import model.dao.DaoFactory;
import model.dao.EmailDao;

public class EmailService {

	private EmailDao dao = DaoFactory.createEmailDao();

	public void emailNovoOuEditar(Email email) {

		if (email.getIdEmail() == null) {

			dao.inserir(email);

		} else {

			dao.atualizar(email);
		}

	}

	public void emailExcluir(int idEmail) {

		dao.excluir(idEmail);

	}

	public List<Email> emailBuscarTodos() {

		return dao.listaEmail();

	}

	public Email buscarEmail() {

		return dao.buscarEmail();

	}

}
