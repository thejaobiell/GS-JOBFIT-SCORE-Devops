package com.gs.fiap.jobfitscore.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@Email(message = "Email inválido")
	@NotBlank(message = "Email é obrigatório")
	private String email;
	
	@NotBlank(message = "Senha é obrigatória")
	@Size(min = 8, message = "Senha deve ter pelo menos 6 caracteres")
	private String senha;
	
	private String refreshToken;
	private LocalDateTime expiracaoRefreshToken;
	
	public static UsuarioDTO fromEntity(Usuario usuario) {
		return new UsuarioDTO(
				usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getSenha(),
				usuario.getRefreshToken(),
				usuario.getExpiracaoRefreshToken()
		);
	}
}
