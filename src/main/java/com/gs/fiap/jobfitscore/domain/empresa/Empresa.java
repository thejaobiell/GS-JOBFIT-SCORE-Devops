package com.gs.fiap.jobfitscore.domain.empresa;

import com.gs.fiap.jobfitscore.domain.autenticacao.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String nome;
	
	@Column(nullable = false, length = 14, unique = true)
	private String cnpj;
	
	@Column(nullable = false, length = 100, unique = true)
	private String email;
	
	@Column(nullable = false, length = 200)
	private String senha;
	
	@Column(name = "refresh_token")
	private String refreshToken;
	
	@Column(name = "expiracao_refresh_token")
	private LocalDateTime expiracaoRefreshToken;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + Role.EMPRESA.name()));
	}
	
	@Override
	public String getPassword() {
		return senha;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	public String novoRefreshToken() {
		this.refreshToken = UUID.randomUUID().toString();
		this.expiracaoRefreshToken = LocalDateTime.now().plusMinutes(120);
		return refreshToken;
	}
	
	public boolean refreshTokenExpirado() {
		return expiracaoRefreshToken != null && expiracaoRefreshToken.isBefore(LocalDateTime.now());
	}
	
	public Role getRole() {
		return Role.EMPRESA;
	}
}