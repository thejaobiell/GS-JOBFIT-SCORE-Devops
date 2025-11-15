package com.gs.fiap.jobfitscore.domain.usuariohabilidade;

import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import com.gs.fiap.jobfitscore.domain.usuario.UsuarioRepository;
import com.gs.fiap.jobfitscore.domain.habilidade.Habilidade;
import com.gs.fiap.jobfitscore.domain.habilidade.HabilidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioHabilidadeService {
	
	private final UsuarioHabilidadeRepository uhR;
	private final UsuarioRepository uR;
	private final HabilidadeRepository hR;
	
	public UsuarioHabilidadeService(UsuarioHabilidadeRepository uhR, UsuarioRepository uR, HabilidadeRepository hR) {
		this.uhR = uhR;
		this.uR = uR;
		this.hR = hR;
	}
	
	public List<UsuarioHabilidadeDTO> listar() {
		return uhR.findAll()
				.stream()
				.map(UsuarioHabilidadeDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public UsuarioHabilidadeDTO buscarPorId(Long id) {
		UsuarioHabilidade uh = uhR.findById(id)
				.orElseThrow(() -> new RuntimeException("Relação não encontrada"));
		return UsuarioHabilidadeDTO.fromEntity(uh);
	}
	
	public List<UsuarioHabilidadeDTO> buscarPorUsuario(Long usuarioId) {
		return uhR.findByUsuario_Id(usuarioId)
				.stream()
				.map(UsuarioHabilidadeDTO::fromEntity)
				.collect(Collectors.toList());
	}

	public UsuarioHabilidadeDTO cadastrar(UsuarioHabilidadeDTO dto) {
		Usuario usuario = uR.findById(dto.getUsuarioId())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		Habilidade habilidade = hR.findById(dto.getHabilidadeId())
				.orElseThrow(() -> new RuntimeException("Habilidade não encontrada"));
		UsuarioHabilidade uh = new UsuarioHabilidade(null, usuario, habilidade);
		return UsuarioHabilidadeDTO.fromEntity(uhR.save(uh));
	}

	public UsuarioHabilidadeDTO atualizar(Long id, UsuarioHabilidadeDTO dto) {
		UsuarioHabilidade uh = uhR.findById(id)
				.orElseThrow(() -> new RuntimeException("Relação não encontrada"));
		Usuario usuario = uR.findById(dto.getUsuarioId())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		Habilidade habilidade = hR.findById(dto.getHabilidadeId())
				.orElseThrow(() -> new RuntimeException("Habilidade não encontrada"));
		uh.setUsuario(usuario);
		uh.setHabilidade(habilidade);
		return UsuarioHabilidadeDTO.fromEntity(uhR.save(uh));
	}
	public void deletar(Long id) {
		uhR.deleteById(id);
	}
}
