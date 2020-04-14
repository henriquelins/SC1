package criptografia;

import model.entities.Usuario;

public class Criptografar {
	
	public Usuario adicionarCriptografia(Usuario usuario) {

		// Gera um sal aleat�rio
		String salGerado = BCrypt.gensalt();
		
		// Gera a senha hasheada utilizando o sal gerado
		String senhaHasheada = BCrypt.hashpw(usuario.getSenha(), salGerado);

		// Atualiza a senha do usu�rio

		usuario.setSenha(senhaHasheada); 
		
		return usuario;

	}
	
	public boolean autenticar(Usuario usuarioCandidato, Usuario usuarioComSenhaHasheada) {

		String senhaDoCandidato = usuarioCandidato.getSenha(); // Essa senha est� em texto puro, sem hash.
		String senhaDoBanco = usuarioComSenhaHasheada.getSenha(); // Essa senha est� hasheada.
		
		// Usa o BCrypt para verificar se a senha passada est� correta.
	
		boolean autenticacaoBateu = BCrypt.checkpw(senhaDoCandidato, senhaDoBanco);

		return autenticacaoBateu;

	}

}
