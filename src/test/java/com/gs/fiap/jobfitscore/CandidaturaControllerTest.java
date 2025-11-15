package com.gs.fiap.jobfitscore;

import com.gs.fiap.jobfitscore.domain.candidatura.CandidaturaDTO;
import com.gs.fiap.jobfitscore.domain.candidatura.CandidaturaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CandidaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidaturaService candidaturaService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testListarCandidaturas() throws Exception {
        CandidaturaDTO candidatura = new CandidaturaDTO();
        candidatura.setIdCandidatura(1L);
        candidatura.setUsuarioId(1L);
        candidatura.setVagaId(1L);
        candidatura.setDataCandidatura(LocalDateTime.now());
        candidatura.setStatus("Em Análise");

        Page<CandidaturaDTO> page = new PageImpl<>(Collections.singletonList(candidatura));
        when(candidaturaService.listarCandidaturas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/candidaturas/listar")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.content[0].idCandidatura").value(1))
                .andExpect(jsonPath("$.content[0].usuarioId").value(1));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testCadastrarCandidatura() throws Exception {
        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setIdCandidatura(1L);
        candidaturaDTO.setUsuarioId(1L);
        candidaturaDTO.setVagaId(1L);
        candidaturaDTO.setDataCandidatura(LocalDateTime.now());
        candidaturaDTO.setStatus("Em Análise");

        when(candidaturaService.cadastrarCandidatura(any(CandidaturaDTO.class))).thenReturn(candidaturaDTO);

        String jsonRequest = """
                {
                    "usuarioId": 1,
                    "vagaId": 1
                }
                """;

        mockMvc.perform(post("/api/candidaturas/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCandidatura").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.vagaId").value(1))
                .andExpect(jsonPath("$.status").value("Em Análise"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testBuscarCandidaturaPorId() throws Exception {
        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setIdCandidatura(1L);
        candidaturaDTO.setUsuarioId(1L);
        candidaturaDTO.setVagaId(1L);
        candidaturaDTO.setStatus("Em Análise");

        when(candidaturaService.buscarCandidaturaPorId(1L)).thenReturn(candidaturaDTO);

        mockMvc.perform(get("/api/candidaturas/buscar-por-id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCandidatura").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testBuscarCandidaturasPorUsuario() throws Exception {
        CandidaturaDTO candidatura1 = new CandidaturaDTO();
        candidatura1.setIdCandidatura(1L);
        candidatura1.setUsuarioId(1L);
        candidatura1.setVagaId(1L);
        candidatura1.setStatus("Em Análise");

        CandidaturaDTO candidatura2 = new CandidaturaDTO();
        candidatura2.setIdCandidatura(2L);
        candidatura2.setUsuarioId(1L);
        candidatura2.setVagaId(2L);
        candidatura2.setStatus("Aprovado");

        List<CandidaturaDTO> candidaturas = Arrays.asList(candidatura1, candidatura2);
        when(candidaturaService.buscarCandidaturasPorUsuario(1L)).thenReturn(candidaturas);

        mockMvc.perform(get("/api/candidaturas/buscar-por-usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].usuarioId").value(1))
                .andExpect(jsonPath("$[1].usuarioId").value(1))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "testempresa", roles = {"EMPRESA"})
    void testBuscarCandidaturasPorVaga() throws Exception {
        CandidaturaDTO candidatura1 = new CandidaturaDTO();
        candidatura1.setIdCandidatura(1L);
        candidatura1.setUsuarioId(1L);
        candidatura1.setVagaId(1L);
        candidatura1.setStatus("Em Análise");

        CandidaturaDTO candidatura2 = new CandidaturaDTO();
        candidatura2.setIdCandidatura(2L);
        candidatura2.setUsuarioId(2L);
        candidatura2.setVagaId(1L);
        candidatura2.setStatus("Triagem");

        List<CandidaturaDTO> candidaturas = Arrays.asList(candidatura1, candidatura2);
        when(candidaturaService.buscarCandidaturaPorVaga(1L)).thenReturn(candidaturas);

        mockMvc.perform(get("/api/candidaturas/buscar-por-vaga")
                        .param("vagaId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].vagaId").value(1))
                .andExpect(jsonPath("$[1].vagaId").value(1))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testAtualizarCandidatura() throws Exception {
        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setIdCandidatura(1L);
        candidaturaDTO.setUsuarioId(1L);
        candidaturaDTO.setVagaId(1L);
        candidaturaDTO.setStatus("Aprovado");

        when(candidaturaService.atualizarCandidatura(eq(1L), any(CandidaturaDTO.class))).thenReturn(candidaturaDTO);

        String jsonRequest = """
                {
                    "usuarioId": 1,
                    "vagaId": 1,
                    "status": "Aprovado"
                }
                """;

        mockMvc.perform(put("/api/candidaturas/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCandidatura").value(1))
                .andExpect(jsonPath("$.status").value("Aprovado"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testDeletarCandidatura() throws Exception {
        mockMvc.perform(delete("/api/candidaturas/deletar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}