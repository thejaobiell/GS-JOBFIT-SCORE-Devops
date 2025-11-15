package com.gs.fiap.jobfitscore;

import com.gs.fiap.jobfitscore.domain.empresa.Empresa;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaDTO;
import com.gs.fiap.jobfitscore.domain.empresa.EmpresaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService empresaService;

    @Test
    @WithMockUser(username = "testempresa", roles = {"EMPRESA"})
    void testListarEmpresas() throws Exception {
        EmpresaDTO empresa1 = new EmpresaDTO();
        empresa1.setId(1L);
        empresa1.setNome("Empresa Teste 1");
        empresa1.setCnpj("12345678000190");

        EmpresaDTO empresa2 = new EmpresaDTO();
        empresa2.setId(2L);
        empresa2.setNome("Empresa Teste 2");
        empresa2.setCnpj("98765432000110");

        List<EmpresaDTO> empresas = Arrays.asList(empresa1, empresa2);
        when(empresaService.listarEmpresas()).thenReturn(empresas);

        mockMvc.perform(get("/api/empresas/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    @WithMockUser(username = "testempresa", roles = {"EMPRESA"})
    void testBuscarEmpresaPorId() throws Exception {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(1L);
        empresaDTO.setNome("Tech Solutions");
        empresaDTO.setCnpj("11222333000144");

        when(empresaService.buscarEmpresaPorId(1L)).thenReturn(empresaDTO);

        mockMvc.perform(get("/api/empresas/buscar-por-id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Tech Solutions"))
                .andExpect(jsonPath("$.cnpj").value("11222333000144"));
    }

    @Test
    @WithMockUser(username = "testempresa", roles = {"EMPRESA"})
    void testBuscarEmpresaPorCnpj() throws Exception {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(1L);
        empresaDTO.setNome("Empresa CNPJ Test");
        empresaDTO.setCnpj("12345678000190");

        when(empresaService.buscarEmpresaPorCNPJ("12345678000190")).thenReturn(empresaDTO);

        mockMvc.perform(get("/api/empresas/buscar-por-cnpj")
                        .param("cnpj", "12345678000190")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cnpj").value("12345678000190"))
                .andExpect(jsonPath("$.nome").value("Empresa CNPJ Test"));
    }

    @Test
    void testCadastrarEmpresa_EndpointPublico() throws Exception {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(1L);
        empresaDTO.setNome("Nova Empresa");
        empresaDTO.setCnpj("99888777000166");

        when(empresaService.criarEmpresa(any(Empresa.class))).thenReturn(empresaDTO);

        String jsonRequest = """
                {
                    "nome": "Nova Empresa",
                    "cnpj": "99888777000166",
                    "email": "contato@novaempresa.com",
                    "senha": "senha123"
                }
                """;

        mockMvc.perform(post("/api/empresas/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Nova Empresa"));
    }

    @Test
    @WithMockUser(username = "testempresa", roles = {"EMPRESA"})
    void testAtualizarEmpresa() throws Exception {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(1L);
        empresaDTO.setNome("Empresa Atualizada");
        empresaDTO.setCnpj("12345678000190");

        when(empresaService.atualizarEmpresa(eq(1L), any(Empresa.class))).thenReturn(empresaDTO);

        String jsonRequest = """
                {
                    "nome": "Empresa Atualizada",
                    "cnpj": "12345678000190"
                }
                """;

        mockMvc.perform(put("/api/empresas/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Empresa Atualizada"));
    }
}
