package com.gs.fiap.jobfitscore.domain.candidatura;

import com.gs.fiap.jobfitscore.domain.usuario.UsuarioRepository;
import com.gs.fiap.jobfitscore.domain.vaga.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CandidaturaService {
	
	private final CandidaturaRepository cR;
	private final UsuarioRepository uR;
	private final VagaRepository vR;
	
	public CandidaturaService(CandidaturaRepository cR, UsuarioRepository uR, VagaRepository vR) {
		this.cR = cR;
		this.uR = uR;
		this.vR = vR;
	}
	
	public CandidaturaDTO cadastrarCandidatura(CandidaturaDTO dto) {
		var usuario = uR.findById(dto.getUsuarioId())
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
		var vaga = vR.findById(dto.getVagaId())
				.orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));
		
		Candidatura c = dto.toEntity();
		c.setUsuario(usuario);
		c.setVaga(vaga);
		return CandidaturaDTO.fromEntity(cR.save(c));
	}
	
	public Page<CandidaturaDTO> listarCandidaturas(Pageable pageable) {
		return cR.findAll(pageable)
				.map(CandidaturaDTO::fromEntity);
	}
	
	public CandidaturaDTO buscarCandidaturaPorId(Long id) {
		return cR.findById(id)
				.map(CandidaturaDTO::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada"));
	}
	
	public List<CandidaturaDTO> buscarCandidaturaPorVaga(Long vagaId) {
		return cR.findByVaga_Id(vagaId)
				.stream()
				.map(CandidaturaDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public List<CandidaturaDTO> buscarCandidaturasPorUsuario(Long usuarioId) {
		return cR.findByUsuario_Id(usuarioId)
				.stream()
				.map(CandidaturaDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public List<CandidaturaDTO> buscarCandidaturasPorVaga(Long vagaId) {
		return cR.findByVaga_Id(vagaId)
				.stream()
				.map(CandidaturaDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public CandidaturaDTO atualizarCandidatura(Long id, CandidaturaDTO dto) {
		Candidatura c = cR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada"));
		
		if (dto.getStatus() != null) c.setStatus(dto.getStatus());
		return CandidaturaDTO.fromEntity(cR.save(c));
	}
	
	public void deletarCandidatura(Long id) {
		cR.deleteById(id);
	}
}
