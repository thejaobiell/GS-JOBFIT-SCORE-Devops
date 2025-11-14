package com.gs.fiap.jobfitscore.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmailIgnoreCase(String email);
	@Query("""
    SELECT u FROM Usuario u
    WHERE u.refreshToken = :token
    AND u.expiracaoRefreshToken > CURRENT_TIMESTAMP
""")
	Optional<Usuario> findByRefreshToken(@Param("token") String token);
}

