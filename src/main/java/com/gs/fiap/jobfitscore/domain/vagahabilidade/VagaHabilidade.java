package com.gs.fiap.jobfitscore.domain.vagahabilidade;

import com.gs.fiap.jobfitscore.domain.vaga.Vaga;
import com.gs.fiap.jobfitscore.domain.habilidade.Habilidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vaga_habilidade", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"vaga_id", "habilidade_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaHabilidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vaga_habilidade")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vaga_id", nullable = false)
	private Vaga vaga;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "habilidade_id", nullable = false)
	private Habilidade habilidade;
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public Vaga getVaga() {
		return vaga;
	}
	
	public void setVaga( Vaga vaga ) {
		this.vaga = vaga;
	}
	
	public Habilidade getHabilidade() {
		return habilidade;
	}
	
	public void setHabilidade( Habilidade habilidade ) {
		this.habilidade = habilidade;
	}
}
