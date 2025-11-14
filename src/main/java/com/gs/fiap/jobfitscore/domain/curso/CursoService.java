package com.gs.fiap.jobfitscore.domain.curso;

import com.gs.fiap.jobfitscore.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {
	
	private final CursoRepository cR;
	private final UsuarioRepository uR;
	
	public CursoService(CursoRepository cR, UsuarioRepository uR) {
		this.cR = cR;
		this.uR = uR;
	}
	
	public CursoDTO cadastrar(CursoDTO dto) {
		var usuario = uR.findById(dto.getUsuarioId())
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
		
		Curso curso = dto.toEntity();
		curso.setUsuario(usuario);
		return CursoDTO.fromEntity(cR.save(curso));
	}
	
	public List<CursoDTO> listar() {
		return cR.findAll().stream().map(CursoDTO::fromEntity).collect(Collectors.toList());
	}
	
	public List<CursoDTO> buscarPorUsuario(Long usuarioId) {
		return cR.findByUsuario_Id(usuarioId).stream().map(CursoDTO::fromEntity).collect(Collectors.toList());
	}
	
	public CursoDTO buscarPorId(Long id) {
		return cR.findById(id).map(CursoDTO::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
	}
	
	public CursoDTO atualizar(Long id, CursoDTO dto) {
		Curso curso = cR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
		
		if (dto.getNome() != null) curso.setNome(dto.getNome());
		if (dto.getInstituicao() != null) curso.setInstituicao(dto.getInstituicao());
		if (dto.getCargaHoraria() != null) curso.setCargaHoraria(dto.getCargaHoraria());
		
		return CursoDTO.fromEntity(cR.save(curso));
	}
	
	public void deletar(Long id) {
		cR.deleteById(id);
	}
}
