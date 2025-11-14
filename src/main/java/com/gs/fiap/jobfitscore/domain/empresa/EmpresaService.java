package com.gs.fiap.jobfitscore.domain.empresa;

import com.gs.fiap.jobfitscore.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpresaService {
	
	private final EmpresaRepository repository;
	private final PasswordEncoder encoder;
	
	public EmpresaService(EmpresaRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	public List<EmpresaDTO> listarEmpresas() {
		return repository.findAll().stream().map(EmpresaDTO::fromEntity).toList();
	}
	
	public EmpresaDTO buscarEmpresaPorId( Long id) {
		Empresa empresa = repository.findById(id)
				.orElseThrow(() -> new RegraDeNegocioException("Empresa de ID: " + id + " não encontrada."));
		return EmpresaDTO.fromEntity(empresa);
	}
	
	public EmpresaDTO buscarEmpresaPorCNPJ( String cnpj ){
		Empresa empresa = repository.findByCnpj( cnpj )
				.orElseThrow(() -> new RegraDeNegocioException( "Empresa de CNPJ: " + cnpj + " não encontrada" ));
		return EmpresaDTO.fromEntity( empresa );
	}
	
	@Transactional
	public EmpresaDTO salvarEmpresa(Empresa empresa) {
		empresa.setSenha(encoder.encode(empresa.getSenha()));
		Empresa salvo = repository.save(empresa);
		return EmpresaDTO.fromEntity(salvo);
	}
	
	@Transactional
	public EmpresaDTO atualizarEmpresa(Long id, Empresa empresaAtualizada) {
		Empresa empresa = repository.findById(id)
				.orElseThrow(() -> new RegraDeNegocioException("Empresa de ID: " + id + " não encontrada."));
		
		if (empresaAtualizada.getNome() != null && !empresaAtualizada.getNome().isBlank())
			empresa.setNome(empresaAtualizada.getNome());
		
		if (empresaAtualizada.getCnpj() != null && !empresaAtualizada.getCnpj().isBlank())
			empresa.setCnpj(empresaAtualizada.getCnpj());
		
		if (empresaAtualizada.getEmail() != null && !empresaAtualizada.getEmail().isBlank())
			empresa.setEmail(empresaAtualizada.getEmail());
		
		if (empresaAtualizada.getSenha() != null && !empresaAtualizada.getSenha().isBlank())
			empresa.setSenha(encoder.encode(empresaAtualizada.getSenha()));
		
		Empresa salvo = repository.save(empresa);
		return EmpresaDTO.fromEntity(salvo);
	}
	
	@Transactional
	public void deletarEmpresa(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional
	public void atualizarRefreshTokenEmpresa(Empresa empresa, String refreshToken, LocalDateTime expiracao) {
		empresa.setRefreshToken(refreshToken);
		empresa.setExpiracaoRefreshToken(expiracao);
		repository.save(empresa);
	}
	
	public Empresa buscarPorRefreshToken(String token) {
		return repository.findByRefreshToken(token)
				.orElse(null);
	}
}
