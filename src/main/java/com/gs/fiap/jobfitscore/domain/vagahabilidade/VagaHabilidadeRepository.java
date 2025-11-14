package com.gs.fiap.jobfitscore.domain.vagahabilidade;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VagaHabilidadeRepository extends JpaRepository<VagaHabilidade, Long> {
	
	List<VagaHabilidade> findByVaga_Id(Long vagaId);
	
	List<VagaHabilidade> findByHabilidade_Id(Long habilidadeId);
}

