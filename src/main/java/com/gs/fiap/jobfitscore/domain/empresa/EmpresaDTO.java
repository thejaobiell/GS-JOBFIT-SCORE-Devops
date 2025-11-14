package com.gs.fiap.jobfitscore.domain.empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
	
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "CNPJ é obrigatório")
	@Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
	private String cnpj;
	
	@Email(message = "Email inválido")
	@NotBlank(message = "Email é obrigatório")
	private String email;
	
	@NotBlank(message = "Senha é obrigatória")
	@Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
	private String senha;
	
	private String refreshToken;
	private LocalDateTime expiracaoRefreshToken;
	
	public static EmpresaDTO fromEntity(Empresa empresa) {
		return new EmpresaDTO(
				empresa.getId(),
				empresa.getNome(),
				empresa.getCnpj(),
				empresa.getEmail(),
				empresa.getSenha(),
				empresa.getRefreshToken(),
				empresa.getExpiracaoRefreshToken()
		);
	}
}
