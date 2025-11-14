package com.gs.fiap.jobfitscore.domain.autenticacao;

public enum Role {
	ADMIN(3),
	USUARIO(2),
	EMPRESA(2);
	
	private final int nivel;
	
	Role(int nivel) {
		this.nivel = nivel;
	}
	
	public int getNivel(){
		return nivel;
	}
}
