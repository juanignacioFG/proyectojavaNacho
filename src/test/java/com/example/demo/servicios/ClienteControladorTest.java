package com.example.demo.servicios;

import com.example.demo.entities.Cliente;
import com.example.demo.repositorios.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @BeforeEach
    void setUp() {
        // Limpia el repositorio antes de cada prueba
        clienteRepositorio.deleteAll();
    }

    @Test
    void testCrearCliente() throws Exception {
        String clienteJson = """
                {
                    "nombre": "Juan",
                    "apellido": "Pérez",
                    "telefono": "123456789"
                }
                """;

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    void testObtenerTodosLosClientes() throws Exception {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        clienteRepositorio.save(cliente);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellido").value("Pérez"))
                .andExpect(jsonPath("$[0].telefono").value("123456789"));
    }

    @Test
    void testObtenerClientePorId() throws Exception {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);

        mockMvc.perform(get("/clientes/" + clienteGuardado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    void testActualizarCliente() throws Exception {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);

        String clienteActualizadoJson = """
                {
                    "nombre": "Carlos",
                    "apellido": "González",
                    "telefono": "987654321"
                }
                """;

        mockMvc.perform(put("/clientes/" + clienteGuardado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteActualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.apellido").value("González"))
                .andExpect(jsonPath("$.telefono").value("987654321"));
    }

    @Test
    void testEliminarCliente() throws Exception {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);

        mockMvc.perform(delete("/clientes/" + clienteGuardado.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/clientes/" + clienteGuardado.getId()))
                .andExpect(status().isNotFound());
    }
}