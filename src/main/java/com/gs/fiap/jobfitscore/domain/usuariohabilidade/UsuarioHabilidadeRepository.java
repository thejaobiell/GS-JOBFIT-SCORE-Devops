package com.gs.fiap.jobfitscore.domain.usuariohabilidade;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioHabilidadeRepository extends JpaRepository<UsuarioHabilidade, Long> {
	List<UsuarioHabilidade> findByUsuario_Id(Long usuarioId);
}
