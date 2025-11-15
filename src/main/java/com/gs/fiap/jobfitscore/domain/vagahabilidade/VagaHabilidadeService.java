package com.gs.fiap.jobfitscore.domain.vagahabilidade;

import com.gs.fiap.jobfitscore.domain.habilidade.HabilidadeRepository;
import com.gs.fiap.jobfitscore.domain.vaga.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VagaHabilidadeService {
	
	private final VagaHabilidadeRepository vhR;
	private final VagaRepository vR;
	private final HabilidadeRepository hR;
	
	public VagaHabilidadeService(VagaHabilidadeRepository vhR, VagaRepository vR, HabilidadeRepository hR) {
		this.vhR = vhR;
		this.vR = vR;
		this.hR = hR;
	}
	
	public VagaHabilidadeDTO cadastrar(VagaHabilidadeDTO dto) {
		var vaga = vR.findById(dto.getVagaId())
				.orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));
		var habilidade = hR.findById(dto.getHabilidadeId())
				.orElseThrow(() -> new EntityNotFoundException("Habilidade não encontrada"));
		
		VagaHabilidade vh = dto.toEntity(vaga, habilidade);
		return VagaHabilidadeDTO.fromEntity(vhR.save(vh));
	}
	
	public List<VagaHabilidadeDTO> listar() {
		return vhR.findAll()
				.stream()
				.map(VagaHabilidadeDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public List<VagaHabilidadeDTO> buscarPorVaga(Long vagaId) {
		return vhR.findByVaga_Id(vagaId)
				.stream()
				.map(VagaHabilidadeDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public List<VagaHabilidadeDTO> buscarPorHabilidade(Long habilidadeId) {
		return vhR.findByHabilidade_Id(habilidadeId)
				.stream()
				.map(VagaHabilidadeDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	public VagaHabilidadeDTO atualizar(Long id, VagaHabilidadeDTO dto) {
		VagaHabilidade vh = vhR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("VagaHabilidade não encontrada"));
		var vaga = vR.findById(dto.getVagaId())
				.orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));
		var habilidade = hR.findById(dto.getHabilidadeId())
				.orElseThrow(() -> new EntityNotFoundException("Habilidade não encontrada"));
		vh.setVaga(vaga);
		vh.setHabilidade(habilidade);
		VagaHabilidade salvo = vhR.save(vh);
		return VagaHabilidadeDTO.fromEntity(salvo);
	}
	
	public void deletar(Long id) {
		vhR.deleteById(id);
	}
}
