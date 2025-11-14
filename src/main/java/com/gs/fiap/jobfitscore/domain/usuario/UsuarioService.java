package com.gs.fiap.jobfitscore.domain.usuario;

import com.gs.fiap.jobfitscore.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {
	
	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	public UsuarioService( UsuarioRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	public Page<UsuarioDTO> listarUsuarios( Pageable pageable) {
		return repository.findAll(pageable)
				.map(UsuarioDTO::fromEntity);
	}
	
	public UsuarioDTO buscarUsuarioPorId( Long id ) {
		Usuario usuario = repository.findById( id ).orElseThrow( () -> new RegraDeNegocioException( "Usuário de ID: " + id + " não encontrado." ) );
		return UsuarioDTO.fromEntity( usuario );
	}
	
	@Transactional
	public UsuarioDTO salvarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		Usuario salvo = repository.save(usuario);
		return UsuarioDTO.fromEntity(salvo);
	}
	
	@Transactional
	public UsuarioDTO atualizarUsuario(Long id, Usuario usuarioAtualizado) {
		Usuario usuario = repository.findById(id)
				.orElseThrow(() -> new RegraDeNegocioException("Usuário de ID: " + id + " não encontrado."));
		
		if (usuarioAtualizado.getNome() != null && !usuarioAtualizado.getNome().isBlank()) {
			usuario.setNome(usuarioAtualizado.getNome());
		}
		
		if (usuarioAtualizado.getEmail() != null && !usuarioAtualizado.getEmail().isBlank()) {
			usuario.setEmail(usuarioAtualizado.getEmail());
		}
		
		if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isBlank()) {
			usuario.setSenha(encoder.encode(usuarioAtualizado.getSenha()));
		}
		
		Usuario salvo = repository.save(usuario);
		return UsuarioDTO.fromEntity(salvo);
	}
	
	@Transactional
	public void deletarUsuario( Long id ) {
		repository.deleteById( id );
	}
	
	@Transactional
	public void atualizarRefreshTokenUsuario( Usuario usuario, String refreshToken, LocalDateTime expiracao ) {
		usuario.setRefreshToken( refreshToken );
		usuario.setExpiracaoRefreshToken( expiracao );
		repository.save( usuario );
	}
	
	public Usuario buscarPorRefreshToken(String token) {
		return repository.findByRefreshToken(token)
				.orElse(null);
	}
}
