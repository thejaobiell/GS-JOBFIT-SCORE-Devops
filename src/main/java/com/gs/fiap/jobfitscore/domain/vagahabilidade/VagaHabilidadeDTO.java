package com.gs.fiap.jobfitscore.domain.vagahabilidade;

import com.gs.fiap.jobfitscore.domain.habilidade.Habilidade;
import com.gs.fiap.jobfitscore.domain.vaga.Vaga;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaHabilidadeDTO {
	
	private Long id;
	private Long vagaId;
	private Long habilidadeId;
	
	public static VagaHabilidadeDTO fromEntity(VagaHabilidade vh) {
		return new VagaHabilidadeDTO(
				vh.getId(),
				vh.getVaga().getId(),
				vh.getHabilidade().getId()
		);
	}
	
	public VagaHabilidade toEntity(Vaga vaga, Habilidade habilidade) {
		VagaHabilidade vh = new VagaHabilidade();
		vh.setVaga(vaga);
		vh.setHabilidade(habilidade);
		return vh;
	}
}
