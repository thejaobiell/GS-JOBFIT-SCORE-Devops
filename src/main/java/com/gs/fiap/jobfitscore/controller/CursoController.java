package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.curso.CursoDTO;
import com.gs.fiap.jobfitscore.domain.curso.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
	
	private final CursoService cS;
	
	public CursoController(CursoService cS) {
		this.cS = cS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<CursoDTO>> listar() {
		List<CursoDTO> cursos = cS.listar();
		return ResponseEntity.ok(cursos);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<CursoDTO> cadastrar( @Valid @RequestBody CursoDTO dto) {
		CursoDTO criado = cS.cadastrar(dto);
		return ResponseEntity.status(201).body(criado);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Long id) {
		CursoDTO curso = cS.buscarPorId(id);
		return ResponseEntity.ok(curso);
	}
	
	@GetMapping("/buscar-por-usuario/{usuarioId}")
	public ResponseEntity<List<CursoDTO>> buscarPorUsuario(@PathVariable Long usuarioId) {
		List<CursoDTO> cursos = cS.buscarPorUsuario(usuarioId);
		return ResponseEntity.ok(cursos);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<CursoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CursoDTO dto) {
		CursoDTO atualizado = cS.atualizar(id, dto);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		cS.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
