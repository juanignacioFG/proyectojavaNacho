package com.example.demo;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Reserva;
import com.example.demo.repositorios.ClienteRepositorio;
import com.example.demo.repositorios.ReservaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservaControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @BeforeEach
    void setUp() {
        // Limpia los repositorios antes de cada prueba
        reservaRepositorio.deleteAll();
        clienteRepositorio.deleteAll();
    }

    @Test
    void testCrearReserva() throws Exception {
        // Crear un cliente
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);

        String reservaJson = """
                {
                    "clienteId": %d,
                    "fechaHora": "2024-12-20T19:30:00",
                    "numeroComensales": 4,
                    "cliente": {
                        "id": %d
                    }
                }
                """.formatted(clienteGuardado.getId(), clienteGuardado.getId());

        mockMvc.perform(post("/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fechaHora").value("2024-12-20T19:30:00"))
                .andExpect(jsonPath("$.numeroComensales").value(4))
                .andExpect(jsonPath("$.cliente.id").value(clienteGuardado.getId()));
    }

    @Test
    void testObtenerTodasLasReservas() throws Exception {
        // Crear un cliente y una reserva
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        Reserva reserva = new Reserva(Math.toIntExact(clienteGuardado.getId()), LocalDateTime.of(2024, 12, 20, 19, 30), 4, clienteGuardado);
        reservaRepositorio.save(reserva);

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fechaHora").value("2024-12-20T19:30:00"))
                .andExpect(jsonPath("$[0].numeroComensales").value(4))
                .andExpect(jsonPath("$[0].cliente.id").value(clienteGuardado.getId()));
    }

    @Test
    void testObtenerReservaPorId() throws Exception {
        // Crear un cliente y una reserva
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        Reserva reserva = new Reserva(Math.toIntExact(clienteGuardado.getId()), LocalDateTime.of(2024, 12, 20, 19, 30), 4, clienteGuardado);
        Reserva reservaGuardada = reservaRepositorio.save(reserva);

        mockMvc.perform(get("/reservas/" + reservaGuardada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaHora").value("2024-12-20T19:30:00"))
                .andExpect(jsonPath("$.numeroComensales").value(4))
                .andExpect(jsonPath("$.cliente.id").value(clienteGuardado.getId()));
    }

    @Test
    void testActualizarReserva() throws Exception {
        // Crear un cliente y una reserva
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        Reserva reserva = new Reserva(Math.toIntExact(clienteGuardado.getId()), LocalDateTime.of(2024, 12, 20, 19, 30), 4, clienteGuardado);
        Reserva reservaGuardada = reservaRepositorio.save(reserva);

        String reservaActualizadaJson = """
                {
                    "fechaHora": "2024-12-21T20:00:00",
                    "numeroComensales": 6,
                    "cliente": {
                        "id": %d
                    }
                }
                """.formatted(clienteGuardado.getId());

        mockMvc.perform(put("/reservas/" + reservaGuardada.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaActualizadaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaHora").value("2024-12-21T20:00:00"))
                .andExpect(jsonPath("$.numeroComensales").value(6))
                .andExpect(jsonPath("$.cliente.id").value(clienteGuardado.getId()));
    }

    @Test
    void testEliminarReserva() throws Exception {
        // Crear un cliente y una reserva
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        Reserva reserva = new Reserva(Math.toIntExact(clienteGuardado.getId()), LocalDateTime.of(2024, 12, 20, 19, 30), 4, clienteGuardado);
        Reserva reservaGuardada = reservaRepositorio.save(reserva);

        mockMvc.perform(delete("/reservas/" + reservaGuardada.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/reservas/" + reservaGuardada.getId()))
                .andExpect(status().isNotFound());
    }
}
