package com.example.demo.controladores;

import entities.Reserva;
import servicios.ReservaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaControlador {

    private final ReservaServicio reservaServicio;

    public ReservaControlador(ReservaServicio reservaServicio) {
        this.reservaServicio = reservaServicio;
    }

    @PostMapping
    public ResponseEntity<Object> crearReserva(@RequestBody Reserva reservaDTO) {
        Reserva nuevaReserva = reservaServicio.agregarReserva(reservaDTO);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable int id) {
        ReservaDTO reserva = reservaService.obtenerReservaPorId(id);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> obtenerTodasLasReservas() {
        List<ReservaDTO> reservas = reservaService.obtenerReservas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizarReserva(@PathVariable int id, @RequestBody ReservaDTO reservaDTO) {
        ReservaDTO reservaActualizada = reservaService.actualizarReserva(id, reservaDTO);
        return new ResponseEntity<>(reservaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarReserva(@PathVariable int id) {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>("Reserva eliminada correctamente.", HttpStatus.NO_CONTENT);
    }
}
