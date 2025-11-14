package com.gs.fiap.jobfitscore.domain.vaga;

import com.gs.fiap.jobfitscore.domain.empresa.Empresa;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VagaService {
	
	private final VagaRepository vR;
	private final EmpresaRepository eR;
	
	public VagaService(VagaRepository vR, EmpresaRepository eR) {
		this.vR = vR;
		this.eR = eR;
	}
	
	@Cacheable("vagas")
	public Page<VagaDTO> listarVagas( Pageable pageable) {
		return vR.findAll(pageable)
				.map(VagaDTO::fromEntity);
	}
	
	
	public VagaDTO buscarVagaPorId(Long id) {
		Vaga vaga = vR.findById(id)
				.orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
		return VagaDTO.fromEntity(vaga);
	}
	
	public VagaDTO cadastrarVaga(VagaDTO dto) {
		Empresa empresa = eR.findById(dto.getEmpresaId())
				.orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
		Vaga vaga = dto.toEntity();
		vaga.setEmpresa(empresa);
		return VagaDTO.fromEntity(vR.save(vaga));
	}
	
	public VagaDTO atualizarVaga(Long id, VagaDTO dto) {
		Vaga vaga = vR.findById(id)
				.orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
		Empresa empresa = eR.findById(dto.getEmpresaId())
				.orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
		vaga.setTitulo(dto.getTitulo());
		vaga.setEmpresa(empresa);
		return VagaDTO.fromEntity(vR.save(vaga));
	}
	
	public void deletarVaga(Long id) {
		vR.deleteById(id);
	}
}
