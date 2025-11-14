package com.gs.fiap.jobfitscore.domain.candidatura;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CandidaturaDTO {
	
	private Long idCandidatura;
	
	@NotNull(message = "Usuário é obrigatório")
	private Long usuarioId;
	
	@NotNull(message = "Vaga é obrigatória")
	private Long vagaId;
	
	private LocalDateTime dataCandidatura;
	
	@Pattern(regexp = "Em Análise|Triagem|Aprovado|Reprovado", message = "Status inválido")
	private String status;
	
	public static CandidaturaDTO fromEntity(Candidatura c) {
		CandidaturaDTO dto = new CandidaturaDTO();
		dto.setIdCandidatura(c.getIdCandidatura());
		dto.setUsuarioId(c.getUsuario().getId());
		dto.setVagaId(c.getVaga().getId());
		dto.setDataCandidatura(c.getDataCandidatura());
		dto.setStatus(c.getStatus());
		return dto;
	}
	
	public Candidatura toEntity() {
		Candidatura c = new Candidatura();
		c.setIdCandidatura(this.idCandidatura);
		c.setDataCandidatura(this.dataCandidatura != null ? this.dataCandidatura : LocalDateTime.now());
		c.setStatus(this.status != null ? this.status : "Em Análise");
		return c;
	}
}
