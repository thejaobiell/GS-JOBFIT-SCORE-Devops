package com.gs.fiap.jobfitscore.domain.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosRefreshToken( @NotBlank String refreshToken) {
}
