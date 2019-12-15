package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioService {

	// java vari�veis

	Usuario user = new Usuario();

	private UsuarioDao dao = DaoFactory.createUsuarioDao();

	// m�todo login

	public Usuario login(Usuario usuario) {

		user = dao.login(usuario);
		return user;

	}

	// m�todo novo ou editar usu�rio

	public void usuarioNovoOuEditar(Usuario usuario) {

		if (usuario.getIdUsuario() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar o usu�rio " + usuario.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(usuario);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja editar o usu�rio " + usuario.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(usuario);

			}

		}
	}

	// m�todo excluir usu�rio

	public void remove(Usuario usuario) {

		dao.deleteById(usuario.getIdUsuario());

	}

	// m�todo listar todos usu�rios

	public List<Usuario> findAll() {

		return dao.findAll();

	}
}
