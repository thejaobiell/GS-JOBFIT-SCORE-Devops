package com.gs.fiap.jobfitscore.controller;
import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import com.gs.fiap.jobfitscore.domain.usuario.UsuarioDTO;
import com.gs.fiap.jobfitscore.domain.usuario.UsuarioService;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	private final UsuarioService uS;
	
	public UsuarioController( UsuarioService uS ) {
		this.uS = uS;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listarUsuarios(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<UsuarioDTO> pageUsuarios = uS.listarUsuarios(pageable);
		
		Map<String, Object> response = new HashMap<>();
		response.put("content", pageUsuarios.getContent());
		response.put("currentPage", pageUsuarios.getNumber());
		response.put("totalItems", pageUsuarios.getTotalElements());
		response.put("totalPages", pageUsuarios.getTotalPages());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorID(@PathVariable Long id) {
		UsuarioDTO dto = uS.buscarUsuarioPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<UsuarioDTO> criar(@RequestBody Usuario usuario) {
		UsuarioDTO criado = uS.salvarUsuario(usuario);
		return ResponseEntity.status(201).body(criado);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
		UsuarioDTO atualizado = uS.atualizarUsuario(id, usuario);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		uS.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
}
