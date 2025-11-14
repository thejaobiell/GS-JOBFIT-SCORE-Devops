package com.gs.fiap.jobfitscore.infra.security;

import com.gs.fiap.jobfitscore.domain.autenticacao.TokenService;
import com.gs.fiap.jobfitscore.domain.autenticacao.UsuarioEmpresaUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FiltroTokenAcesso extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	private final UsuarioEmpresaUserDetailsService userDetailsService;
	
	public FiltroTokenAcesso(TokenService tokenService, UsuarioEmpresaUserDetailsService userDetailsService) {
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarTokenRequisicao(request);
		
		if (token != null) {
			try {
				String email = tokenService.getSubject(token);
				
				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails autenticado = userDetailsService.loadUserByUsername(email);
					
					Authentication authentication =
							new UsernamePasswordAuthenticationToken(autenticado, null, autenticado.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				
			} catch (Exception e) {
				System.err.println("Erro ao validar token: " + e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String recuperarTokenRequisicao(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}
}
