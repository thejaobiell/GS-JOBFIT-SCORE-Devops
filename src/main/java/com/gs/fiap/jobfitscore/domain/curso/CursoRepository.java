package com.gs.fiap.jobfitscore.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	List<Curso> findByUsuario_Id(Long usuarioId);
}
