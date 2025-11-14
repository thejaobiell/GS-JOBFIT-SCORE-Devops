package com.gs.fiap.jobfitscore.domain.autenticacao;

import com.gs.fiap.jobfitscore.domain.usuario.UsuarioRepository;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioEmpresaUserDetailsService implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;
	private final EmpresaRepository empresaRepository;
	
	public UsuarioEmpresaUserDetailsService(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.empresaRepository = empresaRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Busca no repositório de usuários
		var usuario = usuarioRepository.findByEmailIgnoreCase(email);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		
		// Se não encontrou, busca no repositório de empresas
		var empresa = empresaRepository.findByEmailIgnoreCase(email);
		if (empresa.isPresent()) {
			return empresa.get();
		}
		
		throw new UsernameNotFoundException("Usuário ou empresa não encontrado com email: " + email);
	}
}