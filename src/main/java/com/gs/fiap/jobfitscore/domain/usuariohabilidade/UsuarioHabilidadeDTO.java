package com.gs.fiap.jobfitscore.domain.usuariohabilidade;

import com.gs.fiap.jobfitscore.domain.habilidade.Habilidade;
import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioHabilidadeDTO {
	
	private Long id;
	
	@NotNull(message = "Usuário é obrigatório")
	private Long usuarioId;
	
	@NotNull(message = "Habilidade é obrigatória")
	private Long habilidadeId;
	
	public static UsuarioHabilidadeDTO fromEntity(UsuarioHabilidade uh) {
		return new UsuarioHabilidadeDTO(
				uh.getId(),
				uh.getUsuario().getId(),
				uh.getHabilidade().getId()
		);
	}
	
	public UsuarioHabilidade toEntity() {
		UsuarioHabilidade uh = new UsuarioHabilidade();
		Usuario u = new Usuario();
		u.setId(this.usuarioId);
		Habilidade h = new Habilidade();
		h.setId(this.habilidadeId);
		uh.setUsuario(u);
		uh.setHabilidade(h);
		return uh;
	}
}
