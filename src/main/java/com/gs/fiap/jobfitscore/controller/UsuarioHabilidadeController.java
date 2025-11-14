package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.usuariohabilidade.UsuarioHabilidadeDTO;
import com.gs.fiap.jobfitscore.domain.usuariohabilidade.UsuarioHabilidadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-habilidade")
public class UsuarioHabilidadeController {
	
	private final UsuarioHabilidadeService uhS;
	
	public UsuarioHabilidadeController(UsuarioHabilidadeService uhS) {
		this.uhS = uhS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<UsuarioHabilidadeDTO>> listar() {
		List<UsuarioHabilidadeDTO> lista = uhS.listar();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<UsuarioHabilidadeDTO> buscar(@PathVariable Long id) {
		UsuarioHabilidadeDTO dto = uhS.buscarPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/buscar-por-usuario/{usuarioId}")
	public ResponseEntity<List<UsuarioHabilidadeDTO>> buscarPorUsuario(@PathVariable Long usuarioId) {
		List<UsuarioHabilidadeDTO> lista = uhS.buscarPorUsuario(usuarioId);
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<UsuarioHabilidadeDTO> cadastrar(@Valid @RequestBody UsuarioHabilidadeDTO dto) {
		UsuarioHabilidadeDTO criado = uhS.cadastrar(dto);
		return ResponseEntity.status(201).body(criado);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		uhS.deletar(id);
		return ResponseEntity.noContent().build();
	}
}

