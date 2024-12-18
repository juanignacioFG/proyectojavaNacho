package com.example.demo.controladores;

import com.example.demo.entities.Reserva;
import com.example.demo.entities.Cliente;
import com.example.demo.repositorios.ReservaRepositorio;
import com.example.demo.repositorios.ClienteRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaControlador {

    private final ReservaRepositorio reservaRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public ReservaControlador(ReservaRepositorio reservaRepositorio, ClienteRepositorio clienteRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    // Obtener todas las reservas
    @GetMapping
    public List<Reserva> verTodasLasReservas() {
        return this.reservaRepositorio.findAll();
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> verReservaPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = this.reservaRepositorio.findById(id);
        return reserva.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva nuevaReserva) {
        if (nuevaReserva.getCliente() != null && nuevaReserva.getCliente().getId() != null) {
            Optional<Cliente> cliente = clienteRepositorio.findById(nuevaReserva.getCliente().getId());
            if (cliente.isPresent()) {
                nuevaReserva.setCliente(cliente.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cliente con ID " + nuevaReserva.getCliente().getId() + " no encontrado.");
            }
        }
        Reserva reservaGuardada = this.reservaRepositorio.save(nuevaReserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaGuardada);
    }

    // Actualizar una reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) {
        return this.reservaRepositorio.findById(id).map(reserva -> {
            reserva.setFechaHora(reservaActualizada.getFechaHora());
            reserva.setNumeroComensales(reservaActualizada.getNumeroComensales());
            if (reservaActualizada.getCliente() != null && reservaActualizada.getCliente().getId() != null) {
                Optional<Cliente> cliente = clienteRepositorio.findById(reservaActualizada.getCliente().getId());
                if (cliente.isPresent()) {
                    reserva.setCliente(cliente.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cliente con ID " + reservaActualizada.getCliente().getId() + " no encontrado.");
                }
            }
            Reserva reservaGuardada = this.reservaRepositorio.save(reserva);
            return ResponseEntity.ok(reservaGuardada);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        if (this.reservaRepositorio.existsById(id)) {
            this.reservaRepositorio.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}