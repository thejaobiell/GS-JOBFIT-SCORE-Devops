package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.habilidade.HabilidadeDTO;
import com.gs.fiap.jobfitscore.domain.habilidade.HabilidadeService;
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
@RequestMapping("/api/habilidades")
public class HabilidadeController {
	
	private final HabilidadeService hS;
	
	public HabilidadeController(HabilidadeService hS) {
		this.hS = hS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<HabilidadeDTO> pageHabilidades = hS.listarHabilidades(pageable);
		
		Map<String, Object> response = new HashMap<>();
		response.put("content", pageHabilidades.getContent());
		response.put("currentPage", pageHabilidades.getNumber());
		response.put("totalItems", pageHabilidades.getTotalElements());
		response.put("totalPages", pageHabilidades.getTotalPages());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<HabilidadeDTO> buscarPorId(@PathVariable Long id) {
		HabilidadeDTO dto = hS.buscarHabilidadePorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<HabilidadeDTO> criar(@Valid @RequestBody HabilidadeDTO dto) {
		HabilidadeDTO criado = hS.cadastrarHabilidade(dto);
		return ResponseEntity.status(201).body(criado);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<HabilidadeDTO> atualizar(@PathVariable Long id, @Valid @RequestBody HabilidadeDTO dto) {
		HabilidadeDTO atualizado = hS.atualizarHabilidade(id, dto);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		hS.deletarHabilidade(id);
		return ResponseEntity.noContent().build();
	}
}
