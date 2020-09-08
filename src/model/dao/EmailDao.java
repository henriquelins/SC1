package model.dao;

import java.util.List;
import email.Email;

public interface EmailDao {
	
	void inserir(Email email);
	void atualizar(Email email);
	void excluir (int idEmail);
    Email buscarEmail();
    List<Email> listaEmail();
	
}
