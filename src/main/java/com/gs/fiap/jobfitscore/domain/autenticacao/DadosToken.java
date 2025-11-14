package com.gs.fiap.jobfitscore.domain.autenticacao;

import java.time.LocalDateTime;

public record DadosToken( String tokenAcesso, String refreshToken, LocalDateTime expiracaoRefreshToken ) {
}
