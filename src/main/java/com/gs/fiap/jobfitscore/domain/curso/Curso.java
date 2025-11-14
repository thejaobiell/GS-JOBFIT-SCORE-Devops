package com.gs.fiap.jobfitscore.domain.curso;

import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cursos")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCurso;
	
	@Column(nullable = false, length = 150)
	private String nome;
	
	@Column(length = 150)
	private String instituicao;
	
	private Integer cargaHoraria;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
}
