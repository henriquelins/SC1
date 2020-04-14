package criptografia;

import model.entities.Usuario;

public class Criptografar {
	
	public Usuario adicionarCriptografia(Usuario usuario) {

		// Gera um sal aleatório
		String salGerado = BCrypt.gensalt();
		
		// Gera a senha hasheada utilizando o sal gerado
		String senhaHasheada = BCrypt.hashpw(usuario.getSenha(), salGerado);

		// Atualiza a senha do usuário

		usuario.setSenha(senhaHasheada); 
		
		return usuario;

	}
	
	public boolean autenticar(Usuario usuarioCandidato, Usuario usuarioComSenhaHasheada) {

		String senhaDoCandidato = usuarioCandidato.getSenha(); // Essa senha está em texto puro, sem hash.
		String senhaDoBanco = usuarioComSenhaHasheada.getSenha(); // Essa senha está hasheada.
		
		// Usa o BCrypt para verificar se a senha passada está correta.
	
		boolean autenticacaoBateu = BCrypt.checkpw(senhaDoCandidato, senhaDoBanco);

		return autenticacaoBateu;

	}

}
