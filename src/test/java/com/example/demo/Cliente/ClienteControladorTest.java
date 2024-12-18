package com.example.demo.Cliente;

import com.example.demo.controladores.ClienteControlador;
import com.example.demo.entities.Cliente;
import com.example.demo.repositorios.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {ClienteControlador.class, ClienteControladorTest.TestConfig.class})
public class ClienteControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    static class TestConfig {
        @Bean
        public ClienteRepositorio clienteRepositorio() {
            return Mockito.mock(ClienteRepositorio.class);
        }

        @Bean
        public ClienteControlador clienteControlador(ClienteRepositorio clienteRepositorio) {
            return new ClienteControlador(clienteRepositorio);
        }
    }

    @BeforeEach
    void setUp() {
        reset(clienteRepositorio);
    }

    @Test
    void testVerTodosLosClientes() throws Exception {
        Cliente cliente1 = new Cliente("Juan", "Pérez", "123456789");
        cliente1.setId(Long);
        Cliente cliente2 = new Cliente("Ana", "Gómez", "987654321");
        cliente2.setId(Long);

        when(clienteRepositorio.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        mockMvc.perform(get("/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")))
                .andExpect(jsonPath("$[1].nombre", is("Ana")));

        verify(clienteRepositorio, times(1)).findAll();
    }

    @Test
    void testVerClientePorId_Encontrado() throws Exception {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        cliente.setId(1L);

        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan")));

        verify(clienteRepositorio, times(1)).findById(1L);
    }

    @Test
    void testVerClientePorId_NoEncontrado() throws Exception {
        when(clienteRepositorio.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(clienteRepositorio, times(1)).findById(1L);
    }

    @Test
    void testCrearCliente() throws Exception {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        cliente.setId(1L);

        when(clienteRepositorio.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan\", \"apellido\":\"Pérez\", \"telefono\":\"123456789\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is("Juan")))
                .andExpect(jsonPath("$.id", is(1)));

        verify(clienteRepositorio, times(1)).save(Mockito.any(Cliente.class));
    }

    @Test
    void testActualizarCliente_Encontrado() throws Exception {
        Cliente clienteExistente = new Cliente("Juan", "Pérez", "123456789");
        clienteExistente.setId(1L);
        Cliente clienteActualizado = new Cliente("Juan", "Martínez", "987654321");
        clienteActualizado.setId(1L);

        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepositorio.save(Mockito.any(Cliente.class))).thenReturn(clienteActualizado);

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan\", \"apellido\":\"Martínez\", \"telefono\":\"987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido", is("Martínez")))
                .andExpect(jsonPath("$.telefono", is("987654321")));

        verify(clienteRepositorio, times(1)).findById(1L);
        verify(clienteRepositorio, times(1)).save(Mockito.any(Cliente.class));
    }

    @Test
    void testEliminarCliente_Encontrado() throws Exception {
        when(clienteRepositorio.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isOk());

        verify(clienteRepositorio, times(1)).existsById(1L);
        verify(clienteRepositorio, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarCliente_NoEncontrado() throws Exception {
        when(clienteRepositorio.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNotFound());

        verify(clienteRepositorio, times(1)).existsById(1L);
    }
}