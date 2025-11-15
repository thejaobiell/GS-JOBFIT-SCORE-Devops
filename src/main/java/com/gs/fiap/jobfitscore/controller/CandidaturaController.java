package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.candidatura.CandidaturaDTO;
import com.gs.fiap.jobfitscore.domain.candidatura.CandidaturaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {
	
	private final CandidaturaService cS;
	
	public CandidaturaController(CandidaturaService cS) {
		this.cS = cS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "idCandidatura") String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<CandidaturaDTO> pageCandidaturas = cS.listarCandidaturas(pageable);
		
		Map<String, Object> response = new HashMap<>();
		response.put("content", pageCandidaturas.getContent());
		response.put("currentPage", pageCandidaturas.getNumber());
		response.put("totalItems", pageCandidaturas.getTotalElements());
		response.put("totalPages", pageCandidaturas.getTotalPages());
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<CandidaturaDTO> criar(@Valid @RequestBody CandidaturaDTO dto) {
		CandidaturaDTO criada = cS.cadastrarCandidatura(dto);
		return ResponseEntity.status(201).body(criada);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public CandidaturaDTO buscarID( @PathVariable Long id) {
		return cS.buscarCandidaturaPorId(id);
	}
	
	@GetMapping("/buscar-por-usuario/{usuarioId}")
	public List<CandidaturaDTO> buscarUsuario( @PathVariable Long usuarioId) {
		return cS.buscarCandidaturasPorUsuario(usuarioId);
	}
	
	@GetMapping("/buscar-por-vaga")
	public List<CandidaturaDTO> buscarPorVaga(@RequestParam Long vagaId) {
		return cS.buscarCandidaturaPorVaga(vagaId);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<CandidaturaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CandidaturaDTO dto) {
		CandidaturaDTO atualizada = cS.atualizarCandidatura(id, dto);
		return ResponseEntity.ok(atualizada);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		cS.deletarCandidatura(id);
		return ResponseEntity.noContent().build();
	}
}
