package com.gs.fiap.jobfitscore.domain.habilidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabilidadeDTO {
	
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
	private String nome;
	
	public static HabilidadeDTO fromEntity(Habilidade h) {
		return new HabilidadeDTO(h.getId(), h.getNome());
	}
	
	public Habilidade toEntity() {
		Habilidade h = new Habilidade();
		h.setId(this.id);
		h.setNome(this.nome);
		return h;
	}
}
