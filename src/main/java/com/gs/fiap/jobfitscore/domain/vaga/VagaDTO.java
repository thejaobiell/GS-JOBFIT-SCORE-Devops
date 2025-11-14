package com.gs.fiap.jobfitscore.domain.vaga;

import com.gs.fiap.jobfitscore.domain.empresa.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaDTO {
	
	private Long id;
	
	@NotBlank(message = "Título é obrigatório")
	@Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
	private String titulo;
	
	@NotNull(message = "EmpresaId é obrigatório")
	private Long empresaId;
	
	public static VagaDTO fromEntity(Vaga vaga) {
		Long empresaId = vaga.getEmpresa() != null ? vaga.getEmpresa().getId() : null;
		return new VagaDTO(vaga.getId(), vaga.getTitulo(), empresaId);
	}
	
	public Vaga toEntity() {
		Vaga vaga = new Vaga();
		vaga.setId(this.id);
		vaga.setTitulo(this.titulo);
		Empresa empresa = new Empresa();
		empresa.setId(this.empresaId);
		vaga.setEmpresa(empresa);
		return vaga;
	}
}
