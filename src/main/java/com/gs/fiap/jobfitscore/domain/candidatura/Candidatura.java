package com.gs.fiap.jobfitscore.domain.candidatura;

import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import com.gs.fiap.jobfitscore.domain.vaga.Vaga;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "candidaturas")
public class Candidatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCandidatura;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "vaga_id", nullable = false)
	private Vaga vaga;
	
	@Column(name = "data_candidatura", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCandidatura = LocalDateTime.now();
	
	@Column(length = 50)
	private String status = "Em Análise";
	
	public Long getIdCandidatura() {
		return idCandidatura;
	}
	
	public void setIdCandidatura( Long idCandidatura ) {
		this.idCandidatura = idCandidatura;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario( Usuario usuario ) {
		this.usuario = usuario;
	}
	
	public Vaga getVaga() {
		return vaga;
	}
	
	public void setVaga( Vaga vaga ) {
		this.vaga = vaga;
	}
	
	public LocalDateTime getDataCandidatura() {
		return dataCandidatura;
	}
	
	public void setDataCandidatura( LocalDateTime dataCandidatura ) {
		this.dataCandidatura = dataCandidatura;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus( String status ) {
		this.status = status;
	}
}
