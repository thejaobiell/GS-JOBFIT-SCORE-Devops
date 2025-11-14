package com.gs.fiap.jobfitscore.domain.curso;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CursoDTO {
	
	private Long idCurso;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 150, message = "Nome pode ter no máximo 150 caracteres")
	private String nome;
	
	@Size(max = 150, message = "Instituição pode ter no máximo 150 caracteres")
	private String instituicao;
	
	@Min(value = 0, message = "Carga horária deve ser positiva")
	private Integer cargaHoraria;
	
	@NotNull(message = "Usuário é obrigatório")
	private Long usuarioId;
	
	public static CursoDTO fromEntity(Curso curso) {
		CursoDTO dto = new CursoDTO();
		dto.setIdCurso(curso.getIdCurso());
		dto.setNome(curso.getNome());
		dto.setInstituicao(curso.getInstituicao());
		dto.setCargaHoraria(curso.getCargaHoraria());
		dto.setUsuarioId(curso.getUsuario().getId());
		return dto;
	}
	
	public Curso toEntity() {
		Curso curso = new Curso();
		curso.setIdCurso(this.idCurso);
		curso.setNome(this.nome);
		curso.setInstituicao(this.instituicao);
		curso.setCargaHoraria(this.cargaHoraria);
		return curso;
	}
}
