package com.gs.fiap.jobfitscore.controller;

import com.gs.fiap.jobfitscore.domain.empresa.Empresa;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaDTO;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
	
	private final EmpresaService eS;
	
	public EmpresaController(EmpresaService eS) {
		this.eS = eS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<EmpresaDTO>> listar() {
		List<EmpresaDTO> empresas = eS.listarEmpresas();
		return ResponseEntity.ok(empresas);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<EmpresaDTO> buscarPorID(@PathVariable Long id) {
		EmpresaDTO empresa = eS.buscarEmpresaPorId(id);
		return ResponseEntity.ok(empresa);
	}
	
	@GetMapping("/buscar-por-cnpj")
	public ResponseEntity<EmpresaDTO> buscarCNPJ(@RequestParam String cnpj) {
		EmpresaDTO empresa = eS.buscarEmpresaPorCNPJ(cnpj);
		return ResponseEntity.ok(empresa);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<EmpresaDTO> criar(@RequestBody Empresa empresa) {
		EmpresaDTO criada = eS.criarEmpresa(empresa);
		return ResponseEntity.status(201).body(criada);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<EmpresaDTO> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
		EmpresaDTO atualizada = eS.atualizarEmpresa(id, empresa);
		return ResponseEntity.ok(atualizada); 
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		eS.deletarEmpresa(id);
		return ResponseEntity.noContent().build();
	}
}
