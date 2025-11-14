package com.gs.fiap.jobfitscore.domain.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	Optional<Empresa> findByEmailIgnoreCase(String email);
	Optional<Empresa> findByCnpj(String cnpj);
	@Query("""
        SELECT e FROM Empresa e
        WHERE e.refreshToken = :token
        AND e.expiracaoRefreshToken > CURRENT_TIMESTAMP
    """)
	Optional<Empresa> findByRefreshToken(@Param("token") String token);
}
