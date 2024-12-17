package entitiesClass;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private int clienteId;
    private LocalDateTime fechaHora;
    private int numeroComensales;

    public Reserva(int id, int clienteId, LocalDateTime fechaHora, int numeroComensales) {
        this.id = id;
        this.clienteId = clienteId;
        this.fechaHora = fechaHora;
        this.numeroComensales = numeroComensales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getNumeroComensales() {
        return numeroComensales;
    }

    public void setNumeroComensales(int numeroComensales) {
        this.numeroComensales = numeroComensales;
    }
}
