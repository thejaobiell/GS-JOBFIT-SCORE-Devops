package com.gs.fiap.jobfitscore.domain.candidatura;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
	List<Candidatura> findByUsuario_Id(Long usuarioId);
	List<Candidatura> findByVaga_Id(Long idVaga);
}
