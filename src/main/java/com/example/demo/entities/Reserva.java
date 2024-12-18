package com.example.demo.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Getter    //DA FALLO EL LOMBOK
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int clienteId;
    private LocalDateTime fechaHora;
    private int numeroComensales;
    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = true)
    private Cliente cliente;

    public Reserva(int clienteId, LocalDateTime fechaHora, int numeroComensales, Cliente cliente) {
        this.clienteId = clienteId;
        this.fechaHora = fechaHora;
        this.numeroComensales = numeroComensales;
        this.cliente = cliente;
    }

            //constructor por defecto


    public Reserva(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public int getNumeroComensales() {
        return numeroComensales;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Long getId() {
        return id;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setNumeroComensales(int numeroComensales) {
        this.numeroComensales = numeroComensales;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}