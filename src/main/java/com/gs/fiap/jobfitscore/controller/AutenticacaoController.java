package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.autenticacao.DadosLogin;
import com.gs.fiap.jobfitscore.domain.autenticacao.DadosToken;
import com.gs.fiap.jobfitscore.domain.autenticacao.DadosRefreshToken;
import com.gs.fiap.jobfitscore.domain.autenticacao.TokenService;
import com.gs.fiap.jobfitscore.domain.empresa.Empresa;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaService;
import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import com.gs.fiap.jobfitscore.domain.usuario.UsuarioService;
import com.gs.fiap.jobfitscore.infra.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {
	
	private final AuthenticationManager aM;
	private final TokenService tS;
	private final UsuarioService uS;
	private final EmpresaService eS;
	
	public AutenticacaoController( AuthenticationManager aM, TokenService tS,
	                               UsuarioService uS, EmpresaService eS) {
		this.aM = aM;
		this.tS = tS;
		this.uS = uS;
		this.eS = eS;
	}
	
	@Operation(security = {})
	@PostMapping("/login")
	public ResponseEntity<DadosToken> efetuarLogin(@Valid @RequestBody DadosLogin dados) {
		try {
			var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
			Authentication authentication = aM.authenticate(authToken);
			
			Object principal = authentication.getPrincipal();
			
			String tokenAcesso;
			String refreshToken;
			LocalDateTime expiracaoRefresh = LocalDateTime.now().plusMinutes(10080);
			
			if (principal instanceof Usuario usuario) {
				tokenAcesso = tS.gerarTokenUsuario(usuario);
				refreshToken = tS.gerarRefreshTokenUsuario(usuario);
				uS.atualizarRefreshTokenUsuario(usuario, refreshToken, expiracaoRefresh);
				
			} else if (principal instanceof Empresa empresa) {
				tokenAcesso = tS.gerarTokenEmpresa(empresa);
				refreshToken = tS.gerarRefreshTokenEmpresa(empresa);
				eS.atualizarRefreshTokenEmpresa(empresa, refreshToken, expiracaoRefresh);
				
			} else {
				throw new RegraDeNegocioException("Tipo de autenticação desconhecido");
			}
			
			return ResponseEntity.ok(new DadosToken(tokenAcesso, refreshToken, expiracaoRefresh));
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping("/atualizar-token")
	@Operation(security = {})
	public ResponseEntity<DadosToken> atualizarToken(@Valid @RequestBody DadosRefreshToken dados) {
		
		var usuario = uS.buscarPorRefreshToken(dados.refreshToken());
		if (usuario != null && !usuario.refreshTokenExpirado()) {
			String novoTokenAcesso = tS.gerarTokenUsuario(usuario);
			return ResponseEntity.ok(new DadosToken(
					novoTokenAcesso,
					usuario.getRefreshToken(),
					usuario.getExpiracaoRefreshToken()
			));
		}
		var empresa = eS.buscarPorRefreshToken(dados.refreshToken());
		if (empresa != null && !empresa.refreshTokenExpirado()) {
			String novoTokenAcesso = tS.gerarTokenEmpresa(empresa);
			return ResponseEntity.ok(new DadosToken(
					novoTokenAcesso,
					empresa.getRefreshToken(),
					empresa.getExpiracaoRefreshToken()
			));
		}
		throw new RegraDeNegocioException("Refresh token inválido ou expirado");
	}
}