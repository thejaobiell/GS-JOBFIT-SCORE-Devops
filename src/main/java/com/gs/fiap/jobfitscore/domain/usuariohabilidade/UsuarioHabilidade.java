package com.gs.fiap.jobfitscore.domain.usuariohabilidade;

import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import com.gs.fiap.jobfitscore.domain.habilidade.Habilidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_habilidade", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "habilidade_id"}) )
public class UsuarioHabilidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario_habilidade")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "habilidade_id", nullable = false)
	private Habilidade habilidade;
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario( Usuario usuario ) {
		this.usuario = usuario;
	}
	
	public Habilidade getHabilidade() {
		return habilidade;
	}
	
	public void setHabilidade( Habilidade habilidade ) {
		this.habilidade = habilidade;
	}
}
