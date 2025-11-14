package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.vaga.VagaDTO;
import com.gs.fiap.jobfitscore.domain.vaga.VagaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
	
	private final VagaService vS;
	
	public VagaController(VagaService vS) {
		this.vS = vS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listar(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<VagaDTO> pageVagas = vS.listarVagas(pageable);
		
		Map<String, Object> response = new HashMap<>();
		response.put("content", pageVagas.getContent());
		response.put("currentPage", pageVagas.getNumber());
		response.put("totalItems", pageVagas.getTotalElements());
		response.put("totalPages", pageVagas.getTotalPages());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<VagaDTO> buscar(@PathVariable Long id) {
		VagaDTO dto = vS.buscarVagaPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<VagaDTO> criar(@Valid @RequestBody VagaDTO vagaDTO) {
		VagaDTO criado = vS.cadastrarVaga(vagaDTO);
		return ResponseEntity.status(201).body(criado);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<VagaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VagaDTO vagaDTO) {
		VagaDTO atualizado = vS.atualizarVaga(id, vagaDTO);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		vS.deletarVaga(id);
		return ResponseEntity.noContent().build();
	}
}