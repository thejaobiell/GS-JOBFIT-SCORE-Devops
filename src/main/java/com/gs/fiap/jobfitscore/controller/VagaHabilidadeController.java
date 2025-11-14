package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.vagahabilidade.VagaHabilidadeDTO;
import com.gs.fiap.jobfitscore.domain.vagahabilidade.VagaHabilidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaga-habilidade")
public class VagaHabilidadeController {
	
	private final VagaHabilidadeService vhS;
	
	public VagaHabilidadeController(VagaHabilidadeService vhS) {
		this.vhS = vhS;
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<VagaHabilidadeDTO> cadastrar(@RequestBody VagaHabilidadeDTO dto) {
		VagaHabilidadeDTO criado = vhS.cadastrar(dto);
		return ResponseEntity.status(201).body(criado);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<VagaHabilidadeDTO>> listar() {
		List<VagaHabilidadeDTO> lista = vhS.listar();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/buscar-por-vaga")
	public ResponseEntity<List<VagaHabilidadeDTO>> buscarPorVaga(@RequestParam Long vagaId) {
		List<VagaHabilidadeDTO> lista = vhS.buscarPorVaga(vagaId);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/buscar-por-habilidade")
	public ResponseEntity<List<VagaHabilidadeDTO>> buscarPorHabilidade(@RequestParam Long habilidadeId) {
		List<VagaHabilidadeDTO> lista = vhS.buscarPorHabilidade(habilidadeId);
		return ResponseEntity.ok(lista);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		vhS.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
