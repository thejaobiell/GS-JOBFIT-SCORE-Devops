package com.gs.fiap.jobfitscore;

import com.gs.fiap.jobfitscore.domain.usuario.Usuario;
import com.gs.fiap.jobfitscore.domain.usuario.UsuarioDTO;
import com.gs.fiap.jobfitscore.domain.usuario.UsuarioService;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testListarUsuarios() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Teste");
        usuarioDTO.setEmail("teste@email.com");

        Page<UsuarioDTO> page = new PageImpl<>(Collections.singletonList(usuarioDTO));
        when(usuarioService.listarUsuarios(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/usuarios/listar")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalItems").value(1));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USUARIO"})
    void testBuscarUsuarioPorId() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao@email.com");

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuarioDTO);

        mockMvc.perform(get("/api/usuarios/buscar-por-id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void testCadastrarUsuario_EndpointPublico() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Novo Usuario");
        usuarioDTO.setEmail("novo@email.com");

        when(usuarioService.salvarUsuario(any(Usuario.class))).thenReturn(usuarioDTO);

        String jsonRequest = """
                {
                    "nome": "Novo Usuario",
                    "email": "novo@email.com",
                    "senha": "senha123"
                }
                """;

        mockMvc.perform(post("/api/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Novo Usuario"));
    }
}