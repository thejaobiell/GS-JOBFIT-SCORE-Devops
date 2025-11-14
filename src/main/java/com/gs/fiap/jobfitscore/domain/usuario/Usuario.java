package com.gs.fiap.jobfitscore.domain.usuario;

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
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 200)
	private String senha;
	
	@Column(name = "refresh_token")
	private String refreshToken;
	
	@Column(name = "expiracao_refresh_token")
	private LocalDateTime expiracaoRefreshToken;
	
	@Column(name = "is_admin", nullable = false)
	private boolean admin = false;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public LocalDateTime getExpiracaoRefreshToken() {
		return expiracaoRefreshToken;
	}
	
	public void setExpiracaoRefreshToken(LocalDateTime expiracaoRefreshToken) {
		this.expiracaoRefreshToken = expiracaoRefreshToken;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (admin) {
			return List.of(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()));
		}
		return List.of(new SimpleGrantedAuthority("ROLE_" + Role.USUARIO.name()));
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
		return admin ? Role.ADMIN : Role.USUARIO;
	}
}